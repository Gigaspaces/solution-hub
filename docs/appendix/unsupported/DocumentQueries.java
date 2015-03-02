@SpaceDocumentRepository(typeName = "Person", id = "id", routing = "age")
public interface DocumentQueries
        extends XapDocumentRepository<SpaceDocument, String> {

    @Query("name = ?")
    List<SpaceDocument> findByName(String name);

    // this declaration without @Query annotation
    // or named query from external resource
    // will throw UnsupportedOperationException during configuration
    List<SpaceDocument> findByAge(Integer age);

}