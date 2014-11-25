package org.springframework.data.xap.repository.support;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.mapping.XapMappingContext;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;
import org.springframework.data.xap.repository.query.DefaultXapEntityInformation;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactory extends RepositoryFactorySupport {
    private SpaceClient space;

    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;

    public XapRepositoryFactory(SpaceClient space, MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
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
