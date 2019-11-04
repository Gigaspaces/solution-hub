package org.springframework.data.gigaspaces.repository.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableGigaspacesRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new GigaspacesRepositoryConfigurationExtension();
    }
}
