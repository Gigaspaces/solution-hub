package org.springframework.data.gigaspaces.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.gigaspaces.repository.config.GigaspacesRepositoryConfigurationExtension;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesDataNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        GigaspacesRepositoryConfigurationExtension extension = new GigaspacesRepositoryConfigurationExtension();
        registerBeanDefinitionParser("repositories", new RepositoryBeanDefinitionParser(extension));
    }

}
