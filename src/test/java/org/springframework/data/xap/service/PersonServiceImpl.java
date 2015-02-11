package org.springframework.data.xap.service;




import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.PersonRepository;
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
        return (List<Person>) personRepository.findAll();
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

    @Override
    public List<Person> findPersons(Sort sort) {
        Iterable<Person> all = personRepository.findAll(sort);
        return Lists.newArrayList(all);
    }

    @Override
    public List<Person> findPersons(Pageable pageable) {
        Page<Person> all = personRepository.findAll(pageable);
        return Lists.newArrayList(all);
    }

    @Override
    public Person findByName(String name) {
        return personRepository.findByName(name);
    }
}
