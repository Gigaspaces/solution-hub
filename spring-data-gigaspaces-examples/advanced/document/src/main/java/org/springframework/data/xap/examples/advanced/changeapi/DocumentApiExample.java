package org.springframework.data.xap.examples.advanced.changeapi;

import com.gigaspaces.document.SpaceDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.PersonDocument;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@Component
public class DocumentApiExample {
    private static Logger log = LoggerFactory.getLogger(DocumentApiExample.class);

    @Autowired
    private MeetingDocumentRepository meetingRepository;

    @Autowired
    private PersonDocumentRepository personRepository;

    public void run() {
        log.info("SIMPLE DOCUMENT STORAGE");

        SpaceDocument meetingInGreen = new SpaceDocument("Meeting")
                .setProperty("id", "1")
                .setProperty("startTime", new Date())
                .setProperty("meetingRoom", new MeetingRoom(null, "green"));
        SpaceDocument meetingInRed = new SpaceDocument("Meeting")
                .setProperty("id", "2")
                .setProperty("startTime", new Date())
                .setProperty("meetingRoom", new MeetingRoom(null, "red"));
        log.info("Saving Meeting documents into space: " + Arrays.asList(meetingInGreen, meetingInRed));
        meetingRepository.saveAll(Arrays.asList(meetingInGreen, meetingInRed));

        Iterable<SpaceDocument> meetingDocuments = meetingRepository.findAll();
        log.info("Current Meeting space state: " + meetingDocuments);

        List<SpaceDocument> meetingsInGreen = meetingRepository.findByMeetingRoom("green");
        log.info("Got Meetings in green room: " + meetingsInGreen);


        log.info("EXTENDED DOCUMENT STORAGE");

        PersonDocument chris = new PersonDocument("1", "Chris", 22);
        log.info("Saving Person document into space: " + chris);
        personRepository.save(chris);

        Iterable<PersonDocument> personDocuments = personRepository.findAll();
        log.info("Current Person space state: " + personDocuments);

        PersonDocument paul = new PersonDocument("2", "Paul", 30);
        paul.setProperty("customField", "customValue");
        log.info("Saving Person document with custom field into space: " + paul);
        personRepository.save(paul);

        List<PersonDocument> documentsWithCustomField = personRepository.findByCustomField("customValue");
        log.info("Got Person documents with custom field: " + documentsWithCustomField);
    }

}