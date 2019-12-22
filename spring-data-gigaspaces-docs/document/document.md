#### <a name="document"/>4.7 Gigaspaces Document Storage Support

The [Gigaspaces Document API](http://docs.gigaspaces.com/latest/dev-java/document-api.html) exposes the space as Document Store. A document, which is represented by the class `SpaceDocument`, is a collection of key-value pairs, where the keys are strings and the values are primitives, `String`, `Date`, other documents, or collections thereof. Most importantly, the Space is aware of the internal structure of a document, and thus can index document properties at any nesting level and expose rich query semantics for retrieving documents.

While using Spring Data Gigaspaces you can declare one or more of your repositories to be a Document Repository. To do so, first, you have to add a schema definition of the document type into the Space configuration in context:
```xml
<os-core:embedded-space id="space" name="space">

  <os-core:space-type type-name="Person">
    <os-core:id property="id"/>
    <os-core:routing property="age"/>
    <os-core:basic-index path="name"/>
    <os-core:extended-index path="birthday"/>
  </os-core:space-type>

  <!-- other document types declarations -->

</os-core:embedded-space>
```

Then, extend `GigaspacesDocumentRepository` interface (instead of usual `GigaspacesRepository`) and annotate it with `@SpaceDocumentName` to wire it to document descriptor declared above:
```java
@SpaceDocumentName("Person")
public interface PersonDocumentRepository extends GigaspacesDocumentRepository<SpaceDocument, String> {
}
```
> If you don't mark your Document Repository with `@SpaceDocumentName` annotation, context configuration will fail.

Now `PersonDocumentRepository` will have basic CRUD operations available for `SpaceDocument` entities. To read more on available document storage features, refer to [Document API](http://docs.gigaspaces.com/latest/dev-java/document-api.html).

While documents allow using a dynamic schema, they force us to give up Java’s type-safety for working with type less key-value pairs. Spring Data Gigaspaces Supports extending the `SpaceDocument` class to provide a type-safe wrapper for documents which is much easier to code with, while maintaining the dynamic schema. As an example, let's declare a `PersonDocument` wrapper:
```java
public class PersonDocument extends SpaceDocument {
    public static final String TYPE_NAME = "Person";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_AGE = "age";
    // other properties omitted

    public PersonDocument() {
        super(TYPE_NAME);
    }

    public String getId() {
        return super.getProperty(PROPERTY_ID);
    }

    public PersonDocument setId(String id) {
        super.setProperty(PROPERTY_ID, id);
        return this;
    }

    public Integer getAge() {
        return super.getProperty(PROPERTY_AGE);
    }

    public PersonDocument setAge(Integer age) {
        super.setProperty(PROPERTY_AGE, age);
        return this;
    }

    // other properties accessors are omitted
}
```
> Note that wrapper classes must have a parameter less constructor

To work with objects of a `PersonDocument` class instead of `SpaceDocument`, Space configuration must contain the declaration of the wrapper class:
```xml
<os-core:embedded-space id="space" name="space">

  <os-core:space-type type-name="Person">
    <os-core:id property="id"/>
    <os-core:routing property="age"/>
    <os-core:basic-index path="name"/>
    <os-core:extended-index path="birthday"/>
    <os-core:document-class>com.yourcompany.foo.bar.PersonDocument</os-core:document-class>
  </os-core:space-type>

  <!-- other document types declarations -->

</os-core:embedded-space>
```

Now we can declare our Document Repository with the next syntax:
```java
@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends GigaspacesDocumentRepository<PersonDocument, String> {
}
```
> Note that domain class of `PersonDocumentRepository` is now set to `PersonDocument` instead of `SpaceDocument`. Also, type name for `PersonDocument` is reused in `@SpaceDocumentName` annotation for the repository.

If you want to read more on the concept of wrapping the `SpaceDocument`, please, refer to [Extended Document](http://docs.gigaspaces.com/latest/dev-java/document-extending.html).

You can supply your Document Repository with query methods. But be aware that due to dynamic nature of `SpaceDocument` there is no way for Spring Data to automatically derive query method names into queries. The only possibility to declare a method is to use `@Query` annotation or load queries from external resources. Refer to [Query methods](#query) to read more on possible find methods declarations. Here is an example of Document Repository supplied with search and sorting methods:
```java
@SpaceDocumentName(PersonDocument.TYPE_NAME)
public interface PersonDocumentRepository extends GigaspacesDocumentRepository<PersonDocument, String> {

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
```
> Note that you don't have to declare document properties to use them in queries, which allows dynamically adding and removing the properties.

Document Repositories do not support Querydsl syntax due to dynamic nature of `SpaceDocument` properties.
