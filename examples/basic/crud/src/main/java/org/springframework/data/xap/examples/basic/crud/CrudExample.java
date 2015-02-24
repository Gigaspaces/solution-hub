package org.springframework.data.xap.examples.basic.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.Address;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class CrudExample {
    private static Logger log = LoggerFactory.getLogger(CrudExample.class);

    @Autowired
    MeetingRoomRepository repository;

    public void run(){
        DataSet.setup(repository.space());

        log.info("Find one (by id = green).. ");
        MeetingRoom room = repository.findOne("green");
        log.info(room.toString());

        log.info("Find all.. ");
        List<MeetingRoom> rooms = new ArrayList<>();
        for (MeetingRoom meetingRoom: repository.findAll()){
            rooms.add(meetingRoom);
        }
        log.info(rooms.toString());

        log.info("Save new room.. ");
        repository.save(new MeetingRoom(new Address("London", "Main Street 18"), "red"));
        log.info("Saved room: " + repository.findOne("red"));

        log.info("Count objects.. Result is: " + repository.count());

        log.info("Delete one by id.. ");
        repository.delete("blue");
        log.info("Delete another one.. ");
        repository.delete("orange");

        log.info("Current number of room is: " + repository.count());

        DataSet.cleanup(repository.space());
    }
}
