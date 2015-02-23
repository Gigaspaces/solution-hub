package org.springframework.data.xap.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.repository.MeetingRoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Anna_Babich.
 */
@Component
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    public void save(MeetingRoom meetingRoom) {
        meetingRoomRepository.save(meetingRoom);
    }

    public void save(Iterable<MeetingRoom> rooms) {
        meetingRoomRepository.save(rooms);
    }

    public Iterable<MeetingRoom> findAll() {
        return meetingRoomRepository.findAll();
    }

    public void deleteAll() {
        meetingRoomRepository.deleteAll();
    }

    public List<MeetingRoom> findByCityCustomQuery(String city) {
        return meetingRoomRepository.findByCityCustomQuery(city);
    }
}
