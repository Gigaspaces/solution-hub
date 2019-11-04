@SpaceDocumentName("Person")
public interface DocumentQueries
        extends GigaspacesDocumentRepository<SpaceDocument, String> {

    @Query("name = ?")
    List<SpaceDocument> findByName(String name);

    // this declaration without @Query annotation
    // or named query from external resource
    // will throw UnsupportedOperationException during configuration
    List<SpaceDocument> findByAge(Integer age);

}