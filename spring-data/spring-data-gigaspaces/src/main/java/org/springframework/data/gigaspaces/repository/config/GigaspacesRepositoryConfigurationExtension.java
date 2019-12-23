package org.springframework.data.gigaspaces.repository.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.data.gigaspaces.repository.support.GigaspacesRepositoryFactoryBean;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.util.Optional;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport {

    @Override
    protected String getModulePrefix() {
        return "gigaspaces";
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config) {
        Element element = config.getElement();
        String gigaspace = element.getAttribute("gigaspace");
        if (StringUtils.hasText(gigaspace)) {
            builder.addPropertyReference("gigaSpace", gigaspace);
        }
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return GigaspacesRepositoryFactoryBean.class.getName();
    }

    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
        Optional<String> gigaspace = config.getAttribute("gigaspace");
        gigaspace.ifPresent(s->{if (StringUtils.hasText(s)) {
            builder.addPropertyReference("gigaSpace", s);
        }});
    }
}
