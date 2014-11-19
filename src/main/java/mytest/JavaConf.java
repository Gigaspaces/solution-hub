package mytest;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.xap.repository.config.EnableXapRepositories;

/**
 * @author Oleksiy_Dyagilev
 */
@Configuration
@EnableXapRepositories("mytest")
public class JavaConf {

    @Bean
    public GigaSpace gigaSpaceBean() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/space?groups=fe2s");
        GigaSpace space = new GigaSpaceConfigurer(urlSpaceConfigurer).create();
        return space;
    }

}
