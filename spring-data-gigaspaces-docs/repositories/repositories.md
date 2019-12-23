#### <a name="repositories"/>2.2 Gigaspaces Repositories

This part of the document explains how to configure and start using Gigaspaces Repositories with Spring Data. While one can try to directly operate with `GigaSpace` created from the [previous section](#support) to perform read and write operations, it is generally easier to use Spring Data Repositories for the same purposes. This approach significantly reduces the amount of boilerplate code from your data-access layer as well as gives you more flexibility and cleaner code which is easy to read and support. `GigaSpace` will be still available with `space()` method at `GigaspacesRepository` interface.

> Spring Data Gigaspaces Supports all Spring Data Commons configuration features like exclude filters, standalone configuration, manual wiring, etc. For more details on how to apply them, please, refer to [Creating Repository Instances](http://docs.spring.io/spring-data/commons/docs/current/reference/html/#repositories.create-instances).

To start with handy Spring Data Gigaspaces features you will need to create your repository interface extending `GigaspacesRepository` and tell Spring Container to look for such classes.

> Spring Data Gigaspaces does not support `ignoreCase` and `nullHandling` in query expressions and `Sort`.

An example of such user-defined repository with no additional functionality is given below:
```java
public interface PersonRepository extends GigaspacesRepository<Person, String> {
}
```
> Note that you define the type of data to be stored and the type of it's id.

##### <a name="repositories-xml"/>Registering Gigaspaces Repositories using XML-based metadata

While you can use Spring’s traditional `<beans/>` XML namespace to register an instance of your repository implementing `GigaspacesRepository` with the container, the XML can be quite verbose as it is general purpose. To simplify configuration, Spring Data Gigaspaces provides a dedicated XML namespace.

To enable Spring search for repositories, add the next configuration if you are using XML-based metadata:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:gigaspaces-data="http://www.springframework.org/schema/data/gigaspaces"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="
         http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
         http://www.springframework.org/schema/data/gigaspaces http://www.springframework.org/schema/data/gigaspaces/spring-data-gigaspaces.xsd">

  <gigaspaces-data:repositories base-package="com.yourcompany.foo.bar"/>

  <!-- other configuration omitted -->

</beans>
```
> Note that Spring Container will search for interfaces extending `GigaspacesRepository` in package and it's subpackages defined under `base-package` attribute.

Repositories will look for `GigaSpace` bean by type in the context, if this behaviour is not overridden. If you have several space declarations, repositories will not be able to wire the space automatically. To define the space to be used by Gigaspaces Repositories, just add a `gigaspace` attribute with a proper bean id. An example can be found [below](#repositories-multi).

##### <a name="repositories-java"/>Registering Gigaspaces Repositories using Java-based metadata

To achieve the same configuration with Java-based bean metadata, simply add `@EnableGigaspacesRepositories` annotation to configuration class:
```java
@Configuration
@EnableGigaspacesRepositories("com.yourcompany.foo.bar")
public class ContextConfiguration {
    // bean definitions omitted
}
```
> Note that `base-package` can be defined as a value of `@EnableGigaspacesRepositories` annotation. Also, `GigaSpace` bean will be automatically found in the context by type or can be explicitly wired with `gigaspace` attribute. An example can be found [below](#repositories-multi).

##### <a name="repositories-exclude"/>Excluding custom interfaces from the search

If you need to have an interface that won't be treated as a Repository by Spring Container, you can mark it with `@NoRepositoryBean` annotation:
```java
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends GigaspacesRepository<T, ID> {

    // you can define methods that apply to all other repositories

    T findByName(String name);

    ...

}
```

##### <a name="repositories-multi"/>Multi-space configuration

Sometimes it is required to have different groups of repositories to store and exchange data in different spaces. Configuration for such case will have several space declarations and several repository groups. For each group the space to use will be assigned using `gigaspace` attribute referring to `GigaSpace` bean id. Usually, groups of repositories will be placed in subpackages - it makes system structure clearer and eases configuration as well.

Here is an example of multi-space configuration in XML-based metadata:
```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:gigaspaces-data="http://www.springframework.org/schema/data/gigaspaces"
       xmlns:os-core="http://www.openspaces.org/schema/core"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/data/gigaspaces http://www.springframework.org/schema/data/gigaspaces/spring-data-gigaspaces.xsd
           http://www.openspaces.org/schema/core http://www.openspaces.org/schema/14.5/core/openspaces-core.xsd">

  <!-- Initializes repositories in .operational with operationalGSpace -->
  <gigaspaces-data:repositories base-package="com.yourcompany.foo.bar.repository.operational" gigaspace="operationalGSpace"/>
  <!-- Initializes repositories in .billing with billingGSpace -->
  <gigaspaces-data:repositories base-package="com.yourcompany.foo.bar.repository.billing" gigaspace="billingGSpace"/>

  <os-core:space-proxy id="billingSpace" name="billing"/>
  <os-core:giga-space id="billingGSpace" space="billingSpace"/>

  <os-core:embedded-space id="operationalSpace" name="operational"/>
  <os-core:giga-space id="operationalGSpace" space="operationalSpace"/>

  <!-- other configuration omitted -->

</beans>

```

In Java-based configuration you would have to separate groups of repositories into different sub-contexts:
```java
@Configuration
@Import({OperationalRepositories.class, BillingRepositories.class})
public class ContextConfiguration {
    /* other beans declaration omitted */
}

@Configuration
@EnableGigaspacesRepositories(basePackages = "com.yourcompany.foo.bar.repository.operational", gigaspace = "operationalGSpace")
class OperationalRepositories {
    /**
     * @return embedded operational space configuration
     */
    @Bean
    public GigaSpace operationalGSpace() {
        return new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./operational")).gigaSpace();
    }
}

@Configuration
@EnableGigaspacesRepositories(basePackages = "com.yourcompany.foo.bar.repository.billing", gigaspace = "billingGSpace")
class BillingRepositories {
    /**
     * @return proxy billing space configuration
     */
    @Bean
    public GigaSpace billingGSpace() {
        return new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/billing")).gigaSpace();
    }
}
```
