package org.springframework.data.xap.repository.query;

import com.google.common.primitives.Ints;
import com.j_spaces.core.client.SQLQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.ParametersParameterAccessor;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anna_Babich.
 */
public class PartTreeXapRepositoryQuery extends XapRepositoryQuery{

    private final XapQueryMethod method;
    private final PartTree tree;
    private final SpaceClient space;

    public PartTreeXapRepositoryQuery(XapQueryMethod method, SpaceClient space){
        super(method);

        Class<?> domainClass = method.getEntityInformation().getJavaType();

        this.tree = new PartTree(method.getName(), domainClass);
        this.method = method;
        this.space = space;
    }

    @Override
    public Object execute(Object[] parameters) {
        ParametersParameterAccessor parameterAccessor = new ParametersParameterAccessor(method.getParameters(), parameters);

        String className = method.getPersistentEntity().getName();

        String query = new XapQueryCreator(tree, parameterAccessor).createQuery();

        SQLQuery sqlQuery = new SQLQuery(className, query);

        Pageable pageable = extractPagingParameter(parameters);

        int maxEntries = (pageable == null) ? Integer.MAX_VALUE : pageable.getOffset() + pageable.getPageSize();

        sqlQuery.setParameters(prepareStringParameters(parameters));

        Object[] results = space.readMultiple(sqlQuery, maxEntries);

        return applyPagination(results, parameters);
    }

    private Pageable extractPagingParameter(Object[] parameters) {
        Pageable result = null;
        int pageableCount = 0;
        for (Object parameter : parameters){
            if (parameter instanceof Pageable){
                result = (Pageable) parameter;
                pageableCount++;
            }
        }
        if (pageableCount > 1){
            throw new RuntimeException("Only one Pageable parameter is allowed");
        }
        return result;
    }


    private Object[] applyPagination(Object[] results, Object[] parameters) {
        for (Object parameter : parameters){
            if (parameter instanceof Pageable){
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
        List<Object> stringParameters = new ArrayList<Object>(parameters.length);

        for (Object parameter : parameters) {
            if (parameter == null || parameter instanceof Sort) {
                stringParameters.add(parameter);
            } else if (parameter instanceof Pageable){

            } else {
                switch (partsIterator.next().getType()) {
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
            }
        }

        return stringParameters.toArray();
    }

}
