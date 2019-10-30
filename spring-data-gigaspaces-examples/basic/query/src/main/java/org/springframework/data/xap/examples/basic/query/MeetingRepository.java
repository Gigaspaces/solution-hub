package org.springframework.data.xap.examples.basic.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.xap.examples.model.Meeting;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.repository.XapRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface MeetingRepository extends XapRepository<Meeting, Integer> {
    List<Meeting> findByStartTimeAndMeetingRoom(Date startTime, MeetingRoom meetingRoom);

    List<Meeting> findByStartTimeAfter(Date date);

    List<Meeting> findByMeetingRoomAddressCity(String city);
}
