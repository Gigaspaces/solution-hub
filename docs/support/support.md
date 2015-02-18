Spring Data XAP provides full configuration and initialization of the connection to XAP data storage through Spring’s IoC container.

To get started, first you need to set up a Service Grid and deploy the Data Grid on it. Refer to [XAP Quick Start guide](http://docs.gigaspaces.com/xap100/your-first-data-grid-application.html) an explanation on how to startup a space storage. Once installed, deploying Data Grid is typically a matter of executing the next commands from the `GS_HOME/bin` folder:

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
**(1)** JINI search path for the active space with name `space`

**(2)** IJSpace instance injection into `SpaceClient`

#### Connecting to space using Java based metadata

The same configuration can be achieved with next Java-based bean metadata

```java
${configuration-java.txt}
```

##### Registering XAP Repository using XML-based metadata

While you can use Spring’s traditional `<beans/>` XML namespace to register an instance of your repository implementing `org.springframework.data.xap.repository.XapRepository` with the container, the XML can be quite verbose as it is general purpose. To simplify configuration, Spring Data XAP provides a dedicated XML namespace.