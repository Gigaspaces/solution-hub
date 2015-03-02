package org.springframework.data.xap.repository.query;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;
import org.springframework.data.xap.repository.Query;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author Anna_Babich.
 */
public class XapQueryMethod extends QueryMethod {
    private final Method method;
    private final XapPersistentEntity<?> persistentEntity;
    private final RepositoryMetadata metadata;

    public XapQueryMethod(Method method, RepositoryMetadata metadata, MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        super(method, metadata);
        Assert.notNull(context);
        this.metadata = metadata;
        this.method = method;
        this.persistentEntity = context.getPersistentEntity(getDomainClass());
    }

    public boolean hasAnnotatedQuery() {
        return StringUtils.hasText(getAnnotatedQuery());
    }

    public XapPersistentEntity<?> getPersistentEntity() {
        return persistentEntity;
    }

    String getAnnotatedQuery() {
        Query query = method.getAnnotation(Query.class);
        String queryString = query == null ? null : (String) AnnotationUtils.getValue(query);
        return StringUtils.hasText(queryString) ? queryString : null;
    }

    public RepositoryMetadata getMetadata() {
        return metadata;
    }
}
