package org.springframework.data.xap.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.xap.repository.config.XapRepositoryConfigurationExtension;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapDataNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        XapRepositoryConfigurationExtension extension = new XapRepositoryConfigurationExtension();
        registerBeanDefinitionParser("repositories", new RepositoryBeanDefinitionParser(extension));
    }
}
