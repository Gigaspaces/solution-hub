package org.springframework.data.xap.integration;

import com.gigaspaces.document.DocumentProperties;
import com.gigaspaces.document.SpaceDocument;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.PersonDocumentRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class BaseSpaceDocumentRepositoryTest{

    @Autowired
    protected PersonDocumentRepository personDocumentRepository;

    private static String typeName = "Person";

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    protected static SpaceDocument nick;
    protected static SpaceDocument chris;
    protected static SpaceDocument paul;
    protected static SpaceDocument chris2;
    protected static SpaceDocument chris3;
    protected static SpaceDocument paul2;

    @Autowired
    protected GigaSpace client;

    private List<SpaceDocument> list;

    @BeforeClass
    public static void initPersons() throws ParseException {
        nick = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "1")
                .setProperty("name", "Nick")
                .setProperty("age", 20)
                .setProperty("birthday", sdf.parse("01/01/1994"))
                .setProperty("spouse", null)
                .setProperty("active", true));
        chris = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "2")
                .setProperty("name", "Chris")
                .setProperty("age", 30)
                .setProperty("birthday", sdf.parse("05/06/1984"))
                .setProperty("spouse", new DocumentProperties()
                        .setProperty("id", 10)
                        .setProperty("name", "Ann")
                        .setProperty("age", 25))
                .setProperty("active", false));
        paul = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "3")
                .setProperty("name", "Paul")
                .setProperty("age", 40)
                .setProperty("birthday", sdf.parse("10/06/1974"))
                .setProperty("spouse", new DocumentProperties()
                        .setProperty("id", 11)
                        .setProperty("name", "Mary")
                        .setProperty("age", 23))
                .setProperty("active", false));
        chris2 = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "4")
                .setProperty("name", "Chris")
                .setProperty("age", 50)
                .setProperty("birthday", sdf.parse("10/06/1964"))
                .setProperty("active", true));
        chris3 = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "5")
                .setProperty("name", "Chris")
                .setProperty("age", 30)
                .setProperty("birthday", sdf.parse("10/08/1984"))
                .setProperty("active", true));
        paul2 = new SpaceDocument(typeName, new DocumentProperties()
                .setProperty("id", "6")
                .setProperty("name", "Paul")
                .setProperty("age", 30)
                .setProperty("birthday", sdf.parse("02/03/1974"))
                .setProperty("active", false));
    }

    @Before
    public void init(){
        list = new ArrayList<>();
        list.add(chris);
        list.add(paul);
        list.add(chris2);
        list.add(chris3);
        list.add(paul2);
        personDocumentRepository.save(list);
    }

    @After
    public void clear() {
        personDocumentRepository.deleteAll();
    }


    @Test
    public void testFindOne() {
        SpaceDocument spaceDocument = personDocumentRepository.findOne("3");
        assertEquals(paul, spaceDocument);
    }

    @Test
    public void testFindAll() {
        List<SpaceDocument> all = (List<SpaceDocument>) personDocumentRepository.findAll();
        assertTrue(all.contains(paul));
        assertTrue(all.contains(paul2));
        assertTrue(all.contains(chris));
        assertTrue(all.contains(chris2));
        assertTrue(all.contains(chris3));
    }

    @Test
    public void testDelete() {
        personDocumentRepository.delete(paul);
        assertNull(personDocumentRepository.findOne("3"));
    }

    @Test
    public void testDeleteById() {
        personDocumentRepository.delete("3");
        assertNull(personDocumentRepository.findOne("3"));
    }

    @Test
    public void count() {
        assertEquals(personDocumentRepository.count(), list.size());
    }

    @Test
    public void exists() {
        TestCase.assertTrue(personDocumentRepository.exists(getId(chris)));
    }

    @Test
    public void saveMultiple() {
        SpaceDocument result2 = personDocumentRepository.findOne(getId(chris));
        SpaceDocument result3 = personDocumentRepository.findOne(getId(paul));
        assertEquals(chris, result2);
        assertEquals(paul, result3);
    }

    @Test
    public void save() {
        personDocumentRepository.deleteAll();
        personDocumentRepository.save(nick);
        SpaceDocument result = personDocumentRepository.findOne(getId(nick));
        assertEquals(nick, result);
    }

    @Test
    public void testFindAllById() {
        personDocumentRepository.save(nick);
        List<String> idList = new ArrayList<>();
        idList.add(getId(chris));
        idList.add(getId(paul));
        List<SpaceDocument> resultList = findAll(idList);
        TestCase.assertTrue(resultList.contains(chris));
        TestCase.assertTrue(resultList.contains(paul));
        assertFalse(resultList.contains(nick));
    }

    @Test
    public void delete() {
        personDocumentRepository.delete(getId(paul));
        assertFalse(personDocumentRepository.exists(getId(paul)));
    }

    @Test
    public void testFindByNameDeclaredQuery() {
        List<SpaceDocument> persons = personDocumentRepository.findByNameDeclaredQuery("Chris");
        assertEquals(3, persons.size());
        TestCase.assertTrue(persons.contains(chris));
        TestCase.assertTrue(persons.contains(chris2));
        TestCase.assertTrue(persons.contains(chris3));
    }

//    @Test(expected = UnsupportedOperationException.class)
//    public void testIgnoreCase(){
//        personDocumentRepository.findByNameIgnoreCase("paul");
//    }
//
//    @Test(expected = UnsupportedOperationException.class)
//    public void testIgnoreCaseInSorting(){
//        Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "id").ignoreCase());
//        Pageable pageable = new PageRequest(1, 2, sorting);
//        personDocumentRepository.findByNameEquals("paul", pageable);
//    }
//
//    @Test(expected = UnsupportedOperationException.class)
//    public void testNullHandling(){
//        Sort sorting = new Sort(new Sort.Order(Sort.Direction.ASC, "id", Sort.NullHandling.NULLS_FIRST));
//        Pageable pageable = new PageRequest(1, 2, sorting);
//        personDocumentRepository.findByNameEquals("paul", pageable);
//    }

    private List<SpaceDocument> findAll(List<String> ids) {
        Iterable<SpaceDocument> persons = personDocumentRepository.findAll(ids);
        List<SpaceDocument> personList = new ArrayList<>();
        for (SpaceDocument person : persons) {
            personList.add(person);
        }
        return personList;
    }

    private String getId(SpaceDocument person) {
        return person.getProperty("id").toString();
    }
//    @Override
//    public void testFindByNameRegex() {
//        super.testFindByNameRegex();
//    }
//
//    @Override
//    public void testFindByAgeBetweenAndNameIn() {
//        super.testFindByAgeBetweenAndNameIn();
//    }
//
//    @Override
//    public void testFindByActiveFalse() {
//        super.testFindByActiveFalse();
//    }
//
//    @Override
//    public void testFindByActiveTrue() {
//        super.testFindByActiveTrue();
//    }
//
//    @Override
//    public void testFindByNameNotIn() {
//        super.testFindByNameNotIn();
//    }
//
//    @Override
//    public void testFindByNameIn() {
//        super.testFindByNameIn();
//    }
//
//    @Override
//    public void testFindByAgeIn() {
//        super.testFindByAgeIn();
//    }
//
//    @Override
//    public void testFindByNameNot() {
//        super.testFindByNameNot();
//    }
//
//    @Override
//    public void testFindByNameOrderByAgeAndId() {
//        super.testFindByNameOrderByAgeAndId();
//    }
//
//    @Override
//    public void testFindByNameOrderByAgeAsc() {
//        super.testFindByNameOrderByAgeAsc();
//    }
//
//    @Override
//    public void testFindByNameOrderByIdDesc() {
//        super.testFindByNameOrderByIdDesc();
//    }
//
//    @Override
//    public void testFindByNameContaining() {
//        super.testFindByNameContaining();
//    }
//
//    @Override
//    public void testFindByNameEndingWith() {
//        super.testFindByNameEndingWith();
//    }
//
//    @Override
//    public void testFindByNameStartingWith() {
//        super.testFindByNameStartingWith();
//    }
//
//    @Override
//    public void testFindByNameNotLike() {
//        super.testFindByNameNotLike();
//    }
//
//    @Override
//    public void testFindByNameLike() {
//        super.testFindByNameLike();
//    }
//
//    @Override
//    public void testFindBySpouseIsNotNull() {
//        super.testFindBySpouseIsNotNull();
//    }
//
//    @Override
//    public void testFindBySpouseIsNull() {
//        super.testFindBySpouseIsNull();
//    }
//
//    @Override
//    public void testFindByBirthDayBefore() throws ParseException {
//        super.testFindByBirthDayBefore();
//    }
//
//    @Override
//    public void testFindByBirthDayAfter() throws ParseException {
//        super.testFindByBirthDayAfter();
//    }
//
//    @Override
//    public void testFindByIdsWithProjection() {
//        super.testFindByIdsWithProjection();
//    }
//
//    @Override
//    public void testFindOneWithProjection() {
//        super.testFindOneWithProjection();
//    }
//
//    @Override
//    public void testFindByQueryWithProjection() {
//        super.testFindByQueryWithProjection();
//    }
//
//    @Override
//    public void testFindAllWithProjection() {
//        super.testFindAllWithProjection();
//    }
//
//    @Override
//    public void testFindByAgeGreaterThanEqual() {
//        super.testFindByAgeGreaterThanEqual();
//    }
//
//    @Override
//    public void testFindByAgeGreaterThan() {
//        super.testFindByAgeGreaterThan();
//    }
//
//    @Override
//    public void testFindByAgeLessThanEqual() {
//        super.testFindByAgeLessThanEqual();
//    }
//
//    @Override
//    public void testFindByAgeLessThan() {
//        super.testFindByAgeLessThan();
//    }
//
//    @Override
//    public void testBetweenOrderBy() {
//        super.testBetweenOrderBy();
//    }
//
//    @Override
//    public void testBetweenAndName() {
//        super.testBetweenAndName();
//    }
//
//    @Override
//    public void testFindByAgeBetween() {
//        super.testFindByAgeBetween();
//    }
//
//    @Override
//    public void testFindByNameEquals() {
//        super.testFindByNameEquals();
//    }
//
//    @Override
//    public void testFindByAgeIs() {
//        super.testFindByAgeIs();
//    }
//
//    @Override
//    public void testFindAllWithPagingAndProjection() {
//        super.testFindAllWithPagingAndProjection();
//    }
//
//    @Override
//    public void testFindAllWithPagingEmptyResult() {
//        super.testFindAllWithPagingEmptyResult();
//    }
//
//    @Override
//    public void testFindAllWithPaging() {
//        super.testFindAllWithPaging();
//    }
//
//    @Override
//    public void testFindAllWithSortingAndProjection() {
//        super.testFindAllWithSortingAndProjection();
//    }
//
//    @Override
//    public void testFindAllWithSorting() {
//        super.testFindAllWithSorting();
//    }
//
//
//
//    @Override
//    public void testFindBySpouseAgeNamedQuery() {
//        super.testFindBySpouseAgeNamedQuery();
//    }
//
//    @Override
//    public void testFindByAgeCreatedQuery() {
//        super.testFindByAgeCreatedQuery();
//    }
//
//    @Override
//    public void testFindByNameAndAgeCreatedQuery() {
//        super.testFindByNameAndAgeCreatedQuery();
//    }
//
//    @Override
//    public void testFindByNameOrAgeCreatedQuery() {
//        super.testFindByNameOrAgeCreatedQuery();
//    }
//
//    @Override
//    public void testFindBySpouseAge() {
//        super.testFindBySpouseAge();
//    }
//
//    @Override
//    public void testFindBySpouseNameCreatedQuery() {
//        super.testFindBySpouseNameCreatedQuery();
//    }
//
//    @Override
//    public void testFindByAgeSortedCreatedQuery() {
//        super.testFindByAgeSortedCreatedQuery();
//    }
//
//    @Override
//    public void testFindByAgePagedCreatedQuery() {
//        super.testFindByAgePagedCreatedQuery();
//    }
//

}
