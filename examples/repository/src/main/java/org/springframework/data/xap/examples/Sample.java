package org.springframework.data.xap.examples;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.Address;
import org.springframework.data.xap.examples.model.Meeting;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.examples.service.MeetingRoomService;
import org.springframework.data.xap.examples.service.MeetingService;
import org.springframework.data.xap.examples.service.PersonService;
import org.springframework.data.xap.examples.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class Sample {

    private static Person nick;
    private static Person mary;
    private static Person john;
    private static Person paul;
    private static Person jim;

    private static MeetingRoom green;
    private static MeetingRoom orange;
    private static MeetingRoom blue;

    private static Logger log = LoggerFactory.getLogger(Sample.class);

    @Autowired
    private PersonService personService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingRoomService meetingRoomService;

    public void run() {
        createPersons();
        createRooms();
        createMeetings();
        find();
        deleteAll();
    }

    private void createPersons() {
        nick = new Person(1, "Nick", true, "accounting", 22);
        mary = new Person(2, "Mary", false, "teacher", 29);
        john = new Person(3, "John", true, "accounting", 31);
        paul = new Person(4, "Paul", false, "accounting", 43);
        jim = new Person(5, "Jim", true, "security", 24);

        personService.save(Arrays.asList(new Person[]{nick, mary, john, paul, jim}));

        log.info("Persons created: ");
        for (Person person : personService.findAll()) {
            log.info(person.toString());
        }
    }

    private void createRooms(){
        green = new MeetingRoom(new Address("New York", "Main Street, 1"), "green");
        orange = new MeetingRoom(new Address("New York", "Main Street, 5"), "orange");
        blue = new MeetingRoom(new Address("Kyiv", "Main Street, 12"), "blue");

        meetingRoomService.save(Arrays.asList(new MeetingRoom[]{green, orange, blue}));

        log.info("Rooms created: ");
        for(MeetingRoom room: meetingRoomService.findAll()){
            log.info(room.toString());
        }
    }


    private void createMeetings() {
        log.info("Booking room.. ");
        meetingService.createMeeting(new Meeting(1, green, Arrays.asList(new Person[]{nick, mary}), DateUtils.getDate("10 01-03-2015")));
        log.info("Created meeting " + meetingService.findOne(1));

        log.info("Booking room.. ");
        meetingService.createMeeting(new Meeting(2, orange, Arrays.asList(new Person[]{jim, paul}), DateUtils.getDate("10 02-03-2015")));
        log.info("Created meeting " + meetingService.findOne(2));


        log.info("Try to create meeting in occupied room.. ");
        meetingService.createMeeting(new Meeting(3, green, Arrays.asList(new Person[]{john, mary}), DateUtils.getDate("10 01-03-2015")));

        log.info("Create meeting for active persons from accounting department.. ");
        List<Person> accountants = personService.findByPositionAndActiveTrue("accounting");
        meetingService.createMeeting(new Meeting(4, blue, accountants, DateUtils.getDate("12 20-04-2015")));
        log.info("Created meeting " + meetingService.findOne(4));
    }

    private void find() {

        log.info("Get all meetings after 10 01-03-2015");
        List<Meeting> meetings = meetingService.findByStartTimeAfter(DateUtils.getDate("10 01-03-2015"));
        log.info(meetings.toString());

        log.info("Get info about meetings in New York rooms");
        meetings = meetingService.findByMeetingRoomAddressCity("New York");
        log.info(meetings.toString());
    }


    private void deleteAll() {
        personService.deleteAll();
        meetingRoomService.deleteAll();
        meetingService.deleteAll();
        log.info("All data has been deleted from space.");
    }
}
