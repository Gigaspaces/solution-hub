@SpaceDocumentRepository(typeName = "Person", id = "id", routing = "age")
public interface PersonDocumentRepository extends XapDocumentRepository<SpaceDocument, String> {
}