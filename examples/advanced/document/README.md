Spring Data XAP - Document Storage Support
==========================================

The Spring Data XAP supports the [Document API](http://docs.gigaspaces.com/xap101/document-api.html) which exposes the space as Document Store. A document, which is represented by the class `SpaceDocument`, is a collection of key-value pairs, where the keys are strings and the values are primitives, `String`, `Date`, other documents, or collections thereof. Most importantly, the Space is aware of the internal structure of a document, and thus can index document properties at any nesting level and expose rich query semantics for retrieving documents.

In this example, extended documents usage is displayed. There are several points to pay attention to:
* document schema is defined in `spring-context.xml` in Space declaration
* type-safe wrapper `PersonDocument` is created over `SpaceDocument` enabling both static and dynamic schema
* repository interface `PersonDocumentRepository` extends the `XapDocumentRepository` which provides basic CRUD operations over `SpaceDocument`

Running the example (`DocumentApiMain` class) must produce the next output:

```
DOCUMENT STORAGE SUPPORT EXAMPLE
...
Saving document into space: SpaceDocument [typeName=Person, version=0, transient=false, properties=DocumentProperties {id=1,name=Chris,age=22}]
Current space state: [SpaceDocument [typeName=Person, version=1, transient=false, properties=DocumentProperties {name=Chris,age=22,id=1}]]
Saving document with custom field into space: SpaceDocument [typeName=Person, version=0, transient=false, properties=DocumentProperties {name=Paul,id=2,customField=customValue,age=30}]
Got documents with custom field: [SpaceDocument [typeName=Person, version=1, transient=false, properties=DocumentProperties {age=30,name=Paul,customField=customValue,id=2}]]
...
```

To read more on this topic, please, refer to [XAP Document Storage Support](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#document).