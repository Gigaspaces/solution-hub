package org.springframework.data.xap.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.examples.repository.PersonRepository;
import org.springframework.data.xap.examples.repository.PredicatePersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.xap.examples.model.QPerson.person;
import static org.springframework.data.xap.querydsl.QChangeSet.changeSet;

/**
 * @author Anna_Babich.
 */
@Component
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PredicatePersonRepository predicatePersonRepository;

    public void save(Person person) {
        personRepository.save(person);
    }

    public void save(Iterable<Person> persons) {
        personRepository.save(persons);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }

    public List<Person> findByPositionAndActiveTrue(String position) {
        return personRepository.findByPositionAndActiveTrue(position);
    }

    public List<Person> findByAgeGreaterThan(Integer age, Sort sort) {
        return personRepository.findByAgeGreaterThan(age, sort);
    }

    public void changePersonAgeAndStatus(Person per, Integer incrementValue) {
        predicatePersonRepository.change(
                person.name.eq(per.getName()),
                changeSet().increment(person.age, incrementValue).set(person.active, false)
        );
    }

    public Person findOne(Integer id) {
        return personRepository.findOne(id);
    }
}
