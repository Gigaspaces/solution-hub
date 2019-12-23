package org.springframework.data.gigaspaces.integration.document;

import org.springframework.data.gigaspaces.model.PersonDocument;
import org.springframework.data.gigaspaces.repository.GigaspacesDocumentRepository;
import org.springframework.data.gigaspaces.repository.Query;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;
import org.springframework.data.gigaspaces.repository.query.Projection;

import java.util.List;

@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends GigaspacesDocumentRepository<PersonDocument, String> {

    @Query("name = ?")
    List<PersonDocument> findByName(String name);

    @Query("age = ?")
    List<PersonDocument> findByAge(Integer age);

    @Query("name = ? and age = ?")
    List<PersonDocument> findByNameAndAge(String name, Integer age);

    @Query("name = ? or age = ?")
    List<PersonDocument> findByNameOrAge(String name, Integer age);

    @Query("spouse.name = ?")
    List<PersonDocument> findBySpouseName(String name);

    @Query("customField = ?")
    List<PersonDocument> findByCustomField(String value);

    @Query("age = ? order by id asc")
    List<PersonDocument> findByAgeSortedById(Integer age);

    @Query("age = ?")
    List<PersonDocument> findByAge(Integer age, Projection projection);

    @Query("age between ? and ?")
    List<PersonDocument> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query("active = 'true'")
    List<PersonDocument> findByActiveTrue();

    @Query("name in (?)")
    List<PersonDocument> findByNameIn(List<String> names);

    @Query("name rlike ?")
    List<PersonDocument> findByNameRegex(String regex);

    List<PersonDocument> findBySpouseAge(Integer age);

}