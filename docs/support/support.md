The first task to handle while developing XAP application using Spring would be to configure a connection to the active space inside the Spring IoC container. This part of the document will show you how basic connection can be applied using XML and Java based Spring configurations.

To get started, first you need to get the space running: set up a Service Grid and deploy the Data Grid on it. Refer to [XAP Quick Start guide](http://docs.gigaspaces.com/xap100/your-first-data-grid-application.html) for an explanation on how to startup a space storage. Once installed, deploying Data Grid is typically a matter of executing the next commands from the `GS_HOME/bin` folder:

```
${gsa-script.txt}
```

Then in your project (assuming you build it with [Maven](http://maven.apache.org/)) add the following to `pom.xml` dependencies section:

```xml
${data-dependency.xml}
```

If possible, change the Spring Framework version to 4.0.x or above:

```xml
${spring-version.xml}
```

##### Connecting to space using XML based metadata

To use XAP Repository you need to provide a connection to space with an instance of `SpaceClient`. Basic access can be easily configured with next Spring XML configuration:

```xml
${space-context.xml}
```

##### Connecting to space using Java based metadata

The same configuration can be achieved with next Java-based bean metadata:

```java
${JavaConfiguration.java}
```

> Java-based configuration will be used in every following examples.

##### Using plain write and read operation

`SpaceClient` configured above can be used directly to perform interaction with space. To do so, you can simply inject `SpaceClient` bean into your Repository classes. Let's see an example of such usage. First, here is an example of POJO class:
```java
${Person.java}
```
> Note that class is marked with `@SpaceClass` annotation - it allows Spring XAP to look for entities in your data model and automatically handle their structure. Also, the `getId()` method is marked with `@SpaceId(autogenerate = true)` annotation - it will tell the space to handle ids automatically.

Now `SpaceClient` can be injected and used directly in Repository layer:
```java
${XapPersonRepository.java}
```