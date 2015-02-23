#### <a name="repositories"/>2.2 XAP Repositories

This part of the document explains how to configure and start using XAP Repositories with Spring Data. While one can try to directly operate with `GigaSpace` created from the [previous section](#support) to perform read and write operations, it is generally easier to use Spring Data Repositories for the same purposes. This approach significantly reduces the amount of boilerplate code from your data-access layer as well as gives you more flexibility and cleaner code which is easy to read and support. `GigaSpace` will be still available with `space()` method at `XapRepository` interface.

> Spring Data XAP supports all Spring Data Commons configuration features like exclude filters, standalone configuration, manual wiring, etc. For more details on how to apply them, please, refer to [Creating Repository Instances](http://docs.spring.io/spring-data/commons/docs/current/reference/html/#repositories.create-instances).

To start with handy Spring Data XAP features you will need to create your repository interface extending `XapRepository` and tell Spring Container to look for such classes.

An example of such user-defined repository with no additional functionality is given below:
```java
${PersonRepository.java}
```
> Note that you define the type of data to be stored and the type of it's id.

##### Registering XAP Repository using XML-based metadata

While you can use Spring’s traditional `<beans/>` XML namespace to register an instance of your repository implementing `XapRepository` with the container, the XML can be quite verbose as it is general purpose. To simplify configuration, Spring Data XAP provides a dedicated XML namespace.

To enable Spring search for repositories, add the next configuration if you are using XML-based metadata:
```xml
${repository-context.xml}
```
> Note that Spring Container will search for interfaces extending `XapRepository` in package and it's subpackages defined under `base-package` attribute.

##### Registering XAP Repository using Java-based metadata

To achieve the same configuration with Java-based bean metadata, simply add `@EnableXapRepositories` annotation to configuration class:
```java
${JavaConfiguration.java}
```
> Note that `base-package` can be defined as a value of `@EnableXapRepositories` annotation.

##### Excluding custom interfaces from the search

If you need to have an interface that won't be treated as a Repository by Spring Container, you can mark it with `@NoRepositoryBean` annotation:
```java
${BaseRepository.java}
```