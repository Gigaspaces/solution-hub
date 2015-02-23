package org.springframework.data.xap.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.examples.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void save(Person person){
        personRepository.save(person);
    }

    public void save(Iterable<Person> persons){
        personRepository.save(persons);
    }

    public Iterable<Person> findAll(){
        return personRepository.findAll();
    }

    public void deleteAll(){
        personRepository.deleteAll();
    }

    public List<Person> findByPositionAndActiveTrue(String position){
        return personRepository.findByPositionAndActiveTrue(position);
    }
}
