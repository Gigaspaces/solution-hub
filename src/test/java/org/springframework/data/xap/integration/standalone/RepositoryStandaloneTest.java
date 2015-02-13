package org.springframework.data.xap.integration.standalone;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.integration.BaseRepositoryTest;
import org.springframework.data.xap.repository.PersonRepository;
import org.springframework.data.xap.repository.support.XapRepositoryFactory;
import org.springframework.data.xap.service.PersonService;
import org.springframework.data.xap.service.PersonServiceImpl;
import org.springframework.data.xap.spaceclient.SpaceClient;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Leonid_Poliakov
 */
@RunWith(JUnit4.class)
public class RepositoryStandaloneTest extends BaseRepositoryTest {
    @Before
    public void init() {
        try {
            PersonRepository personRepository = initRepository();
            injectDependencies(personRepository);
            super.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PersonRepository initRepository() throws FinderException, IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));

        ISpaceProxy space = (ISpaceProxy) SpaceFinder.find("jini://*/*/space?groups=" + properties.getProperty("space.groups"));
        SpaceClient gigaSpace = new SpaceClient();
        gigaSpace.setSpace(space);

        RepositoryFactorySupport factory = new XapRepositoryFactory(gigaSpace, null);
        return factory.getRepository(PersonRepository.class);
    }

    private void injectDependencies(PersonRepository repository) {
        PersonService service = new PersonServiceImpl();
        ReflectionTestUtils.setField(service, "personRepository", repository);
        ReflectionTestUtils.setField(this, "personService", service);
    }
}