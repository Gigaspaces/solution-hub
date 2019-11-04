package org.springframework.data.gigaspaces.examples;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gigaspaces.repository.config.EnableGigaspacesRepositories;

/**
 * @author Leonid_Poliakov
 */
@Configuration
@EnableGigaspacesRepositories("org.springframework.data.gigaspaces.examples.simple")
@ComponentScan("org.springframework.data.gigaspaces.examples.simple")
public class ContextConfiguration {

    /**
     * Builds a space instance with settings that allow it to connect to the 'space'.
     *
     * @return gigaSpace.
     */
    @Bean
    public GigaSpace gigaSpace() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./space");
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }
}