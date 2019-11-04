package org.springframework.data.gigaspaces.examples.basic.query;

import org.springframework.data.gigaspaces.examples.model.MeetingRoom;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;
import org.springframework.data.gigaspaces.repository.Query;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface MeetingRoomRepository extends GigaspacesRepository<MeetingRoom, String> {

    @Query("address.city=?")
    public List<MeetingRoom> findByCityCustomQuery(String city);

}
