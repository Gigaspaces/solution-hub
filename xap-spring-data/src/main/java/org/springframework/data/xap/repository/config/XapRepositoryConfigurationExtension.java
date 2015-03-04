package org.springframework.data.xap.repository.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.data.xap.repository.support.XapRepositoryFactoryBean;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return "xap";
    }

    @Override
    public String getRepositoryFactoryClassName() {
        return XapRepositoryFactoryBean.class.getName();
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config) {
        Element element = config.getElement();
        String gigaspace = element.getAttribute("gigaspace");
        if (StringUtils.hasText(gigaspace)) {
            builder.addPropertyReference("gigaSpace", gigaspace);
        }
    }
}
