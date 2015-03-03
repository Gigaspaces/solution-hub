package org.springframework.data.xap.examples.advanced.changeapi;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.xap.repository.Query;
import org.springframework.data.xap.repository.SpaceDocumentName;
import org.springframework.data.xap.repository.XapDocumentRepository;

import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@SpaceDocumentName("Meeting")
public interface MeetingDocumentRepository extends XapDocumentRepository<SpaceDocument, Integer> {

    @Query("meetingRoom.name = ?")
    List<SpaceDocument> findByMeetingRoom(String name);

}