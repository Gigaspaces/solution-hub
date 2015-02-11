package org.springframework.data.xap.repository.query;

import com.j_spaces.core.client.SQLQuery;
import org.springframework.data.xap.spaceclient.SpaceClient;
import org.springframework.util.StringUtils;

/**
 * TODO
 * @author Anna_Babich.
 */
public class StringBasedXapRepositoryQuery extends XapRepositoryQuery{

    private boolean userDefinedQuery = false;

    private final XapQueryMethod method;
    private final SpaceClient spaceClient;
    private final String query;

    public StringBasedXapRepositoryQuery(String query, XapQueryMethod method, SpaceClient space){

        this.userDefinedQuery |= !StringUtils.hasText(query);
        this.method = method;
        this.spaceClient = space;
        this.query = query;
    }

    public StringBasedXapRepositoryQuery(XapQueryMethod method, SpaceClient space){
        this(method.getAnnotatedQuery(), method, space);
    }

    public StringBasedXapRepositoryQuery asUserDefinedQuery() {
        this.userDefinedQuery = true;
        return this;
    }

    @Override
    public Object execute(Object[] parameters) {
        SQLQuery sqlQuery = new SQLQuery(method.getEntityInformation().getJavaType(), query);
        sqlQuery.setParameters(parameters);
        return spaceClient.readMultiple(sqlQuery);
    }

    public boolean isUserDefinedQuery() {
        return userDefinedQuery;
    }
}

