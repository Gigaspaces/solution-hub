package org.springframework.data.xap.repository.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableXapRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new XapRepositoryConfigurationExtension();
    }
}
