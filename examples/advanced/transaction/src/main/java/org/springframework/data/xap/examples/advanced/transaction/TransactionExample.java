package org.springframework.data.xap.examples.advanced.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.data.xap.examples.model.Address;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Anna_Babich.
 */
@Component
public class TransactionExample {
    private static Logger log = LoggerFactory.getLogger(TransactionExample.class);

    @Autowired
    private TransactionalService service;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    public void run() {
        DataSet.setup(meetingRoomRepository.space());
        MeetingRoom green = new MeetingRoom(new Address("London", "Main Street 22"), "green");
        MeetingRoom yellow = new MeetingRoom(new Address("Budapest", "Main Street 33"), "yellow");
        MeetingRoom grey = new MeetingRoom(new Address("Minsk", "Main Street 44"), "grey");
        MeetingRoom white = new MeetingRoom(new Address("Amsterdam", "Main Street 28"), "white");

        log.info("Run service method with expected rollback.. ");
        try {
            service.saveRooms(Arrays.asList(yellow, green));
        } catch (RoomNameIsUnavailableException e) {
            log.error(e.getMessage());
        }

        log.info("Run service method with correct data..");
        try{
            service.saveRooms(Arrays.asList(grey, white));
        } catch (RoomNameIsUnavailableException e) {
            log.error(e.getMessage());
        }

        log.info("Result room list: ");
        for (MeetingRoom room: meetingRoomRepository.findAll()){
            log.info(room.toString());
        }
    }
}
