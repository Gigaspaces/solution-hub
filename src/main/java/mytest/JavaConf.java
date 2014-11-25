package mytest;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.spaceclient.SpaceClient;


/**
 * @author Oleksiy_Dyagilev
 */

public class JavaConf {

    @Bean
    public SpaceClient gigaSpaceBean() {

        SpaceClient gigaSpace;
        try {
            ISpaceProxy iSpace = (ISpaceProxy) SpaceFinder.find("jini://*/*/space");
            gigaSpace = new SpaceClient();
            gigaSpace.setSpace(iSpace);
        } catch (FinderException e) {
            throw new RuntimeException(e);
        }

        return gigaSpace;
    }

}
