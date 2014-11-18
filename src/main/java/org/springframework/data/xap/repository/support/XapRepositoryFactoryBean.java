package org.springframework.data.xap.repository.support;

import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> implements ApplicationContextAware {

    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;
    private GigaSpace space;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.space = applicationContext.getBean(GigaSpace.class);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new XapRepositoryFactory(space, context);
    }

    public void setXapMappingContext(MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        this.context = context;
    }
}
