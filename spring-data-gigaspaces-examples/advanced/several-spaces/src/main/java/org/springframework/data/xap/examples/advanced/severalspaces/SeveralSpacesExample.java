package org.springframework.data.xap.examples.advanced.severalspaces;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.advanced.severalspaces.person.PersonRepository;
import org.springframework.data.xap.examples.advanced.severalspaces.room.MeetingRoomRepository;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Anna_Babich.
 */
@Component
public class SeveralSpacesExample {
    private static Logger log = LoggerFactory.getLogger(SeveralSpacesExample.class);
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public void run() {
        GigaSpace personSpace = personRepository.space();
        GigaSpace roomSpace = meetingRoomRepository.space();
        log.info("Space name of the personRepository " + personSpace.getSpace().getName());
        log.info("Space name of the roomRepository " + roomSpace.getSpace().getName());
        log.info("Save persons and rooms to repositories.. ");
        personRepository.saveAll(Arrays.asList(DataSet.john, DataSet.jim));
        meetingRoomRepository.saveAll(Arrays.asList(DataSet.green, DataSet.orange));
        log.info("Get persons from repository: ");
        for(Person person: personRepository.findAll()){
            log.info(person.toString());
        }
        log.info("Get rooms from repository: ");
        for(MeetingRoom room: meetingRoomRepository.findAll()){
            log.info(room.toString());
        }
        log.info("Try to get rooms from personRepository space");
        log.info(Arrays.asList(personSpace.readMultiple(new MeetingRoom())).toString());
        log.info("Try to get persons from meetingRoomRepository space");
        log.info(Arrays.asList(roomSpace.readMultiple(new Person())).toString());
        
        log.info("Clean up the first space:");
        DataSet.cleanup(personSpace);
        log.info("Clean up the second space:");
        DataSet.cleanup(roomSpace);
    }
}
