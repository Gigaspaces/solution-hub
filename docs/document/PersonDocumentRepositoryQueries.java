@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends XapDocumentRepository<PersonDocument, String> {

    // you can define simple queries
    @Query("name = ?")
    List<PersonDocument> findByName(String name);

    // you can build complex queries
    @Query("name = ? and age = ?")
    List<PersonDocument> findByNameAndAge(String name, Integer age);

    // you can query embedded properties
    @Query("spouse.name = ?")
    List<PersonDocument> findBySpouseName(String name);

    // you can query any properties, even if they are not present in you wrapper
    @Query("customField = ?")
    List<PersonDocument> findByCustomField(String value);

    // you can perform sorting using SQLQuery syntax
    @Query("age = ? order by id asc")
    List<PersonDocument> findByAgeSortedById(Integer age);

}