package org.springframework.data.xap.examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.Meeting;
import org.springframework.data.xap.examples.repository.MeetingRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class MeetingService {

    private static Logger log = LoggerFactory.getLogger(MeetingService.class);

    @Autowired
    private MeetingRepository meetingRepository;

    public void deleteAll() {
        meetingRepository.deleteAll();
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

    public Meeting findOne(Integer id) {
        return meetingRepository.findOne(id);
    }

    public List<Meeting> findByStartTimeAfter(Date date) {
        return meetingRepository.findByStartTimeAfter(date);
    }

    public List<Meeting> findByMeetingRoomAddressCity(String city) {
        return meetingRepository.findByMeetingRoomAddressCity(city);
    }
}
