#### <a name="query"/>3.1 Query Methods

To start off, next declaration will allow application to search for objects by different field matches:

```java
public interface PersonRepository extends GigaspacesRepository<Person, String> {

    // you can query objects with exact field match
    List<Person> findByName(String name);

    List<Person> findByAge(Integer age);

    // you can use ranged of search for number fields
    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);

    // you can use boolean expressions to define complex conditions
    List<Person> findByNameAndAge(String name, Integer age);

    List<Person> findByNameOrAge(String name, Integer age);

}
```

As you can see, different keywords can be used and combined to create desired conditions. Full list of supported keywords can be found in [Appendix A](#appendix-a).

The process of deriving query methods into Gigaspaces Queries depends a lot on the query lookup strategy chosen for the repository. Spring Data Gigaspaces  provides the support for all [common strategies](http://docs.spring.io/spring-data/data-commons/docs/1.9.1.RELEASE/reference/html/#repositories.query-methods.query-lookup-strategies).

The default strategy enables both deriving queries from method names and overriding them with custom defined queries. There are several ways to specify custom query for a method. First possibility is to apply `@Query` annotation on the method:

```java
public interface PersonRepository extends GigaspacesRepository<Person, String> {

    @Query("name = ? order by name asc")
    List<Person> findByNameOrdered(String name);

}
```

The syntax used for `@Query` is similar to SQL queries. Refer to [SQLQuery](http://docs.gigaspaces.com/latest/dev-java/query-sql.html) for the full list of possibilities.

Another way would be to import named queries from external resource. Let's say we have `named-queries.properties` file in the classpath with next content:

```properties
Person.findByNameOrdered=name = ? order by name asc
```

The query strings defined in the file will be applied to methods with same names in `PersonRepository` if we target `named-queries.properties` in the configuration. In XML-based configuration the `named-queries-location` attribute can be used:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:gigaspaces-data="http://www.springframework.org/schema/data/gigaspaces"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="
         http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/data/gigaspaces http://www.springframework.org/schema/data/gigaspaces/spring-data-gigaspaces.xsd">

  <gigaspaces-data:repositories base-package="com.yourcompany.foo.bar"
                         named-queries-location="classpath:named-queries.properties"/>

  <!-- other configuration omitted -->

</beans>
```

Similarly, annotation field `namedQueriesLocation` can be used in Java-based configuration:

```java
@Configuration
@EnableGigaspacesRepositories(value = "com.yourcompany.foo.bar", namedQueriesLocation = "classpath:named-queries.properties")
public class ContextConfiguration {
    // bean definitions omitted
}
```