package org.springframework.data.xap.examples.advanced.severalspaces.room;

import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface MeetingRoomRepository extends XapRepository<MeetingRoom, String>{
}
