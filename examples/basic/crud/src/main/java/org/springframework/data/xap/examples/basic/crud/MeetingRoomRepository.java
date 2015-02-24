package org.springframework.data.xap.examples.basic.crud;

import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface MeetingRoomRepository extends XapRepository<MeetingRoom, String> {
}
