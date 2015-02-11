/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.xap.integration.standalone;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.integration.AbstractRepositoryTest;
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
public class RepositoryStandaloneTest extends AbstractRepositoryTest {
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