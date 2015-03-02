@SpaceDocumentRepository(
        typeName = PersonDocument.TYPE_NAME,
        id = PersonDocument.PROPERTY_ID,
        routing = PersonDocument.PROPERTY_AGE)
public interface PersonDocumentRepository extends XapDocumentRepository<PersonDocument, String> {
}