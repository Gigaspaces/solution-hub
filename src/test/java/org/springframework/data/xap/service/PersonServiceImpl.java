package org.springframework.data.xap.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.test.repository.PersonRepository;
import org.springframework.data.xap.test.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
@Component
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void addPerson(Person p) {
        personRepository.save(p);
    }

    @Override
    public void addPersons(List<Person> persons) {
        personRepository.save(persons);
    }

    @Override
    public Person getById(String id) {
        return personRepository.findOne(id);
    }

    @Override
    public boolean exists(String id) {
        return personRepository.exists(id);
    }

    @Override
    public List<Person> getAll() {
        return (List<Person>)personRepository.findAll();
    }

    @Override
    public List<Person> getAll(List<String> ids) {
        Iterable<Person> persons = personRepository.findAll(ids);
        List<Person> personList = new ArrayList<>();
        for(Person person:persons){
            personList.add(person);
        }
        return personList;
    }


    @Override
    public long count() {
        return personRepository.count();
    }

    @Override
    public void delete(String id) {
        personRepository.delete(id);
    }


    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }

}
