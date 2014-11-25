package org.springframework.data.xap.test.integration.xml;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.spaceclient.SpaceClient;
import org.springframework.data.xap.test.model.Person;
import org.springframework.data.xap.test.service.PersonService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Anna_Babich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:RepositoryXmlTest-context.xml")
public class RepositoryXmlTest {

    @Autowired
    private SpaceClient spaceClient;

    @Autowired
    private PersonService personService;


    @Before
    public void init() {
    }

    @Test
    public void write() {
        personService.addPerson(new Person("1", "qqq"));
    }
}
