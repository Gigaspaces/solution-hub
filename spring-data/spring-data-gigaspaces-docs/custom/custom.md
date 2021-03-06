#### <a name="custom"/>3.2 Custom Methods

It is often needed to add custom methods with your implementation to repository interfaces. Spring Data allows you to provide custom repository code and still utilize basic CRUD features and query method functionality. To extend your repository, you first define a separate interface with custom methods declarations:
```java
public interface PersonRepositoryCustom {

    String customMethod();

}
```

Then you add an implementation for the defined interface:
```java
public class PersonRepositoryCustomImpl implements PersonRepositoryCustom {

    public String customMethod() {
        // your custom implementation
    }

}
```

> Note that Spring Data recognizes an `Impl` suffix by default to look for custom methods implementations.

The implementation itself does not depend on Spring Data, so you can inject other beans or property values into it using standard dependency injection. E.g. you could inject `GigaSpaces` and use it directly in your custom methods.

The third step would be to apply interface with custom methods to your repository declaration:
```java
public interface PersonRepository extends GigaspacesRepository<Person, String>, PersonRepositoryCustom {

    // query methods declarations are ommited

}
```

This will combine basic CRUD methods and your custom functionality and make it available to clients.

So, how did it really work? Spring Data looks for implementations of custom method among all classes located under `base-package` attribute in XML or `basePackages` in Java configuration. It searches for `<custom interface name><suffix>` classes, where `suffix` is `Impl` by default. If your project conventions tell you to use another suffix for the implementations, you can specify it with `repository-impl-postfix` attribute in XML configuration:
```xml
<gigaspaces-data:repositories
        base-package="com.yourcompany.foo.bar"
        repository-impl-postfix="FooBar"/>
```

Or with `repositoryImplementationPostfix` in Java configuration:
```java
@Configuration
@EnableGigaspacesRepositories(value = "com.yourcompany.foo.bar", repositoryImplementationPostfix = "FooBar")
public class ContextConfiguration {
    // bean definitions omitted
}
```

Another option would be to manually put the implementation into the context and use a proper name for it. In XML configuration it would look like this:
```xml
<gigaspaces-data:repositories base-package="com.yourcompany.foo.bar"/>

<bean id="personRepositoryImpl" class="...">
<!-- further configuration -->
</bean>
```

And similarly in Java-based configuration:
```java
@Configuration
@EnableGigaspacesRepositories("com.yourcompany.foo.bar")
public class ContextConfiguration {
    
    @Bean
    public AnyClassHere personRepositoryImpl() {
        // further configuration
    }
    
    // other bean definitions omitted
}
```