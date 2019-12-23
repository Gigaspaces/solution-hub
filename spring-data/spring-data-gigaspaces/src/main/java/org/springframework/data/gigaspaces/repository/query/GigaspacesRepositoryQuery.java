package org.springframework.data.gigaspaces.repository.query;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;

/**
 * @author Anna_Babich.
 */
public abstract class GigaspacesRepositoryQuery implements RepositoryQuery {
    private GigaspacesQueryMethod queryMethod;

    public GigaspacesRepositoryQuery(GigaspacesQueryMethod queryMethod) {
        this.queryMethod = queryMethod;
    }

    @Override
    public QueryMethod getQueryMethod() {
        return queryMethod;
    }

    protected String getTypeName(GigaspacesQueryMethod method) {
        if (isSpaceDocumentQuery(method)) {
            SpaceDocumentName annotation = method.getMetadata().getRepositoryInterface().getAnnotation(SpaceDocumentName.class);
            return annotation.value();
        } else {
            return method.getEntityInformation().getJavaType().getCanonicalName();
        }
    }

    protected boolean isSpaceDocumentQuery(GigaspacesQueryMethod method) {
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