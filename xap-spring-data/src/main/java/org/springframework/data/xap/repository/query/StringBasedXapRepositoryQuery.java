package org.springframework.data.xap.repository.query;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

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
        sqlQuery.setParameters(parameters);
        return space.readMultiple(sqlQuery);
    }

}