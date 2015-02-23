package org.springframework.data.xap.examples;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.repository.config.EnableXapRepositories;

/**
 * @author Leonid_Poliakov
 */
@Configuration
@EnableXapRepositories("org.springframework.data.xap.examples.repository")
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