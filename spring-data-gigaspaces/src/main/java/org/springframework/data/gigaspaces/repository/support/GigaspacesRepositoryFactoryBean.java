package org.springframework.data.gigaspaces.repository.support;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentEntity;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> implements ApplicationContextAware {

    private MappingContext<? extends GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> context;
    private GigaSpace gigaSpace;

    /**
     * Creates a new {@link RepositoryFactoryBeanSupport} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    protected GigaspacesRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

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
            if (beans.size() == 0) {
                throw new NoSuchBeanDefinitionException(GigaSpace.class);
            } else if (beans.size() > 1) {
                throw new NoUniqueBeanDefinitionException(GigaSpace.class, beans.size(), "GigaSpace bean should be only one in context or defined explicitly for repository (using attribute gigaspace)");
            }
            gigaSpace = applicationContext.getBean(GigaSpace.class);
        }
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new GigaspacesRepositoryFactory(gigaSpace, context);
    }

    public void setGigaspacesMappingContext(MappingContext<? extends GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> context) {
        this.context = context;
    }
}
