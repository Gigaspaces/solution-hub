package org.springframework.data.gigaspaces.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.model.Person;
import org.springframework.data.gigaspaces.repository.query.Projection;

import java.util.Date;
import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends GigaspacesRepository<Person, String> {

    @Query("name=?")
    List<Person> findByNameCustomQuery(String name);

    List<Person> findByAge(Integer age);

    List<Person> findByNameAndAge(String name, Integer age);

    List<Person> findByNameOrAge(String name, Integer age);

    List<Person> findBySpouseName(String name);

    List<Person> findByAge(Integer age, Sort sort);

    List<Person> findByAge(Integer age, Pageable pageable);

    List<Person> findByAgeIs(Integer age);

    List<Person> findByNameEquals(String name);

    List<Person> findByNameEquals(String name, Pageable pageable);

    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);

    List<Person> findByAgeLessThan(Integer age);

    List<Person> findByAgeLessThanEqual(Integer age);

    List<Person> findByAgeGreaterThan(Integer age);

    List<Person> findByAgeGreaterThanEqual(Integer age);

    List<Person> findByAge(Integer age, Projection projection);

    List<Person> findByBirthdayAfter(Date date);

    List<Person> findByBirthdayBefore(Date date);

    List<Person> findBySpouseIsNull();

    List<Person> findBySpouseIsNotNull();

    List<Person> findByNameLike(String namePattern);

    List<Person> findByNameNotLike(String namePattern);

    List<Person> findByNameStartingWith(String namePart);

    List<Person> findByNameEndingWith(String namePart);

    List<Person> findByNameContaining(String namePart);

    List<Person> findByNameOrderByIdDesc(String name);

    List<Person> findByNameOrderByAgeAsc(String name);

    List<Person> findByNameOrderByAgeAscIdDesc(String name);

    List<Person> findByNameNot(String name);

    List<Person> findByAgeIn(List<Integer> ageList);

    List<Person> findByActiveTrue();

    List<Person> findByActiveFalse();

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findBySpouseAge(Integer age);

    List<Person> findByAgeBetweenAndName(Integer min, Integer max, String name);

    List<Person> findByAgeBetweenOrderByAgeAsc(Integer min, Integer max);

    List<Person> findByNameIn(List<String> names);

    List<Person> findByNameNotIn(List<String> names);

    List<Person> findByAgeBetweenAndNameIn(Integer min, Integer max, List<String> names);

    List<Person> findByNameRegex(String regex);

    List<Person> findBySpouse_Age(Integer age);

    List<Person> findByNameExists(boolean exists);

    List<Person> findByAgeIsNear(Integer nearAge);

    List<Person> findByAgeIsWithin(Integer minAge, Integer maxAge);
    
}
