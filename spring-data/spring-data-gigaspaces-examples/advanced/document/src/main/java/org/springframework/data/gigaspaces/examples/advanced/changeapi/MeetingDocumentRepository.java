package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.gigaspaces.repository.Query;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;
import org.springframework.data.gigaspaces.repository.GigaspacesDocumentRepository;

import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@SpaceDocumentName("Meeting")
public interface MeetingDocumentRepository extends GigaspacesDocumentRepository<SpaceDocument, Integer> {

    @Query("meetingRoom.name = ?")
    List<SpaceDocument> findByMeetingRoom(String name);

}