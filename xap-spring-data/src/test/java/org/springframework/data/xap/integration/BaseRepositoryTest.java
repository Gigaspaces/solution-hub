package org.springframework.data.xap.integration;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.j_spaces.core.LeaseContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.PersonRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.*;
import static org.springframework.data.xap.repository.query.Projection.projections;

/**
 * @author Anna_Babich
 */
public abstract class BaseRepositoryTest {

    protected static Person nick;
    protected static Person chris;
    protected static Person paul;
    protected static Person chris2;
    protected static Person chris3;
    protected static Person paul2;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Autowired
    protected PersonRepository personRepository;

    protected GigaSpace client;

    private List<Person> list;

    @BeforeClass
    public static void initPersons() throws ParseException {
        nick = new Person("1", "Nick", 20, sdf.parse("01/01/1994"), true);
        chris = new Person("2", "Chris", 30, new Person("10", "Ann", 25), sdf.parse("05/06/1984"), false);
        paul = new Person("3", "Paul", 40, new Person("11", "Mary", 24), sdf.parse("10/06/1974"), false);
        chris2 = new Person("4", "Chris", 50, sdf.parse("10/06/1964"), true);
        chris3 = new Person("5", "Chris", 30, sdf.parse("10/08/1984"), true);
        paul2 = new Person("6", "Paul", 30, sdf.parse("02/03/1984"), false);
    }

    @Before
    public void init() {
        client = personRepository.space();
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
    public void testFindBySpouseAge() {
        List<Person> personList = personRepository.findBySpouse_Age(24);
        assertEquals(1, personList.size());
        assertTrue(personList.contains(paul));
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
    public void testFindBySpouseAgeNamedQuery() {
        List<Person> person = personRepository.findBySpouseAge(25);
        assertEquals(1, person.size());
        assertTrue(person.contains(chris));
    }

    @Test
    public void testFindByNameDeclaredQuery() {
        List<Person> persons = personRepository.findByNameCustomQuery("Chris");
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
    public void testFindAll() {
        List<Person> resultList = (List<Person>) personRepository.findAll();
        assertTrue(resultList.contains(paul));
        assertTrue(resultList.contains(chris));
    }

    @Test
    public void testFindAllById() {
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
    public void testFindAllWithSorting() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        List<Person> persons = findPersons(sorting);
        assertSortedByName(persons);
    }

    @Test
    public void testFindAllWithSortingAndProjection() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        List<Person> persons = Lists.newArrayList(personRepository.findAll(sorting, projections("name", "id")));
        assertSortedByName(persons);
        for (Person person : persons) {
            assertNull(person.getAge());
        }
    }

    private void assertSortedByName(List<Person> persons) {
        assertEquals("5", persons.get(0).getId());
        assertEquals("4", persons.get(1).getId());
        assertEquals("2", persons.get(2).getId());
        assertEquals("1", persons.get(3).getId());
        assertEquals("6", persons.get(4).getId());
        assertEquals("3", persons.get(5).getId());
    }

    @Test
    public void testFindAllWithPaging() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(1, 2, sorting);
        List<Person> persons = findPersons(pageable);
        assertEquals(chris.getId(), persons.get(0).getId());
        assertEquals(nick.getId(), persons.get(1).getId());
    }

    @Test
    public void testFindAllWithPagingEmptyResult() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(100500, 2, sorting);
        List<Person> persons = findPersons(pageable);
        assertTrue(persons.isEmpty());
    }

    @Test
    public void testFindAllWithPagingAndProjection() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting = new Sort(orders);
        Pageable pageable = new PageRequest(1, 2, sorting);
        List<Person> persons = Lists.newArrayList(personRepository.findAll(pageable, projections("name")));
        assertEquals(chris.getName(), persons.get(0).getName());
        assertEquals(nick.getName(), persons.get(1).getName());

        assertAllNullExceptName(persons);
    }

    @Test
    public void testFindByAgeIs() {
        List<Person> personList = personRepository.findByAgeIs(30);
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByNameEquals() {
        List<Person> personList = personRepository.findByNameEquals("Chris");
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }


    @Test
    public void testFindByAgeBetween() {
        List<Person> personList = personRepository.findByAgeBetween(35, 52);
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(chris2));
    }

    @Test
    public void testBetweenAndName() {
        List<Person> personList = personRepository.findByAgeBetweenAndName(35, 52, "Paul");
        assertEquals(1, personList.size());
        assertEquals(paul, personList.get(0));
    }

    @Test
    public void testBetweenOrderBy() {
        List<Person> personList = personRepository.findByAgeBetweenOrderByAgeAsc(35, 52);
        assertEquals(2, personList.size());
        assertEquals(paul, personList.get(0));
        assertEquals(chris2, personList.get(1));
    }

    @Test
    public void testFindByAgeLessThan() {
        List<Person> personList = personRepository.findByAgeLessThan(40);
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByAgeLessThanEqual() {
        List<Person> personList = personRepository.findByAgeLessThanEqual(40);
        assertEquals(4, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByAgeGreaterThan() {
        List<Person> personList = personRepository.findByAgeGreaterThan(40);
        assertEquals(1, personList.size());
        assertTrue(personList.contains(chris2));
    }

    @Test
    public void testFindByAgeGreaterThanEqual() {
        List<Person> personList = personRepository.findByAgeGreaterThanEqual(40);
        assertEquals(2, personList.size());
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
    public void testFindByQueryWithProjection() {
        List<Person> people = personRepository.findByAge(50, projections("name"));
        assertEquals(1, people.size());
        assertEquals("Chris", people.get(0).getName());
        assertNull(people.get(0).getAge());

    }

    @Test
    public void testFindOneWithProjection() {
        Person person = personRepository.findOne(chris.getId(), projections("name"));
        assertEquals(chris.getName(), person.getName());
        assertNull(person.getAge());
    }

    @Test
    public void testFindByIdsWithProjection() {
        List<String> idList = new ArrayList<>();
        idList.add(chris.getId());
        idList.add(paul.getId());

        Iterable<Person> people = personRepository.findAll(idList, projections("name"));
        Set<Person> set = Sets.newHashSet(people.iterator());

        assertEquals(2, set.size());

        assertAllNullExceptName(set);
    }

    @Test
    public void testFindByBirthDayAfter() throws ParseException {
        List<Person> personList = personRepository.findByBirthdayAfter(sdf.parse("03/04/1984"));
        assertEquals(2, personList.size());
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(chris));
    }

    @Test
    public void testFindByBirthDayBefore() throws ParseException {
        List<Person> personList = personRepository.findByBirthdayBefore(sdf.parse("03/04/1984"));
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindBySpouseIsNull() {
        List<Person> personList = personRepository.findBySpouseIsNull();
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindBySpouseIsNotNull() {
        List<Person> personList = personRepository.findBySpouseIsNotNull();
        assertEquals(2, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByNameLike() {
        List<Person> personList = personRepository.findByNameLike("%au%");
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByNameNotLike() {
        List<Person> personList = personRepository.findByNameNotLike("%ri%");
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByNameStartingWith() {
        List<Person> personList = personRepository.findByNameStartingWith("P");
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByNameEndingWith() {
        List<Person> personList = personRepository.findByNameEndingWith("is");
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }

    @Test
    public void testFindByNameContaining() {
        List<Person> personList = personRepository.findByNameContaining("Ch");
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }

    @Test
    public void testFindByNameOrderByIdDesc() {
        List<Person> personList = personRepository.findByNameOrderByIdDesc("Chris");
        assertEquals(3, personList.size());
        assertEquals(chris3, personList.get(0));
        assertEquals(chris2, personList.get(1));
        assertEquals(chris, personList.get(2));
    }

    @Test
    public void testFindByNameOrderByAgeAsc() {
        List<Person> personList = personRepository.findByNameOrderByAgeAsc("Paul");
        assertEquals(2, personList.size());
        assertEquals(paul2, personList.get(0));
        assertEquals(paul, personList.get(1));
    }

    @Test
    public void testFindByNameOrderByAgeAndId() {
        List<Person> personList = personRepository.findByNameOrderByAgeAscIdDesc("Chris");
        assertEquals(3, personList.size());
        assertEquals(chris3, personList.get(0));
        assertEquals(chris, personList.get(1));
        assertEquals(chris2, personList.get(2));
    }

    @Test
    public void testFindByNameNot() {
        List<Person> personList = personRepository.findByNameNot("Chris");
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByAgeIn() {
        List<Person> personList = personRepository.findByAgeIn(Arrays.asList(50, 40));
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(chris2));
    }

    @Test
    public void testFindByNameIn() {
        personRepository.save(nick);
        List<Person> personList = personRepository.findByNameIn(Arrays.asList("Paul", "Nick"));
        assertEquals(3, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(nick));
    }

    @Test
    public void testFindByNameNotIn() {
        personRepository.save(nick);
        List<Person> personList = personRepository.findByNameNotIn(Arrays.asList("Paul", "Nick"));
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }

    @Test
    public void testFindByActiveTrue() {
        List<Person> personList = personRepository.findByActiveTrue();
        assertEquals(2, personList.size());
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }


    @Test
    public void testFindByActiveFalse() {
        List<Person> personList = personRepository.findByActiveFalse();
        assertEquals(3, personList.size());
        assertTrue(personList.contains(chris));
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void testFindByAgeBetweenAndNameIn() {
        List<Person> personList = personRepository.findByAgeBetweenAndNameIn(40, 50, Arrays.asList("Paul", "Nick"));
        assertEquals(1, personList.size());
        assertTrue(personList.contains(paul));
    }

    @Test
    public void testFindByNameRegex() {
        personRepository.save(nick);
        List<Person> personList = personRepository.findByNameRegex("\\w{4}");
        assertEquals(3, personList.size());
        assertTrue(personList.contains(nick));
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIgnoreCase() {
        personRepository.findByNameIgnoreCase("paul");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIgnoreCaseInSorting() {
        Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "id").ignoreCase());
        Pageable pageable = new PageRequest(1, 2, sorting);
        personRepository.findByNameEquals("paul", pageable);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNullHandling() {
        Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "id", Sort.NullHandling.NULLS_FIRST));
        Pageable pageable = new PageRequest(1, 2, sorting);
        personRepository.findByNameEquals("paul", pageable);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testExists() {
        personRepository.findByNameExists(true);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsNear() {
        personRepository.findByAgeIsNear(10);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsWithin() {
        personRepository.findByAgeIsWithin(10, 20);
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

    @Test
    public void writeWithLeaseTest() throws InterruptedException {
        personRepository.save(nick, 100, TimeUnit.MILLISECONDS);
        assertEquals(nick, personRepository.findOne(nick.getId()));
        Thread.sleep(200);
        assertNull(personRepository.findOne(nick.getId()));

        personRepository.save(nick);
        Thread.sleep(200);
        assertNotNull(personRepository.findOne(nick.getId()));
    }

    @Test
    public void writeMultipleWithLeaseTest() throws InterruptedException {
        personRepository.deleteAll();
        personRepository.save(Arrays.asList(nick, paul), 100, TimeUnit.MILLISECONDS);
        assertEquals(nick, personRepository.findOne(nick.getId()));
        assertEquals(paul, personRepository.findOne(paul.getId()));
        Thread.sleep(200);
        assertNull(personRepository.findOne(nick.getId()));
        assertNull(personRepository.findOne(paul.getId()));
    }

    @Test
    public void takeTest(){
        Person result = personRepository.takeOne(paul.getId());
        assertEquals(paul, result);
        Person result2 = personRepository.findOne(paul.getId());
        assertNull(result2);
    }

    @Test
    public void takeTestMultiple(){
        List<Person> result = Lists.newArrayList(personRepository.takeAll(Arrays.asList(paul.getId(), chris.getId())));
        assertTrue(result.contains(paul));
        assertTrue(result.contains(chris));
        Person person1 = personRepository.findOne(paul.getId());
        Person person2 = personRepository.findOne(chris.getId());
        assertNull(person1);
        assertNull(person2);
    }

    @Test
    public void takeAllTest(){
        List<Person> result = Lists.newArrayList(personRepository.takeAll());
        assertEquals(5, result.size());
        assertTrue(result.contains(paul));
        assertTrue(result.contains(chris));
        Person person1 = personRepository.findOne(paul.getId());
        Person person2 = personRepository.findOne(chris.getId());
        assertNull(person1);
        assertNull(person2);
    }

    @Test
    public void testLongLease() {
        LeaseContext<Person> lease = personRepository.save(nick, 10, TimeUnit.DAYS);
        assertEquals(Arrays.asList(nick), personRepository.findByNameEquals(nick.getName()));
        long expectedExpiration = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10);
        assertTrue(expectedExpiration - 1000 < lease.getExpiration() && expectedExpiration + 1000 > lease.getExpiration());
    }

    private void assertAllNullExceptName(Collection<Person> list) {
        for (Person person : list) {
            assertNotNull(person.getName());
            assertNull(person.getId());
            assertNull(person.getSpouse());
            assertNull(person.getAge());
        }
    }

}