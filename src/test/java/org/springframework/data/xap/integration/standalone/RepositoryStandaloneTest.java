package org.springframework.data.xap.integration.standalone;

import com.j_spaces.core.client.FinderException;
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
import org.springframework.data.xap.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

/**
 * Test for Repository standalone usage.
 *
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
        SpaceClient spaceClient = TestUtils.initSpaceClient();

        RepositoryFactorySupport factory = new XapRepositoryFactory(spaceClient, null);
        return factory.getRepository(PersonRepository.class);
    }

    private void injectDependencies(PersonRepository repository) {
        PersonService service = new PersonServiceImpl();
        ReflectionTestUtils.setField(service, "personRepository", repository);
        ReflectionTestUtils.setField(this, "personService", service);
    }
}