package org.springframework.data.xap.repository.config;

import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.xap.repository.support.XapRepositoryFactoryBean;

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
}
