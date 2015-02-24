package org.springframework.data.xap.examples.advanced.querydsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.stereotype.Component;

import static org.springframework.data.xap.examples.model.QPerson.person;

/**
 * @author Anna_Babich.
 */
@Component
public class QueryDslExample {

    private static Logger log = LoggerFactory.getLogger(QueryDslExample.class);

    @Autowired
    PredicatePersonRepository personRepository;

    public void run() {
        DataSet.setup(personRepository.space());

        log.info("Find person with name Nick");
        Person per = personRepository.findOne((person.name.eq("Nick")));
        log.info(per.toString());

        log.info("Find persons older than 26, sort them by name");
        for(Person p: personRepository.findAll(person.age.lt(26), person.name.asc())){
            log.info(p.toString());
        }

        log.info("Find persons by name list");
        for (Person p: personRepository.findAll(person.name.in("Nick", "Alex", "Mary"))){
            log.info(p.toString());
        }

        log.info("Find persons, which names contains J");
        for (Person p: personRepository.findAll(person.name.like("%J%"))){
            log.info(p.toString());
        }

        log.info("Find persons, which names contains 4 letters");
        for (Person p: personRepository.findAll(person.name.matches("\\w{4}"))){
            log.info(p.toString());
        }
        DataSet.cleanup(personRepository.space());
    }
}
