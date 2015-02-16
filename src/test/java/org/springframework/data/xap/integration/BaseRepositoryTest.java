package org.springframework.data.xap.integration;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.PersonRepository;
import org.springframework.data.xap.repository.query.Projection;


import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.springframework.data.xap.repository.query.Projection.projections;

/**
 * @author Anna_Babich
 */
public abstract class BaseRepositoryTest {


    protected static final Person nick = new Person("1", "Nick", 20);
    protected static final Person chris = new Person("2", "Chris", 30, new Person("10", "Ann", 25));
    protected static final Person paul = new Person("3", "Paul", 40, new Person("11", "Mary", 25));
    protected static final Person chris2 = new Person("4", "Chris", 50);
    protected static final Person chris3 = new Person("5", "Chris", 30);
    protected static final Person paul2 = new Person("6", "Paul", 30);

    @Autowired
    protected PersonRepository personRepository;

    private List<Person> list;

    @Before
    public void init() {
        list = new ArrayList<>();
        list.add(chris);
        list.add(paul);
        list.add(chris2);
        list.add(chris3);
        list.add(paul2);
        personRepository.save(list);
    }

    @After
    public void clear() {
        personRepository.deleteAll();
    }

    @Test
    public void testFindByAgePagedCreatedQuery() {
        Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(1, 2, sorting);
        List<Person> person = personRepository.findByAge(30, pageable);
        assertEquals(1, person.size());
        assertEquals(paul2, person.get(0));

        Pageable pageable2 = new PageRequest(0, 2, sorting);
        List<Person> persons2 = personRepository.findByAge(30, pageable2);
        assertEquals(2, persons2.size());
        assertEquals(chris, persons2.get(0));
        assertEquals(chris3, persons2.get(1));
    }

    @Test
    public void testFindByAgeSortedCreatedQuery() {
        List<Person> person = personRepository.findByAge(30, new Sort(new Sort.Order(Sort.Direction.ASC, "id")));
        assertEquals(3, person.size());
        assertEquals(chris, person.get(0));
        assertEquals(chris3, person.get(1));
        assertEquals(paul2, person.get(2));
    }

    @Test
    public void testFindBySpouseNameCreatedQuery() {
        List<Person> person = personRepository.findBySpouseName("Ann");
        assertEquals(1, person.size());
        assertTrue(person.contains(chris));
    }

    @Test
    public void testFindByNameOrAgeCreatedQuery() {
        List<Person> person = personRepository.findByNameOrAge("Paul", 50);
        assertEquals(3, person.size());
        assertTrue(person.contains(paul));
        assertTrue(person.contains(paul2));
        assertTrue(person.contains(chris2));
    }

    @Test
    public void testFindByNameAndAgeCreatedQuery() {
        List<Person> person = personRepository.findByNameAndAge("Chris", 30);
        assertEquals(2, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
    }

    @Test
    public void testFindByAgeCreatedQuery() {
        List<Person> person = personRepository.findByAge(30);
        assertEquals(3, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
        assertTrue(person.contains(paul2));
    }

    @Test
    public void testFindByNameDeclaredQuery() {
        List<Person> persons = personRepository.findByName("Chris");
        assertEquals(3, persons.size());
        assertTrue(persons.contains(chris));
        assertTrue(persons.contains(chris2));
        assertTrue(persons.contains(chris3));
    }

    @Test
    public void save() {
        personRepository.save(nick);
        Person result = personRepository.findOne(nick.getId());
        assertEquals(nick, result);
    }

    @Test
    public void saveMultiple() {
        Person result2 = personRepository.findOne(chris.getId());
        Person result3 = personRepository.findOne(paul.getId());
        assertEquals(chris, result2);
        assertEquals(paul, result3);
    }

    @Test
    public void exists() {
        assertTrue(personRepository.exists(chris.getId()));
    }

    @Test
    public void count() {
        assertEquals(personRepository.count(), list.size());
    }

    @Test
    public void delete() {
        personRepository.delete(paul.getId());
        assertFalse(personRepository.exists(paul.getId()));
    }

    @Test
    public void getAll() {
        List<Person> resultList = (List<Person>) personRepository.findAll();
        assertTrue(resultList.contains(paul));
        assertTrue(resultList.contains(chris));
    }

    @Test
    public void getAllById() {
        personRepository.save(nick);
        List<String> idList = new ArrayList<>();
        idList.add(chris.getId());
        idList.add(paul.getId());
        List<Person> resultList = findAll(idList);
        assertTrue(resultList.contains(chris));
        assertTrue(resultList.contains(paul));
        assertFalse(resultList.contains(nick));
    }

    @Test
    public void testGetAllWithSorting() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        List<Person> persons = findPersons(sorting);
        assertEquals("5", persons.get(0).getId());
        assertEquals("4", persons.get(1).getId());
        assertEquals("2", persons.get(2).getId());
        assertEquals("1", persons.get(3).getId());
        assertEquals("6", persons.get(4).getId());
        assertEquals("3", persons.get(5).getId());
    }

    @Test
    public void testGetAllWithPaging() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(1, 2, sorting);
        List<Person> persons = findPersons(pageable);
        assertEquals("2", persons.get(0).getId());
        assertEquals("1", persons.get(1).getId());
    }

    @Test
    public void testGetAllWithPagingEmptyResult() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(100500, 2, sorting);
        List<Person> persons = findPersons(pageable);
        assertTrue(persons.isEmpty());
    }

    @Test
    public void testFindByAgeIs() {
        List<Person> personList = personRepository.findByAgeIs(30);
        assertTrue(personList.size() == 3);
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByNameEquals() {
        List<Person> personList = personRepository.findByNameEquals("Chris");
        assertTrue(personList.size() == 3);
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }


    //@Test
    public void testFindByAgeBetween() {
        List<Person> personList = personRepository.findByAgeBetween(35, 52);
        assertTrue(personList.size() == 2);
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(chris2));
    }

    //@Test
    public void testFindByAgeLessThan() {
        List<Person> personList = personRepository.findByAgeLessThan(30);
        System.out.println("List: " + personList);
        assertTrue(personList.size() == 3);
        assertTrue(personList.contains(nick));
    }

    //@Test
    public void testFindByAgeLessThanEqual() {
        List<Person> personList = personRepository.findByAgeLessThanEqual(30);
        System.out.println("List: " + personList);
        assertTrue(personList.size() == 6);
        assertTrue(personList.contains(nick));
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByAgeGreaterThan() {
        List<Person> personList = personRepository.findByAgeGreaterThan(40);
        assertTrue(personList.size() == 1);
        assertTrue(personList.contains(chris2));
    }

    @Test
    public void testFindByAgeGreaterThanEqual() {
        List<Person> personList = personRepository.findByAgeGreaterThanEqual(40);
        assertTrue(personList.size() == 2);
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindAllWithProjection() {
        Iterable<Person> people = personRepository.findAll(projections("name", "id"));
        ArrayList<Person> list = Lists.newArrayList(people);
        assertEquals(5, list.size());

        for (Person person : list) {
            assertNotNull(person.getName());
            assertNotNull(person.getId());
            assertNull(person.getAge());
        }
    }

    @Test
    public void testFindByQueryWithProjection(){
        List<Person> people = personRepository.findByAge(50, projections("name"));
        assertEquals(1, people.size());
        assertEquals("Chris", people.get(0).getName());
        assertNull(people.get(0).getAge());

    }

    private void prepareDataForSortingTest() {
        personRepository.save(nick);
        personRepository.delete("4");
        personRepository.delete("5");
        personRepository.delete("6");
        personRepository.save(new Person("4", "Chris", 50));
        personRepository.save(new Person("5", "Chris", 35));
        personRepository.save(new Person("6", "Paul", 45));
    }

    private List<Person> findPersons(Sort sort) {
        Iterable<Person> all = personRepository.findAll(sort);
        return Lists.newArrayList(all);

    }

    private List<Person> findPersons(Pageable pageable) {
        Page<Person> all = personRepository.findAll(pageable);
        return Lists.newArrayList(all);
    }

    private List<Person> findAll(List<String> ids) {
        Iterable<Person> persons = personRepository.findAll(ids);
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            personList.add(person);
        }
        return personList;
    }
}
