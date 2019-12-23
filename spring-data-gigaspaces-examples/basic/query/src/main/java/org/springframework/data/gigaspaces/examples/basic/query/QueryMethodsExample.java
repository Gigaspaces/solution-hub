package org.springframework.data.gigaspaces.examples.basic.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.examples.DataSet;
import org.springframework.data.gigaspaces.examples.model.Meeting;
import org.springframework.data.gigaspaces.examples.model.MeetingRoom;
import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.examples.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class QueryMethodsExample {

    private static Logger log = LoggerFactory.getLogger(QueryMethodsExample.class);

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    public void run() {
        DataSet.setup(personRepository.space());
        createMeetings();
        find();
        DataSet.cleanup(personRepository.space());
    }

    private void createMeetings() {
        log.info("Booking room.. ");
        createMeeting(new Meeting(1, DataSet.green, Arrays.asList(DataSet.nick, DataSet.mary), DateUtils.getDate("10 01-03-2015")));
        log.info("Created meeting " + meetingRepository.findOne(1));

        log.info("Booking room.. ");
        createMeeting(new Meeting(2, DataSet.orange, Arrays.asList(DataSet.jim, DataSet.paul), DateUtils.getDate("10 02-03-2015")));
        log.info("Created meeting " + meetingRepository.findOne(2));

        log.info("Try to create meeting in occupied room.. ");
        createMeeting(new Meeting(3, DataSet.green, Arrays.asList(DataSet.john, DataSet.mary), DateUtils.getDate("10 01-03-2015")));

        log.info("Create meeting for active persons from accounting department.. ");
        List<Person> accountants = personRepository.findByPositionAndActiveTrue("accountant");
        createMeeting(new Meeting(4, DataSet.blue, accountants, DateUtils.getDate("12 20-04-2015")));
        log.info("Created meeting " + meetingRepository.findOne(4));
    }

    private void find() {

        log.info("Get all meetings after 10 01-03-2015");
        List<Meeting> meetings = meetingRepository.findByStartTimeAfter(DateUtils.getDate("10 01-03-2015"));
        log.info(meetings.toString());


        log.info("Get info about meetings in New York rooms");
        meetings = meetingRepository.findByMeetingRoomAddressCity("New York");
        log.info(meetings.toString());

        log.info("Get all rooms that located in Kyiv (using @Query annotation)");
        List<MeetingRoom> rooms = meetingRoomRepository.findByCityCustomQuery("Kyiv");
        log.info(rooms.toString());

        log.info("Find acoountants, sort them by ascending age(using named query)");
        List<Person> persons = personRepository.findByPositionOrdered("accountant");
        log.info(persons.toString());

        log.info("Find persons older than 25, sort them by descending id");
        persons = personRepository.findByAgeGreaterThan(25, Sort.by( "id").descending());
        log.info(persons.toString());

        log.info("Find active persons, page 1 (two items)");
        persons = personRepository.findByActiveTrue(PageRequest.of(0, 2));
        log.info(persons.toString());
        log.info("page 2");
        persons = personRepository.findByActiveTrue(PageRequest.of(1, 2));
        log.info(persons.toString());


    }

    public boolean createMeeting(Meeting meeting) {
        if (checkRoomAvailability(meeting)) {
            meetingRepository.save(meeting);
            return true;
        } else {
            log.info("Room has already booked! Try another time or room.");
            return false;
        }
    }

    public boolean checkRoomAvailability(Meeting meeting) {
        List<Meeting> meetings = meetingRepository.findByStartTimeAndMeetingRoom(meeting.getStartTime(), meeting.getMeetingRoom());
        return (meetings.size() == 0);
    }
}
