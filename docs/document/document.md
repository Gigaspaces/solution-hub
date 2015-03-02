#### <a name="document"/>4.7 XAP Document Storage Support

The [XAP Document API](http://docs.gigaspaces.com/xap101/document-api.html) exposes the space as Document Store. A document, which is represented by the class `SpaceDocument`, is a collection of key-value pairs, where the keys are strings and the values are primitives, `String`, `Date`, other documents, or collections thereof. Most importantly, the Space is aware of the internal structure of a document, and thus can index document properties at any nesting level and expose rich query semantics for retrieving documents.

While using Spring Data XAP you can declare one or more of your repositories to be a Document Repository. To do so, you have to extend `XapDocumentRepository` interface (instead of usual `XapRepository`) and annotate it with `@SpaceDocumentRepository` to define document name and optionally mark properties as id, index or routing key:
```java
${PersonDocumentRepository.java}
```
> If you don't mark your Document Repository with `@SpaceDocumentRepository` annotation, context configuration will fail.

Now `PersonDocumentRepository` will have basic CRUD operations available for `SpaceDocument` entities. To read more on available document storage features, refer to [Document API](http://docs.gigaspaces.com/xap101/document-api.html).

While documents allow using a dynamic schema, they force us to give up Java’s type-safety for working with type less key-value pairs. Spring Data XAP supports extending the `SpaceDocument` class to provide a type-safe wrapper for documents which is much easier to code with, while maintaining the dynamic schema. As an example, let's declare a `PersonDocument` wrapper:
```java
${PersonDocument.java}
```

Now we can declare our Document Repository with the next syntax:
```java
${PersonExtendedDocumentRepository.java}
```
> Note that domain class of `PersonDocumentRepository` is now set to `PersonDocument` instead of `SpaceDocument`. Also, property names from `PersonDocument` are reused in `@SpaceDocumentRepository` annotation for the repository.

If you want to read more on the concept of wrapping the `SpaceDocument`, please, refer to [Extended Document](http://docs.gigaspaces.com/xap101/document-extending.html).

You can supply your Document Repository with query methods. But be aware that due to dynamic nature of `SpaceDocument` there is no way for Spring Data to automatically derive query method names into queries. The only possibility to declare a method is to use `@Query` annotation or load queries from external resources. Refer to [Query methods](#query) to read more on possible find methods declarations. Here is an example of Document Repository supplied with search and sorting methods:
```java
${PersonDocumentRepositoryQueries.java}
```
> Note that you don't have to declare document properties to use them in queries, which allows dynamically adding and removing the properties.

Document Repositories do not support Querydsl syntax due to dynamic nature of `SpaceDocument` properties.
