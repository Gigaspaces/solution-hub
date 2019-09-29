package org.springframework.data.xap.repository.query;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anna_Babich.
 */
public class StringBasedXapRepositoryQuery extends XapRepositoryQuery {

    private final XapQueryMethod method;
    private final GigaSpace space;
    private final String query;

    public StringBasedXapRepositoryQuery(String query, XapQueryMethod method, GigaSpace space) {
        super(method);
        this.method = method;
        this.space = space;
        this.query = query;
    }

    public StringBasedXapRepositoryQuery(XapQueryMethod method, GigaSpace space) {
        this(method.getAnnotatedQuery(), method, space);
    }

    @Override
    public Object execute(Object[] parameters) {
        SQLQuery sqlQuery = new SQLQuery(getTypeName(method), query);

        // bind parameters
        sqlQuery.setParameters(prepareStringParameters(parameters));

        // set projections
        Projection projection = extractProjectionParameter(parameters);
        if (projection != null) {
            sqlQuery.setProjections(projection.getProperties());
        }

        return space.readMultiple(sqlQuery);
    }

    private Object[] prepareStringParameters(Object[] parameters) {
        List<Object> stringParameters = new ArrayList<Object>(parameters.length);
        for (Object parameter : parameters) {
            if (!isPageableOrProjection(parameter)) {
                stringParameters.add(parameter);
            }
        }
        return stringParameters.toArray();
    }

}