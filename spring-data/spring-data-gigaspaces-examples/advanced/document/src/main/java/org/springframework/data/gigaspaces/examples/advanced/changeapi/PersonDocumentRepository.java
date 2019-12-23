package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import org.springframework.data.gigaspaces.examples.model.PersonDocument;
import org.springframework.data.gigaspaces.repository.Query;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;
import org.springframework.data.gigaspaces.repository.GigaspacesDocumentRepository;

import java.util.List;

/**
 * @author Leonid_Poliakov.
 */
@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends GigaspacesDocumentRepository<PersonDocument, Integer> {

    @Query("customField = ?")
    List<PersonDocument> findByCustomField(String customFieldValue);

}
