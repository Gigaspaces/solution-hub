package org.springframework.data.xap.examples;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.spaceclient.SpaceClient;

/**
 * @author Leonid_Poliakov
 */
@Configuration
@EnableXapRepositories("org.springframework.data.xap.examples.repository")
public class ContextConfiguration {

    /**
     * Builds a space instance with settings that allow it to connect to the 'example-space'.
     *
     * @return SpaceClient bean
     */
    @Bean
    public SpaceClient spaceClient() {
        try {
            ISpaceProxy iSpace = (ISpaceProxy) SpaceFinder.find("jini://*/*/example-space" + GroupConfig.getGroupName());
            SpaceClient gigaSpace = new SpaceClient();
            gigaSpace.setSpace(iSpace);
            return gigaSpace;
        } catch (FinderException e) {
            throw new RuntimeException(e);
        }
    }
}