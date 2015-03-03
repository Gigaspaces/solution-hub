package org.springframework.data.xap.examples.advanced.changeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.PersonDocument;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@Component
public class DocumentApiExample {
    private static Logger log = LoggerFactory.getLogger(DocumentApiExample.class);

    @Autowired
    private PersonDocumentRepository personRepository;

    public void run() {
        PersonDocument chris = new PersonDocument("1", "Chris", 22);
        log.info("Saving document into space: " + chris);
        personRepository.save(chris);

        Iterable<PersonDocument> documents = personRepository.findAll();
        log.info("Current space state: " + documents);

        PersonDocument paul = new PersonDocument("2", "Paul", 30);
        paul.setProperty("customField", "customValue");
        log.info("Saving document with custom field into space: " + paul);
        personRepository.save(paul);

        List<PersonDocument> documentsWithCustomField = personRepository.findByCustomField("customValue");
        log.info("Got documents with custom field: " + documentsWithCustomField);
    }

}