#### <a name="take"/>4.4 Gigaspaces Take Operations

The Spring Data Gigaspaces Supports take operations that are the same as querying the space, but returned objects are deleted from the storage. This approach removes the need to perform additional operations when you implement a pattern where consumers or workers are receiving tasks or messages.

Basic take operation can be performed by object ids with `take(...)` methods in `GigaspacesRepository` interface. More advanced querying is available in Querydsl style within `GigaspacesQueryDslPredicateExecutor` interface. Those accept `Predicate` to retrieve one or multiple objects that match the query:
```java
${PersonServiceImpl.java}
```
> To start using Querydsl take operations, refer to [Querydsl Support](#querydsl)
