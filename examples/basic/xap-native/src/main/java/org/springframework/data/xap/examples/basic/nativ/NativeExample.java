package org.springframework.data.xap.examples.basic.nativ;

import com.gigaspaces.client.WriteModifiers;
import org.openspaces.core.EntryAlreadyInSpaceException;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.stereotype.Component;

/**
 * @author Anna_Babich.
 */
@Component
public class NativeExample {

    private static Logger log = LoggerFactory.getLogger(NativeMain.class);

    @Autowired
    private PersonRepository personRepository;

    public void run() throws InterruptedException {
        log.info("You can use get GigaSpace instance from repository and use advanced XAP features");
        GigaSpace space = personRepository.space();

        log.info("We are writing an object to the space with 4 seconds to live");
        space.write(DataSet.nick, 4000);
        log.info("Try to get this object immediately: " + space.readById(Person.class, 1));
        log.info("Wait 5 seconds.. ");
        Thread.sleep(5000);
        log.info("Try to get this object again: " + space.readById(Person.class, 1));

        log.info("Write Mary to the space.. ");
        space.write(DataSet.mary);
        try {
            log.info("You can use write/read modifiers for change behavior of this operation. Next modifier don't allow write, if object already exists. ");
            log.info("Try to write Mary again.. ");
            space.write(DataSet.mary, WriteModifiers.WRITE_ONLY);
        } catch (EntryAlreadyInSpaceException e) {
            log.error("Entry already in space!");
        }

        log.info("For another features please read XAP documentation.");

        DataSet.cleanup(space);
    }
}
