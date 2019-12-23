#### <a name="projection"/>4.1 Gigaspaces Projection API

The Spring Data Gigaspaces Supports [Gigaspaces Projection](http://docs.gigaspaces.com/latest/dev-java/query-partial-results.html) which allows to read only certain properties for the objects (a.k.a. delta read). This approach reduces network overhead, garbage memory generation and CPU overhead due to decreased serialization time.

`GigaspacesRepository` interface provides you with basic `find` methods extended with `Projection` argument. Next code demonstrates how `findOne` method can be used to select only `name` field from `Person`:
```java
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public List<String> getAllNames() {
        Iterable<Person> personList = repository.findAll(Projection.projections("name"));
        // result processing ommited
    }

}
```
> Note that if you are using [Querydsl support](#querydsl), you can apply projection using `QueryDslProjection`. This approach will let you avoid run-time errors when POJO field is renamed and projection fields are not since they are just strings.
```java
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public List<String> getAllNames() {
        Iterable<Person> personList = repository.findAll(null, QueryDslProjection.projection(QPerson.person.name));
        // result processing ommited
    }

}
```

You can also supply your query methods with `Projection`, just add an additional argument to the method declaration:
```java
public interface PersonRepository extends GigaspacesRepository<Person, String> {

    List<Person> findByName(String name, Projection projection);

}
```

To read more on projection concepts, please, refer to [Projection](http://docs.gigaspaces.com/latest/dev-java/query-partial-results.html) reference.
