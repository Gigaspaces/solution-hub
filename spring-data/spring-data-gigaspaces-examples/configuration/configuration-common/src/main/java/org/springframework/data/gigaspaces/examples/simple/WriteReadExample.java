package org.springframework.data.gigaspaces.examples.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.gigaspaces.examples.model.Person;


/**
 * @author Anna_Babich.
 */
public class WriteReadExample {
    private static Logger log = LoggerFactory.getLogger(WriteReadExample.class);

    public static void launch(PersonRepository personRepository) {
        Person mike = new Person(1, "Mike", true, "accountant", 20);
        Person nick = new Person(2, "Nick", false, "manager", 27);
        log.info("Save new person " + mike + " ...");
        personRepository.save(mike);
        log.info("Save new person " + nick + " ...");
        personRepository.save(nick);
        log.info("Get persons from space: ");
        for (Person person : personRepository.findAll()) {
            log.info(person.toString());
        }
        log.info("Delete persons from space...");
        personRepository.deleteAll();
    }
}