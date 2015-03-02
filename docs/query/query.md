#### <a name="query"/>3.1 Query Methods

To start off, next declaration will allow application to search for objects by different field matches:

```java
${PersonRepository.java}
```

As you can see, different keywords can be used and combined to create desired conditions. Full list of supported keywords can be found in [Appendix A](#appendix-a).

The process of deriving query methods into XAP Queries depends a lot on the query lookup strategy chosen for the repository. Spring XAP Data provides the support for all [common strategies](http://docs.spring.io/spring-data/data-commons/docs/1.9.1.RELEASE/reference/html/#repositories.query-methods.query-lookup-strategies).

The default strategy enables both deriving queries from method names and overriding them with custom defined queries. There are several ways to specify custom query for a method. First possibility is to apply `@Query` annotation on the method:

```java
${PersonRepositoryQuery.java}
```

The syntax used for `@Query` is similar to SQL queries. Refer to [SQLQuery](http://docs.gigaspaces.com/xap101/query-sql.html) for the full list of possibilities.

Another way would be to import named queries from external resource. Let's say we have `named-queries.properties` file in the classpath with next content:

```properties
${named-queries.properties}
```

The query strings defined in the file will be applied to methods with same names in `PersonRepository` if we target `named-queries.properties` in the configuration. In XML-based configuration the `named-queries-location` attribute can be used:

```xml
${repository-context.xml}
```

Similarly, annotation field `namedQueriesLocation` can be used in Java-based configuration:

```java
${JavaConfiguration.java}
```