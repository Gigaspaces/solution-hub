package org.springframework.data.xap.repository;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.xap.model.Person;

import java.util.List;

@SpaceDocumentRepository(
        typeName = "Person",
        id = "id",
        routing = "age"
)
public interface PersonDocumentRepository extends XapDocumentRepository<SpaceDocument, String> {

        @Query("name=?")
        List<SpaceDocument> findByNameDeclaredQuery(String Name);

}
