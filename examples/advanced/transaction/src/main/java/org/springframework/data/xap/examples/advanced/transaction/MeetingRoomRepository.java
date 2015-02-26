package org.springframework.data.xap.examples.advanced.transaction;

import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface MeetingRoomRepository extends XapRepository<MeetingRoom, String>{
}
