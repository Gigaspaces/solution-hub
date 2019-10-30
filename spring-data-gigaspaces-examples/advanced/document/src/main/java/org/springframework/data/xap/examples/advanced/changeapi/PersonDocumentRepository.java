package org.springframework.data.xap.examples.advanced.changeapi;

import org.springframework.data.xap.examples.model.PersonDocument;
import org.springframework.data.xap.repository.Query;
import org.springframework.data.xap.repository.SpaceDocumentName;
import org.springframework.data.xap.repository.XapDocumentRepository;

import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends XapDocumentRepository<PersonDocument, Integer> {

    @Query("customField = ?")
    List<PersonDocument> findByCustomField(String customFieldValue);

}
