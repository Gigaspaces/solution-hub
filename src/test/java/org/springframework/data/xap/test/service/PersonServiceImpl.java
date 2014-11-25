package org.springframework.data.xap.test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.test.repository.PersonRepository;
import org.springframework.data.xap.test.model.Person;
import org.springframework.stereotype.Component;

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
}
