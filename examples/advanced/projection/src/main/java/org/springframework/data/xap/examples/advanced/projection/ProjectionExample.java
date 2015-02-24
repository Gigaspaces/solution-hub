package org.springframework.data.xap.examples.advanced.projection;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.query.Projection;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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

        log.info("Projection with embedded field address.city");
        for(MeetingRoom room: roomRepository.findAll(Projection.projections("address.city"))){
            log.info(room.toString());
        }

        log.info("Projection with field name");
        for(MeetingRoom room: roomRepository.findAll(Projection.projections("name"))){
            log.info(room.toString());
        }

        log.info("Projection with several fields: name and position");
        for(Person p: personRepository.findAll(Projection.projections("name", "position"))){
            log.info(p.toString());
        }

        log.info("PROJECTION USING NATIVE API");
        log.info("Find fields id age from persons, which age greater than 24 ");
        SQLQuery<Person> query = new SQLQuery(Person.class,"age > 24").setProjections("id", "age");
        List<Person> persons = Arrays.asList(space.readMultiple(query));
        log.info(persons.toString());

        DataSet.cleanup(space);
    }
}
