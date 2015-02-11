package org.springframework.data.xap.repository.query;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.xap.mapping.XapMappingContext;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;

import java.lang.reflect.Method;

/**
 * TODO
 * @author Anna_Babich.
 */
public class XapQueryMethod extends QueryMethod{

    public XapQueryMethod(Method method, RepositoryMetadata metadata,MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        super(method, metadata);
        //TODO
    }

    public XapQueryMethod(Method method, RepositoryMetadata metadata) {
        super(method, metadata);
    }

    public boolean hasAnnotatedQuery(){
        //TODO
        return false;
    }
}
