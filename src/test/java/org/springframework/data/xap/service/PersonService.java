package org.springframework.data.xap.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.xap.model.Person;

import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonService {

    void addPerson(Person p);

    void addPersons(List<Person> persons);

    Person getById(String id);

    boolean exists(String id);

    List<Person> getAll();

    List<Person> getAll(List<String> ids);

    long count();

    void delete(String id);

    void deleteAll();

    List<Person> findPersons(Sort sort);

    List<Person> findPersons(Pageable pageable);

    Person findByName(String name);

}
