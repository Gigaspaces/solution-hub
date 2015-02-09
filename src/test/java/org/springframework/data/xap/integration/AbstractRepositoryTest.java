package org.springframework.data.xap.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.service.PersonService;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * @author Anna_Babich
 */
public abstract class AbstractRepositoryTest {
    private static final Person nick = new Person("1", "Nick");
    private static final Person cris = new Person("2", "Cris");
    private static final Person paul = new Person("3", "Paul");

    @Autowired
    private PersonService personService;

    private List<Person> list;

    @Before
    public void init(){
        list = new ArrayList<>();
        list.add(cris);
        list.add(paul);
        personService.addPersons(list);
    }

    @After
    public void clear() {
        personService.deleteAll();
    }

    @Test
    public void save() {
        personService.addPerson(nick);
        Person result = personService.getById(nick.getId());
        assertEquals(nick, result);
    }

    @Test
    public void saveMultiple(){
        Person result2 = personService.getById(cris.getId());
        Person result3 = personService.getById(paul.getId());
        assertEquals(cris, result2);
        assertEquals(paul, result3);
    }

    @Test
    public void exists(){
        assertTrue(personService.exists(cris.getId()));
    }

    @Test
    public void count(){
        assertEquals(personService.count(), list.size());
    }

    @Test
    public void delete(){
        personService.delete(paul.getId());
        assertFalse(personService.exists(paul.getId()));
    }

    @Test
    public void getAll(){
        List<Person> resultList = personService.getAll();
        assertTrue(resultList.contains(paul));
        assertTrue(resultList.contains(cris));
    }

    @Test public void getAllById(){
        personService.addPerson(nick);
        List<String> idList = new ArrayList<>();
        idList.add(cris.getId());
        idList.add(paul.getId());
        List<Person> resultList = personService.getAll(idList);
        assertTrue(resultList.contains(cris));
        assertTrue(resultList.contains(paul));
        assertFalse(resultList.contains(nick));
    }
}
