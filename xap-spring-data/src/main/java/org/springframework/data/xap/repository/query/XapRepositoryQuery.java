package org.springframework.data.xap.repository.query;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.xap.repository.SpaceDocumentRepository;

/**
 * @author Anna_Babich.
 */
public abstract class XapRepositoryQuery implements RepositoryQuery {
    private XapQueryMethod queryMethod;

    public XapRepositoryQuery(XapQueryMethod queryMethod) {
        this.queryMethod = queryMethod;
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }

    protected String getTypeName(XapQueryMethod method) {
        if (isSpaceDocumentQuery(method)) {
            SpaceDocumentRepository annotation = method.getMetadata().getRepositoryInterface().getAnnotation(SpaceDocumentRepository.class);
            return annotation.typeName();
        } else {
            return method.getEntityInformation().getJavaType().getCanonicalName();
        }
    }

    protected boolean isSpaceDocumentQuery(XapQueryMethod method) {
        return SpaceDocument.class.isAssignableFrom(method.getMetadata().getDomainType());
    }
}