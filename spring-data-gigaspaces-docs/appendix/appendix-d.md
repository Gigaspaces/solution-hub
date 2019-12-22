## <a name="appendix-d"/>Appendix D: Unsupported operations

Although we try to support each and every Spring Data feature, sometimes native implementation is not possible using Space as a data source. Instead of providing workarounds, which are often slow, we decided to mark some features as unsupported, among them are:
#### Using `IgnoreCase`, `Exists`, `IsNear` and `IsWithin` keywords
```java
public interface PersonRepository extends GigaspacesRepository<Person, String> {

    // these methods throw an UnsupportedOperationException when called

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findByNameExists(boolean exists);

    List<Person> findByAgeIsNear(Integer nearAge);

    List<Person> findByAgeIsWithin(Integer minAge, Integer maxAge);

}
```
#### Setting `Sort` to `ignoreCase`
```java
// Order.ignoreCase() is not supported
Sort sorting = new Sort(new Order(ASC, "id").ignoreCase());
// will throw an UnsupportedOperationException
personRepository.findByNameEquals("paul", new PageRequest(1, 2, sorting));
```
#### Setting any `NullHandling` in `Sort` other than `NATIVE`
```java
// NullHandling other than NATIVE is not supported
Sort sorting = new Sort(new Order(ASC, "id", NullHandling.NULLS_FIRST));
// will throw an UnsupportedOperationException
personRepository.findByNameEquals("paul", new PageRequest(1, 2, sorting));
```
#### Using query derivation in `GigaspacesDocumentRepository`
```java
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
```
