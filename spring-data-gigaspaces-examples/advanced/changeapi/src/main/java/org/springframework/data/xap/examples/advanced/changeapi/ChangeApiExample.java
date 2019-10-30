package org.springframework.data.xap.examples.advanced.changeapi;


import com.gigaspaces.client.ChangeSet;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.stereotype.Component;
import static org.springframework.data.xap.examples.model.QPerson.person;
import static org.springframework.data.xap.querydsl.QChangeSet.changeSet;

/**
 * @author Anna_Babich.
 */
@Component
public class ChangeApiExample {

    private static Logger log = LoggerFactory.getLogger(ChangeApiExample.class);

    @Autowired
    PredicatePersonRepository personRepository;

    public void run() {
        GigaSpace space = personRepository.space();
        DataSet.setup(space);

        log.info("CHANGE API USING QUERY DSL");

        log.info("Increment age");
        log.info("Before changes " + personRepository.findOne(1).toString());
        personRepository.change(
                person.name.eq(DataSet.nick.getName()),
                changeSet().increment(person.age, 5)
        );
        log.info("After changes " + personRepository.findOne(1).toString());

        log.info("Set field name");
        log.info("Before changes " + personRepository.findOne(2).toString());
        personRepository.change(
                person.id.eq(2),
                changeSet().set(person.name, "Maria")
        );
        log.info("After changes " + personRepository.findOne(2).toString());

        log.info("CHANGE API USING NATIVE XAP API");

        log.info("Decrement Paul age");
        log.info("Before changes " + personRepository.findOne(4).toString());
        space.change(DataSet.paul, new ChangeSet().decrement("age", 2));
        log.info("Before changes " + personRepository.findOne(4).toString());

        DataSet.cleanup(space);
    }


}
