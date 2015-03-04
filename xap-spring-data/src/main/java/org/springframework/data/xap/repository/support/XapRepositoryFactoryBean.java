package org.springframework.data.xap.repository.support;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> implements ApplicationContextAware {

    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;
    private GigaSpace gigaSpace;

    public GigaSpace getGigaSpace() {
        return gigaSpace;
    }

    public void setGigaSpace(GigaSpace gigaSpace) {
        this.gigaSpace = gigaSpace;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            if (gigaSpace == null) {
                Map<String, GigaSpace> beans = applicationContext.getBeansOfType(GigaSpace.class);
                if(beans.size() != 1){
                    throw new RuntimeException("GigaSpace bean should be only one in context or defined explicitly for repository (using attribute gigaspace)");
                }
                gigaSpace = applicationContext.getBean(GigaSpace.class);
            }
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new XapRepositoryFactory(gigaSpace, context);
    }

    public void setXapMappingContext(MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        this.context = context;
    }
}
