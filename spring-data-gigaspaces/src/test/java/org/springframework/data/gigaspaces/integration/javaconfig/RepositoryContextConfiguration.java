package org.springframework.data.gigaspaces.integration.javaconfig;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.gigaspaces.repository.config.EnableGigaspacesRepositories;
import org.springframework.data.gigaspaces.utils.TestUtils;

@Configuration
@ComponentScan("org.springframework.data.gigaspaces")
@EnableGigaspacesRepositories(value = "org.springframework.data.gigaspaces.repository", namedQueriesLocation = "classpath:named-queries.properties", gigaspace = "gigaSpace1")
public class RepositoryContextConfiguration {
    @Autowired
    Environment env;

    @Bean
    public GigaSpace gigaSpace() {
            return TestUtils.initSpace("space");
        }

    @Bean
    public GigaSpace gigaSpace1() {
            return TestUtils.initSpace("space1");
        }
}
