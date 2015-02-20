package org.springframework.data.xap.repository.query;

import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

/**
 * @author Anna_Babich.
 */
public abstract class XapRepositoryQuery implements RepositoryQuery{

    private XapQueryMethod queryMethod;

    public XapRepositoryQuery(XapQueryMethod queryMethod) {
        this.queryMethod = queryMethod;
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }
}
