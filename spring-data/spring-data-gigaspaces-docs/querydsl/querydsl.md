#### <a name="querydsl"/>4.2 Querydsl Support

The Querydsl framework let's you write type-safe queries in Java instead of using good old query strings. It gives you several advantages: code completion in your IDE, domain types and properties can be accessed in a type-safe manner which reduces the probability of query syntax errors during run-time. If you want to read more about Querydsl, please, proceed to [Querydsl website](http://www.querydsl.com/).

Several steps are needed to start using Gigaspaces Repositories Querydsl support. First, mark wanted repository as a `GigaspacesQueryDslPredicateExecutor` along with `GigaspacesRepository`:
```java
public interface PersonRepository extends GigaspacesRepository<Person, String>, GigaspacesQueryDslPredicateExecutor<Person> {
}
```

> Note that you define the type of data to be accessed with Querydsl.

Then, add source processor to your maven build (`pom.xml`) using Maven Annotation Processing Tool plugin:
```xml
<project>
  <build>
    <plugins>

      ...

      <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
          <execution>
            <goals>
              <goal>process</goal>
            </goals>
            <configuration>
              <outputDirectory>target/generated-sources/java</outputDirectory>
              <processor>org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslAnnotationProcessor</processor>
            </configuration>
          </execution>
        </executions>
      </plugin>

      ...

    </plugins>
  </build>
</project>
```

This configuration will call `GigaspacesQueryDslAnnotationProcessor` before compiling your project sources. It will look for POJOs marked with `@SpaceClass` annotation and generate `Q...` classes for them that allow you to build up Querydsl `Predicate`s. Before using such classes, you have to call this processor with `process-sources` maven goal, or just call `install` if you are already using it:
```
mvn clean process-sources
mvn clean install
```

Now you can query your repository using Querydsl `Predicate`s:
```java
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public Iterable<Person> getByAge(Integer minAge, Integer maxAge) {
        return repository.findAll(
                QPerson.person.name.isNotNull().and(QPerson.person.age.between(minAge, maxAge))
        );
    }

}
```

Full list of supported `Predicate` methods can be found in [Appendix B](#appendix-b).
