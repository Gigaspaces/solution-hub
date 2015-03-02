package org.springframework.data.xap.repository.query;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.domain.Pageable;
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

    protected boolean isPageableOrProjection(Object parameter) {
        return (parameter instanceof Pageable) || (parameter instanceof Projection);
    }

    /**
     * @return null if not found
     */
    protected Projection extractProjectionParameter(Object[] parameters) {
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
}