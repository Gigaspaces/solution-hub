package mytest;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.SpaceFinder;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.wrappers.ISpaceWrapper;

import javax.ejb.FinderException;

/**
 * @author Oleksiy_Dyagilev
 */
@Configuration
@EnableXapRepositories("mytest")
public class JavaConf {

    @Bean
    public ISpaceWrapper gigaSpaceBean() {

        ISpaceWrapper gigaSpace;
        try {
            ISpaceProxy iSpace = (ISpaceProxy) SpaceFinder.find("jini://*/*/space");
            gigaSpace = new ISpaceWrapper();
            gigaSpace.setSpace(iSpace);
        } catch (com.j_spaces.core.client.FinderException e) {
            throw new RuntimeException(e);
        }

        return gigaSpace;
    }

}
