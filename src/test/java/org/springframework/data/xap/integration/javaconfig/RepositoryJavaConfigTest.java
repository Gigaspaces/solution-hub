package org.springframework.data.xap.integration.javaconfig;

/**
 * @author Anna_Babich
 */

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.integration.AbstractRepositoryTest;
import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.spaceclient.SpaceClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RepositoryJavaConfigTest extends AbstractRepositoryTest{

    @Configuration
    @ComponentScan("org.springframework.data.xap")
    @EnableXapRepositories("org.springframework.data.xap")
    public static class ContextConfiguration {

        @Bean
        public SpaceClient gigaSpace() {
            SpaceClient gigaSpace;
            try {
                ISpaceProxy iSpace = (ISpaceProxy) SpaceFinder.find("jini://*/*/space?groups=anna");
                gigaSpace = new SpaceClient();
                gigaSpace.setSpace(iSpace);
            } catch (FinderException e) {
                throw new RuntimeException(e);
            }
            return gigaSpace;
        }
    }
}


