#### <a name="repositories"/>2.2 XAP Repositories

This part of the document explains how to configure and start using XAP Repositories with Spring Data. While one can try to directly operate with `GigaSpace` created from the [previous section](#support) to perform read and write operations, it is generally easier to use Spring Data Repositories for the same purposes. This approach significantly reduces the amount of boilerplate code from your data-access layer as well as gives you more flexibility and cleaner code which is easy to read and support. `GigaSpace` will be still available with `space()` method at `XapRepository` interface.

> Spring Data XAP supports all Spring Data Commons configuration features like exclude filters, standalone configuration, manual wiring, etc. For more details on how to apply them, please, refer to [Creating Repository Instances](http://docs.spring.io/spring-data/commons/docs/current/reference/html/#repositories.create-instances).

To start with handy Spring Data XAP features you will need to create your repository interface extending `XapRepository` and tell Spring Container to look for such classes.

> Spring Data XAP does not support `ignoreCase` and `nullHandling` in query expressions and `Sort`.

An example of such user-defined repository with no additional functionality is given below:
```java
${PersonRepository.java}
```
> Note that you define the type of data to be stored and the type of it's id.

##### <a name="repositories-xml"/>Registering XAP repositories using XML-based metadata

While you can use Spring’s traditional `<beans/>` XML namespace to register an instance of your repository implementing `XapRepository` with the container, the XML can be quite verbose as it is general purpose. To simplify configuration, Spring Data XAP provides a dedicated XML namespace.

To enable Spring search for repositories, add the next configuration if you are using XML-based metadata:
```xml
${repository-context.xml}
```
> Note that Spring Container will search for interfaces extending `XapRepository` in package and it's subpackages defined under `base-package` attribute.

> If you have several space declarations, repositories will not be able to wire the space automatically. To define the space to be used by XAP Repositories, just add a `gigaspace` attribute with a proper bean id.

##### <a name="repositories-java"/>Registering XAP repositories using Java-based metadata

To achieve the same configuration with Java-based bean metadata, simply add `@EnableXapRepositories` annotation to configuration class:
```java
${JavaConfiguration.java}
```
> Note that `base-package` can be defined as a value of `@EnableXapRepositories` annotation. Also, GigaSpace bean can be explicitly wired with `gigaspace` attribute.

##### <a name="repositories-exclude"/>Excluding custom interfaces from the search

If you need to have an interface that won't be treated as a Repository by Spring Container, you can mark it with `@NoRepositoryBean` annotation:
```java
${BaseRepository.java}
```

##### <a name="repositories-multi"/>Multi-space configuration

Sometimes it is required to have different groups of repositories to store and exchange data in different spaces. Configuration for such case will have several space declarations and several repository groups. For each group the space to use will be assigned using `gigaspace` attribute referring to `GigaSpace` bean id. Usually, groups of repositories will be placed in subpackages - it makes system structure clearer and eases configuration as well.

Here is an example of multi-space configuration in XML-based metadata:
```xml
${multi-space-context.xml}
```

In Java-based configuration you would have to separate groups of repositories into different sub-contexts:
```java
${MultiSpaceConfiguration.java}
```
