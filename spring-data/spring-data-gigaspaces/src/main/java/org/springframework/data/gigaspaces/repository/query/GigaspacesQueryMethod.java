package org.springframework.data.gigaspaces.repository.query;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentEntity;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentProperty;
import org.springframework.data.gigaspaces.repository.Query;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author Anna_Babich.
 */
public class GigaspacesQueryMethod extends QueryMethod {
    private final Method method;
    private final RepositoryMetadata metadata;
    private final String typeName;


    public GigaspacesQueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory, MappingContext<? extends GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> context) {
        super(method, metadata,factory);
        Assert.notNull(context,"context cannot be null");
        this.metadata = metadata;
        this.method = method;
        if (SpaceDocument.class.isAssignableFrom(metadata.getDomainType())) {
            typeName = metadata.getRepositoryInterface().getAnnotation(SpaceDocumentName.class).value();
        } else {
            typeName = getDomainClass().getSimpleName();
        }
    }

    public boolean hasAnnotatedQuery() {
        return StringUtils.hasText(getAnnotatedQuery());
    }

    public String getAnnotatedQuery() {
        Query query = method.getAnnotation(Query.class);
        String queryString = query == null ? null : (String) AnnotationUtils.getValue(query);
        return StringUtils.hasText(queryString) ? queryString : null;
    }

    @Override
    public String getNamedQueryName() {
        return String.format("%s.%s", typeName, method.getName());
    }

    public RepositoryMetadata getMetadata() {
        return metadata;
    }
}
