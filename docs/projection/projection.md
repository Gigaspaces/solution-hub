#### <a name="projection"/>4.1 XAP Projection API

The Spring Data XAP supports [XAP Projection](http://docs.gigaspaces.com/xap101/query-partial-results.html) which allows to read only certain properties for the objects (a.k.a. delta read). This approach reduces network overhead, garbage memory generation and CPU overhead due to decreased serialization time.

`XapRepository` interface provides you with basic `find` methods extended with `Projection` argument. Next code demonstrates how `findOne` method can be used to select only `name` field from `Person`:
```java
${PersonServiceImpl.java}
```
> Note that if you are using [Querydsl support](#querydsl), you can apply projection using `QueryDslProjection`. This approach will let you avoid run-time errors when POJO field is renamed and projection fields are not since they are just strings.

You can also supply your query methods with `Projection`, just add an additional argument to the method declaration:
```java
${PersonRepository.java}
```

To read more on projection concepts, please, refer to [Projection](http://docs.gigaspaces.com/xap101/query-partial-results.html) reference.
