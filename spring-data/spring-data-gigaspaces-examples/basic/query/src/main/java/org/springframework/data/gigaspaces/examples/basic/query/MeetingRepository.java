package org.springframework.data.gigaspaces.examples.basic.query;

import org.springframework.data.gigaspaces.examples.model.Meeting;
import org.springframework.data.gigaspaces.examples.model.MeetingRoom;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface MeetingRepository extends GigaspacesRepository<Meeting, Integer> {
    List<Meeting> findByStartTimeAndMeetingRoom(Date startTime, MeetingRoom meetingRoom);

    List<Meeting> findByStartTimeAfter(Date date);

    List<Meeting> findByMeetingRoomAddressCity(String city);
}
