package org.springframework.data.xap.repository;

import org.springframework.data.xap.model.PersonDocument;
import org.springframework.data.xap.repository.query.Projection;

import java.util.List;

@SpaceDocumentRepository(
        typeName = PersonDocument.TYPE_NAME,
        id = PersonDocument.PROPERTY_ID,
        routing = PersonDocument.PROPERTY_AGE
)
public interface PersonDocumentRepository extends XapDocumentRepository<PersonDocument, String> {

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

}