package com.gigaspaces.spring.data.repository.support;

import com.gigaspaces.spring.data.repository.mapping.XapMappingContext;
import com.gigaspaces.spring.data.repository.mapping.XapPersistentEntity;
import com.gigaspaces.spring.data.repository.mapping.XapPersistentProperty;
import com.gigaspaces.spring.data.repository.query.DefaultXapEntityInformation;
import org.openspaces.core.GigaSpace;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactory extends RepositoryFactorySupport {
    private GigaSpace space;
    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;

    public XapRepositoryFactory(GigaSpace space, MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        this.space = space;
        this.context = context == null ? new XapMappingContext() : context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        XapPersistentEntity<T> entity = (XapPersistentEntity<T>) context.getPersistentEntity(domainClass);
        return new DefaultXapEntityInformation<>(entity);
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        return new SimpleXapRepository<>(space, entityInformation);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleXapRepository.class;
    }
}
