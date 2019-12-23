package org.springframework.data.gigaspaces.integration.document;

import com.gigaspaces.document.SpaceDocument;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.model.Person;
import org.springframework.data.gigaspaces.model.PersonDocument;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.MethodOrderer.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.gigaspaces.repository.query.Projection.projections;

@TestMethodOrder(Alphanumeric.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class SpaceDocumentRepositoryTest {
    protected static PersonDocument nick;
    protected static PersonDocument chris;
    protected static PersonDocument paul;
    protected static PersonDocument chris2;
    protected static PersonDocument chris3;
    protected static PersonDocument paul2;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Autowired
    protected PersonDocumentRepository personRepository;

    protected GigaSpace client;

    private List<PersonDocument> list;

    @BeforeAll
    public static void initPersons() throws ParseException {
        nick = personDocument("1", "Nick", 20, null, sdf.parse("01/01/1994"), true, null);
        chris = personDocument("2", "Chris", 30, new Person("10", "Ann", 25), sdf.parse("05/06/1984"), false, "custom1");
        paul = personDocument("3", "Paul", 40, new Person("11", "Mary", 24), sdf.parse("10/06/1974"), false, "custom2");
        chris2 = personDocument("4", "Chris", 50, null, sdf.parse("10/06/1964"), true, null);
        chris3 = personDocument("5", "Chris", 30, null, sdf.parse("10/08/1984"), true, null);
        paul2 = personDocument("6", "Paul", 30, null, sdf.parse("02/03/1984"), false, null);
    }

    private static PersonDocument personDocument(String id, String name, Integer age, Person spouse, Date birthday, Boolean active, String customField) {
        PersonDocument personDocument = new PersonDocument();
        personDocument
                .setProperty("id", id)
                .setProperty("name", name)
                .setProperty("age", age)
                .setProperty("birthday", birthday)
                .setProperty("active", active);
        if (spouse != null) {
            personDocument.setProperty("spouse", spouse);
        }
        if (customField != null) {
            personDocument.setProperty("customField", customField);
        }
        return personDocument;
    }

    @BeforeEach
    public void init() {
        client = personRepository.space();
        list = new ArrayList<>();
        list.add(chris);
        list.add(paul);
        list.add(chris2);
        list.add(chris3);
        list.add(paul2);
        personRepository.saveAll(list);
    }

    @AfterEach
    public void clear() {
        personRepository.deleteAll();
    }


    @DisplayName("01 - Test find one just after save one")
    @Test
    public void test01Save() {
        personRepository.save(nick);
        PersonDocument result = personRepository.findOne(nick.getId());
        assertEquals(nick, result);
    }

    @DisplayName("02 - test find object after save multiple")
    @Test
    public void test02SaveMultiple() {
        SpaceDocument result2 = personRepository.findOne(chris.getId());
        SpaceDocument result3 = personRepository.findOne(paul.getId());
        assertEquals(chris, result2);
        assertEquals(paul, result3);
    }

    @DisplayName("03 - test finding after write with lease")
    @Test
    public void test03WriteWithLease() throws InterruptedException {
        personRepository.save(nick, 400, TimeUnit.MILLISECONDS);
        assertEquals(nick, personRepository.findOne(nick.getId()));
        Thread.sleep(600);
        assertNull(personRepository.findOne(nick.getId()));

        personRepository.save(nick);
        Thread.sleep(700);
        assertNotNull(personRepository.findOne(nick.getId()));
    }

    @DisplayName("04 - test finding after writemultiple with lease")
    @Test
    public void test04WriteMultipleWithLease() throws InterruptedException {
        personRepository.deleteAll();
        personRepository.save(Arrays.asList(nick, paul), 400, TimeUnit.MILLISECONDS);
        assertEquals(nick, personRepository.findOne(nick.getId()));
        assertEquals(paul, personRepository.findOne(paul.getId()));
        Thread.sleep(600);
        assertNull(personRepository.findOne(nick.getId()));
        assertNull(personRepository.findOne(paul.getId()));
    }

    @DisplayName("05 - test findAll")
    @Test
    public void test05FindAll() {
        List<PersonDocument> resultList = (List<PersonDocument>) personRepository.findAll();
        assertTrue(resultList.contains(paul));
        assertTrue(resultList.contains(chris));
        assertEquals(list.size(),resultList.size());
    }

    @DisplayName("06 - test findByIds")
    @Test
    public void test06FindByIds() {
        personRepository.save(nick);
        List<PersonDocument> resultList = findByIds(Arrays.asList(chris.getId(), paul.getId()));
        assertTrue(resultList.contains(chris));
        assertTrue(resultList.contains(paul));
        assertFalse(resultList.contains(nick));
        assertEquals(2,resultList.size());
    }

    @DisplayName("07 - test findAll with sorting")
    @Test
    public void test07FindAllWithSorting() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting =  Sort.by(orders);
        List<PersonDocument> persons = findWithSort(sorting);
        assertSortedByName(persons);
    }

    @DisplayName("08 - test findAll with sorting and projection")
    @Test
    public void test08FindAllWithSortingAndProjection() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting =  Sort.by(orders);
        List<PersonDocument> persons = Lists.newArrayList(personRepository.findAll(sorting, projections("name", "id")));
        assertSortedByName(persons);
        for (PersonDocument person : persons) {
            assertNull(person.getAge());
        }
    }

    @DisplayName("09 - test findAll with paging")
    @Test
    public void test09FindAllWithPaging() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting =  Sort.by(orders);
        Pageable pageable =  PageRequest.of(1, 2, sorting);
        List<PersonDocument> persons = findWithPageable(pageable);
        assertEquals(chris.getId(), persons.get(0).getId());
        assertEquals(nick.getId(), persons.get(1).getId());
    }

    @DisplayName("10 - test findAll with paging (empty result)")
    @Test
    public void test10FindAllWithPagingEmptyResult() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting =  Sort.by(orders);
        Pageable pageable =  PageRequest.of(100500, 2, sorting);
        List<PersonDocument> persons = findWithPageable(pageable);
        assertTrue(persons.isEmpty());
    }

    @DisplayName("11 - Test findAll with paging and projection")
    @Test
    public void test11FindAllWithPagingAndProjection() {
        prepareDataForSortingTest();
        List<Sort.Order> orders = Lists.newArrayList(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sorting =  Sort.by(orders);
        Pageable pageable =  PageRequest.of(1, 2, sorting);
        List<PersonDocument> persons = Lists.newArrayList(personRepository.findAll(pageable, projections("name")));
        assertEquals(chris.getName(), persons.get(0).getName());
        assertEquals(nick.getName(), persons.get(1).getName());

        assertAllNullExceptName(persons);
    }

    @Test
    public void test12FindAllWithProjection() {
        Iterable<PersonDocument> people = personRepository.findAll(projections("name", "id"));
        ArrayList<PersonDocument> list = Lists.newArrayList(people);
        assertEquals(5, list.size());

        for (PersonDocument person : list) {
            assertNotNull(person.getName());
            assertNotNull(person.getId());
            assertNull(person.getAge());
        }
    }

    @Test
    public void test13FindOneWithProjection() {
        PersonDocument person = personRepository.findOne(chris.getId(), projections("name"));
        assertEquals(chris.getName(), person.getName());
        assertNull(person.getAge());
    }

    @Test
    public void test14FindByIdsWithProjection() {
        List<String> idList = new ArrayList<>();
        idList.add(chris.getId());
        idList.add(paul.getId());

        Iterable<PersonDocument> people = personRepository.findAll(idList, projections("name"));
        Set<PersonDocument> set = Sets.newHashSet(people.iterator());

        assertEquals(2, set.size());

        assertAllNullExceptName(set);
    }

    @Test
    public void test15Exists() {
        assertTrue(personRepository.existsById(chris.getId()));
    }

    @Test
    public void test16Count() {
        assertEquals(list.size(), personRepository.count());
    }

    @Test
    public void test17Delete() {
        personRepository.deleteById(paul.getId());
        assertFalse(personRepository.existsById(paul.getId()));
    }

    @Test
    public void test18TakeById() {
        SpaceDocument result = personRepository.takeOne(paul.getId());
        assertEquals(paul, result);
        SpaceDocument result2 = personRepository.findOne(paul.getId());
        assertNull(result2);
    }

    @Test
    public void test19TakeMultipleById() {
        List<PersonDocument> result = Lists.newArrayList(personRepository.takeAll(Arrays.asList(paul.getId(), chris.getId())));
        assertTrue(result.contains(paul));
        assertTrue(result.contains(chris));
        SpaceDocument person1 = personRepository.findOne(paul.getId());
        SpaceDocument person2 = personRepository.findOne(chris.getId());
        assertNull(person1);
        assertNull(person2);
    }

    @Test
    public void test20TakeAll() {
        List<PersonDocument> result = Lists.newArrayList(personRepository.takeAll());
        assertEquals(result.size(), 5);
        assertTrue(result.contains(paul));
        assertTrue(result.contains(chris));
        SpaceDocument person1 = personRepository.findOne(paul.getId());
        SpaceDocument person2 = personRepository.findOne(chris.getId());
        assertNull(person1);
        assertNull(person2);
    }

    @Test
    public void test21FindByAgeSortedDeclaredQuery() {
        List<PersonDocument> person = personRepository.findByAgeSortedById(30);
        assertEquals(3, person.size());
        assertEquals(chris, person.get(0));
        assertEquals(chris3, person.get(1));
        assertEquals(paul2, person.get(2));
    }

    @Test
    public void test22FindBySpouseNameDeclaredQuery() {
        List<PersonDocument> person = personRepository.findBySpouseName("Ann");
        assertEquals(1, person.size());
        assertTrue(person.contains(chris));
    }

    @Test
    public void test23FindByCustomFieldDeclaredQuery() {
        List<PersonDocument> person = personRepository.findByCustomField("custom1");
        assertEquals(1, person.size());
        assertTrue(person.contains(chris));
    }

    @Test
    public void test24FindByNameOrAgeDeclaredQuery() {
        List<PersonDocument> person = personRepository.findByNameOrAge("Paul", 50);
        assertEquals(3, person.size());
        assertTrue(person.contains(paul));
        assertTrue(person.contains(paul2));
        assertTrue(person.contains(chris2));
    }

    @Test
    public void test25FindByAgeDeclaredQuery() {
        List<PersonDocument> person = personRepository.findByAge(30);
        assertEquals(3, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
        assertTrue(person.contains(paul2));
    }

    @Test
    public void test26FindByNameAndAgeDeclaredQuery() {
        List<PersonDocument> person = personRepository.findByNameAndAge("Chris", 30);
        assertEquals(2, person.size());
        assertTrue(person.contains(chris));
        assertTrue(person.contains(chris3));
    }

    @Test
    public void test27FindByNameDeclaredQuery() {
        List<PersonDocument> persons = personRepository.findByName("Chris");
        assertEquals(3, persons.size());
        assertTrue(persons.contains(chris));
        assertTrue(persons.contains(chris2));
        assertTrue(persons.contains(chris3));
    }

    @Test
    public void test28FindByAgeBetweenDeclaredQuery() {
        List<PersonDocument> personList = personRepository.findByAgeBetween(35, 52);
        assertEquals(2, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(chris2));
    }

    @Test
    public void test29FindByDeclaredQueryWithProjection() {
        List<PersonDocument> people = personRepository.findByAge(50, projections("name"));
        assertEquals(1, people.size());
        assertEquals("Chris", people.get(0).getName());
        assertNull(people.get(0).getAge());
    }

    @Test
    public void test30FindByNameInDeclaredQuery() {
        personRepository.save(nick);
        List<PersonDocument> personList = personRepository.findByNameIn(Arrays.asList("Paul", "Nick"));
        assertEquals(3, personList.size());
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
        assertTrue(personList.contains(nick));
    }

    @Test
    public void test31FindByActiveTrueDeclaredQuery() {
        List<PersonDocument> personList = personRepository.findByActiveTrue();
        assertEquals(2, personList.size());
        assertTrue(personList.contains(chris2));
        assertTrue(personList.contains(chris3));
    }

    @Test
    public void test32FindByNameRegexDeclaredQuery() {
        personRepository.save(nick);
        List<PersonDocument> personList = personRepository.findByNameRegex("\\w{4}");
        assertEquals(3, personList.size());
        assertTrue(personList.contains(nick));
        assertTrue(personList.contains(paul));
        assertTrue(personList.contains(paul2));
    }

    @Test
    public void test33FindBySpouseAgeNamedQuery() {
        List<PersonDocument> person = personRepository.findBySpouseAge(25);
        assertEquals(1, person.size());
        assertTrue(person.contains(chris));
    }

    private void prepareDataForSortingTest() {
        personRepository.save(nick);
        personRepository.deleteById("4");
        personRepository.deleteById("5");
        personRepository.deleteById("6");
        personRepository.save(new PersonDocument("4", "Chris", 50));
        personRepository.save(new PersonDocument("5", "Chris", 35));
        personRepository.save(new PersonDocument("6", "Paul", 45));
    }

    private List<PersonDocument> findWithSort(Sort sort) {
        Iterable<PersonDocument> all = personRepository.findAll(sort);
        return Lists.newArrayList(all);
    }

    private List<PersonDocument> findWithPageable(Pageable pageable) {
        Page<PersonDocument> all = personRepository.findAll(pageable);
        return Lists.newArrayList(all);
    }

    private List<PersonDocument> findByIds(List<String> ids) {
        Iterable<PersonDocument> persons = personRepository.findAll(ids);
        List<PersonDocument> personList = new ArrayList<>();
        for (PersonDocument person : persons) {
            personList.add(person);
        }
        return personList;
    }

    private void assertSortedByName(List<PersonDocument> persons) {
        assertEquals("5", persons.get(0).getId());
        assertEquals("4", persons.get(1).getId());
        assertEquals("2", persons.get(2).getId());
        assertEquals("1", persons.get(3).getId());
        assertEquals("6", persons.get(4).getId());
        assertEquals("3", persons.get(5).getId());
    }

    private void assertAllNullExceptName(Collection<PersonDocument> list) {
        for (PersonDocument person : list) {
            assertNotNull(person.getName());
            assertNull(person.getId());
            assertNull(person.getSpouse());
            assertNull(person.getAge());
        }
    }

}