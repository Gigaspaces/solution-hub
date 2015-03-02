package org.springframework.data.xap.repository.query;

import com.gigaspaces.document.SpaceDocument;
import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;

import java.util.*;

/**
 * @author Anna_Babich.
 */
public class PartTreeXapRepositoryQuery extends XapRepositoryQuery {

    private final XapQueryMethod method;
    private final PartTree tree;
    private final GigaSpace space;

    private int betweenCount;

    public PartTreeXapRepositoryQuery(XapQueryMethod method, GigaSpace space) {
        super(method);

        Class<?> domainClass = method.getEntityInformation().getJavaType();

        if (SpaceDocument.class.isAssignableFrom(domainClass)) {
            domainClass = SpaceDocument.class;
        }
        this.tree = new PartTree(method.getName(), domainClass);
        this.method = method;
        this.space = space;
        this.betweenCount = 0;
    }

    @Override
    public Object execute(Object[] parameters) {
        checkForUnsupportedParameters(parameters, tree);
        // parse method and prepare SQLQuery
        ParametersParameterAccessor parameterAccessor = new ParametersParameterAccessor(method.getParameters(), parameters);
        String query = new XapQueryCreator(tree, parameterAccessor).createQuery();
        query = prepareForInOperator(query, parameters);
        SQLQuery sqlQuery = new SQLQuery(getTypeName(method), query);
        // bind parameters
        sqlQuery.setParameters(prepareStringParameters(parameters));

        // set projections
        Projection projection = extractProjectionParameter(parameters);
        if (projection != null) {
            sqlQuery.setProjections(projection.getProperties());
        }

        // extract paging param
        Pageable pageable = extractPagingParameter(parameters);
        int maxEntries = (pageable == null) ? Integer.MAX_VALUE : pageable.getOffset() + pageable.getPageSize();

        // execute SQLQuery
        Object[] results = space.readMultiple(sqlQuery, maxEntries);

        // client side pagination
        return applyPagination(results, parameters);
    }

    private void checkForUnsupportedParameters(Object[] parameters, PartTree tree) {
        for (Object parameter : parameters) {
            if (parameter instanceof Sort) {
                checkSorting((Sort) parameter);
            }
            if (parameter instanceof Pageable) {
                checkSorting(((Pageable) parameter).getSort());
            }
        }
        for (Part part : tree.getParts()) {
            if (Part.IgnoreCaseType.NEVER != part.shouldIgnoreCase()) {
                throw new UnsupportedOperationException("IgnoreCase is not supported for created queries");
            }
        }
    }

    private void checkSorting(Sort sort) {
        if (sort != null) {
            for (Sort.Order order : sort) {
                if (order.isIgnoreCase() || nullHandlingIsNotNative(order)) {
                    throw new UnsupportedOperationException("Null handling and ignoreCase for sorting are not supported");
                }
            }
        }
    }

    private boolean nullHandlingIsNotNative(Sort.Order order) {
        return (order.getNullHandling() != null && order.getNullHandling() != Sort.NullHandling.NATIVE);
    }

    /**
     * @return null if not found
     */
    private Projection extractProjectionParameter(Object[] parameters) {
        Projection result = null;
        int count = 0;
        for (Object parameter : parameters) {
            if (parameter instanceof Projection) {
                result = (Projection) parameter;
                count++;
            }
        }
        if (count > 1) {
            throw new RuntimeException("Only one Projections parameter is allowed");
        }
        return result;
    }

    /**
     * @return null if not found
     */
    private Pageable extractPagingParameter(Object[] parameters) {
        Pageable result = null;
        int pageableCount = 0;
        for (Object parameter : parameters) {
            if (parameter instanceof Pageable) {
                result = (Pageable) parameter;
                pageableCount++;
            }
        }
        if (pageableCount > 1) {
            throw new RuntimeException("Only one Pageable parameter is allowed");
        }
        return result;
    }


    private Object[] applyPagination(Object[] results, Object[] parameters) {
        for (Object parameter : parameters) {
            if (parameter instanceof Pageable) {
                Pageable pageable = (Pageable) parameter;
                int offset = pageable.getOffset();
                int lastIndex = Ints.min(offset + pageable.getPageSize(), results.length);
                return Arrays.copyOfRange(results, offset, lastIndex);
            }
        }
        return results;
    }

    private Object[] prepareStringParameters(Object[] parameters) {
        Iterator<Part> partsIterator = tree.getParts().iterator();
        Part currPart = getNextPart(partsIterator);
        List<Object> stringParameters = new ArrayList<Object>(parameters.length);
        for (Object parameter : parameters) {
            if (isSortOrNull(parameter)) {
                stringParameters.add(parameter);
            } else if (isPageableOrProjection(parameter)) {
                // skip
            } else if (isBetween(currPart)) {
                stringParameters.add(parameter);
                currPart = handleBetween(currPart, partsIterator);
            } else {
                switch (currPart.getType()) {
                    case IN:
                    case NOT_IN:
                        stringParameters.addAll((List) parameter);
                    case CONTAINING:
                        stringParameters.add(String.format("%%%s%%", parameter.toString()));
                        break;
                    case STARTING_WITH:
                        stringParameters.add(String.format("%s%%", parameter.toString()));
                        break;
                    case ENDING_WITH:
                        stringParameters.add(String.format("%%%s", parameter.toString()));
                        break;
                    default:
                        stringParameters.add(parameter);
                }
                currPart = getNextPart(partsIterator);
            }
        }
        return stringParameters.toArray();
    }

    private String prepareForInOperator(String query, Object[] parameters) {
        Iterator<Part> partsIterator = tree.getParts().iterator();
        Part currPart = getNextPart(partsIterator);
        for (Object parameter : parameters) {
            if (!isSortPageableProjectionOrNull(parameter)) {
                if (isInOrNotIn(currPart)) {
                    StringBuilder str = new StringBuilder("in (");
                    List<String> paramList = new ArrayList<>((Collections.nCopies(((List) parameter).size(), "?")));
                    Joiner joiner = Joiner.on(", ").skipNulls();
                    str.append(joiner.join(paramList)).append(")");
                    query = query.replace("in ?", str);
                }
                if (isBetween(currPart)) {
                    currPart = handleBetween(currPart, partsIterator);
                } else {
                    currPart = getNextPart(partsIterator);
                }
            }
        }
        return query;
    }

    private Part getNextPart(Iterator<Part> partsIterator) {
        if (partsIterator.hasNext()) {
            return partsIterator.next();
        }
        return null;
    }

    private Part handleBetween(Part currPart, Iterator<Part> partsIterator) {
        if (betweenCount % 2 == 1) {
            currPart = getNextPart(partsIterator);
        }
        betweenCount++;
        return currPart;
    }

    private boolean isSortOrNull(Object parameter) {
        return (parameter == null) || (parameter instanceof Sort);
    }

    private boolean isPageableOrProjection(Object parameter) {
        return (parameter instanceof Pageable) || (parameter instanceof Projection);
    }

    private boolean isSortPageableProjectionOrNull(Object parameter) {
        return isPageableOrProjection(parameter) || isSortOrNull(parameter);
    }

    private boolean isInOrNotIn(Part currPart) {
        return currPart.getType().equals(Part.Type.IN) || currPart.getType().equals(Part.Type.NOT_IN);
    }

    private boolean isBetween(Part currPart) {
        return currPart.getType().equals(Part.Type.BETWEEN);
    }

}