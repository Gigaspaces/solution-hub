package org.springframework.data.gigaspaces.examples.advanced.projection;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gigaspaces.examples.DataSet;
import org.springframework.data.gigaspaces.examples.model.MeetingRoom;
import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.repository.query.Projection;
import org.springframework.stereotype.Component;

import static org.springframework.data.gigaspaces.examples.model.QMeetingRoom.meetingRoom;
import static org.springframework.data.gigaspaces.examples.model.QPerson.person;
import static org.springframework.data.gigaspaces.querydsl.GigaspacesDslProjection.projection;

/**
 * @author Anna_Babich.
 */
@Component
public class ProjectionExample {
    private static Logger log = LoggerFactory.getLogger(ProjectionExample.class);
    @Autowired
    PredicatePersonRepository personRepository;
    @Autowired
    PredicateRoomRepository roomRepository;

    public void run() {
        GigaSpace space = personRepository.space();
        DataSet.setup(space);

        log.info("PROJECTION USING QUERY DSL");

        log.info("Find person by name, projection with name and age");
        Person p = personRepository.findOne(person.name.eq(DataSet.nick.getName()), projection(person.name, person.age)).orElse(null);
        log.info(p.toString());

        log.info("Find room by name, projection with address only");
        MeetingRoom r = roomRepository.findOne(meetingRoom.name.eq(DataSet.orange.getName()), projection(meetingRoom.address)).orElse(null);
        log.info(r.toString());

        log.info("PROJECTION USING SPECIAL PARAMETER");

        log.info("Projection with embedded field address.city");
        for(MeetingRoom room: roomRepository.findAll(Projection.projections("address.city"))){
            log.info(room.toString());
        }

        log.info("Projection with field name");
        for(MeetingRoom room: roomRepository.findAll(Projection.projections("name"))){
            log.info(room.toString());
        }

        log.info("Projection with several fields: name and position");
        for(Person per: personRepository.findAll(Projection.projections("name", "position"))){
            log.info(per.toString());
        }
        
        DataSet.cleanup(space);
    }
}
