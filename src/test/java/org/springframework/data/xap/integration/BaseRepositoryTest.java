package org.springframework.data.xap.integration;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.service.PersonService;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * @author Anna_Babich
 */
public abstract class BaseRepositoryTest {

    protected static final Person nick = new Person("1", "Nick", 20);
    protected static final Person chris = new Person("2", "Chris", 30);
    protected static final Person paul = new Person("3", "Paul", 40);
    protected static final Person chris2 = new Person("4", "Chris", 50);
    protected static final Person chris3 = new Person("5", "Chris", 30);
    protected static final Person paul2 = new Person("6", "Paul", 30);

    @Autowired
    protected PersonService personService;

    private List<Person> list;

    @Before
    public void init(){
        list = new ArrayList<>();
        list.add(chris);
        list.add(paul);
        list.add(chris2);
        list.add(chris3);
        list.add(paul2);
        personService.addPersons(list);
    }

    @After
    public void clear() {
        personService.deleteAll();
    }

    @Test
    public void testFindByNameOrAgeCreatedQuery(){
        List<Person> person = personService.findByNameOrAge("Paul", 50);
        assertEquals(3, person.size());
        assertTrue(person.contains(paul));
        assertTrue(person.contains(paul2));
        assertTrue(person.contains(chris2));
    }

    @Test
    public void testFindByNameAndAgeCreatedQuery(){
        List<Person> person = personService.findByNameAndAge("Chris", 30);
        assertEquals(2, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
    }

    @Test
    public void testFindByAgeCreatedQuery(){
        List<Person> person = personService.findByAge(30);
        assertEquals(3, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
        assertTrue(person.contains(paul2));
    }

    @Test
    public void testFindByNameDeclaredQuery(){
        List<Person> persons = personService.findByName("Chris");
        assertEquals(3, persons.size());
        assertTrue(persons.contains(chris));
        assertTrue(persons.contains(chris2));
        assertTrue(persons.contains(chris3));
    }

    @Test
    public void save() {
        personService.addPerson(nick);
        Person result = personService.getById(nick.getId());
        assertEquals(nick, result);
    }

    @Test
    public void saveMultiple() {
        Person result2 = personService.getById(chris.getId());
        Person result3 = personService.getById(paul.getId());
        assertEquals(chris, result2);
        assertEquals(paul, result3);
    }

    @Test
    public void exists(){
        assertTrue(personService.exists(chris.getId()));
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
        assertTrue(resultList.contains(chris));
    }

    @Test
    public void getAllById(){
        personService.addPerson(nick);
        List<String> idList = new ArrayList<>();
        idList.add(chris.getId());
        idList.add(paul.getId());
        List<Person> resultList = personService.getAll(idList);
        assertTrue(resultList.contains(chris));
        assertTrue(resultList.contains(paul));
        assertFalse(resultList.contains(nick));
    }

    @Test
    public void testGetAllWithSorting(){
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        List<Person> persons = personService.findPersons(sorting);
        assertEquals("5", persons.get(0).getId());
        assertEquals("4", persons.get(1).getId());
        assertEquals("2", persons.get(2).getId());
        assertEquals("1", persons.get(3).getId());
        assertEquals("6", persons.get(4).getId());
        assertEquals("3", persons.get(5).getId());
    }

    @Test
    public void testGetAllWithPaging(){
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(1, 2, sorting);
        List<Person> persons = personService.findPersons(pageable);
        assertEquals("2", persons.get(0).getId());
        assertEquals("1", persons.get(1).getId());
    }

    private void prepareDataForSortingTest() {
        personService.addPerson(nick);
        personService.delete("4");
        personService.delete("5");
        personService.delete("6");
        personService.addPerson(new Person("4", "Chris", 50));
        personService.addPerson(new Person("5", "Chris", 35));
        personService.addPerson(new Person("6", "Paul", 45));
    }
}
