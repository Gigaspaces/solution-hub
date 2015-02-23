package org.springframework.data.xap.examples.repository;

import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.repository.Query;
import org.springframework.data.xap.repository.XapRepository;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface MeetingRoomRepository extends XapRepository<MeetingRoom, String> {

    @Query("address.city=?")
    public List<MeetingRoom> findByCityCustomQuery(String city);

}
