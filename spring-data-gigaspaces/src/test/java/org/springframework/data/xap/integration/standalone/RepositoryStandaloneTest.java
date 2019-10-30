package org.springframework.data.xap.integration.standalone;

import com.j_spaces.core.client.FinderException;
import org.junit.jupiter.api.BeforeEach;
import org.openspaces.core.GigaSpace;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.integration.BaseRepositoryTest;
import org.springframework.data.xap.repository.PersonRepository;
import org.springframework.data.xap.repository.support.XapRepositoryFactory;
import org.springframework.data.xap.utils.TestUtils;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
/**
 * Test for Repository standalone usage.
 *
 * @author Leonid_Poliakov
 */
public class RepositoryStandaloneTest extends BaseRepositoryTest {
    @BeforeEach
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
        GigaSpace space = TestUtils.initSpace("space");

        RepositoryFactorySupport factory = new XapRepositoryFactory(space, null);
        return factory.getRepository(PersonRepository.class);
    }

    private void injectDependencies(PersonRepository repository) {
        ReflectionTestUtils.setField(this, "personRepository", repository);
    }
}