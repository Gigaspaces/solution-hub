#### <a name="support"/>2.1 XAP Support

The first task to handle while developing XAP application using Spring would be to configure a connection to the active space inside the Spring IoC container. This part of the document will show you how basic connection can be applied using XML or Java based Spring configurations.

To get started, first you need to get the space running: either configure an Embedded Space or set up a Service Grid and deploy the Data Grid on it.

To use the Embedded Space you don't need to start any additional processes on your environment.

To start the Data Grid refer to [XAP Quick Start guide](http://docs.gigaspaces.com/xap100/your-first-data-grid-application.html) for an explanation on how to startup a space storage. Once installed, deploying Data Grid is typically a matter of executing the next commands from the `GS_HOME/bin` folder:

```
${gsa-script.txt}
```

Then in your project (assuming you build it with [Maven](http://maven.apache.org/)) add the following to `pom.xml` dependencies section:

```xml
${data-dependency.xml}
```

##### Connecting to space using XML based metadata

To use XAP Repository you need to provide a connection to space with an instance of `GigaSpace`. Basic access can be easily configured with next Spring XML configuration:

```xml
${space-proxy-context.xml}
```

If you want to use an Embedded Space, the next configuration will suite your needs:

```xml
${embedded-space-context.xml}
```

##### Connecting to space using Java based metadata

The same configuration can be achieved with next Java-based bean metadata:

```java
${JavaConfiguration.java}
```

Or for the Embedded Space:

```java
${JavaEmbeddedConfiguration.java}
```

##### Other commonly used space configurations

In some projects you might want to apply other configurations to your space. There are a lot of options available to you.

###### _Local cache_

A Local Cache is a Client Side Cache that maintains a subset of the master space’s data based on the client application’s recent activity. The local cache is created empty, and whenever the client application executes a query the local cache first tries to fulfill it from the cache, otherwise it executes it on the master space and caches the result locally for future queries.

To find out more about Local Cache and it's configuration, please, proceed to [Local Cache Reference](http://docs.gigaspaces.com/xap101/local-cache.html).

###### _Local view_

A Local View is a Client Side Cache that maintains a subset of the master space’s data, allowing the client to read distributed data without performing any remote calls or data serialization.

Data is streamed into the client local view based on predefined criteria (a collection of SQLQuery objects) specified by the client when the local view is created.

During the local view initialization, data is loaded into the client’s memory based on the view criteria. Afterwards, the local view is continuously updated by the master space asynchronously - any operation executed on the master space that affects an entry which matches the view criteria is automatically propagated to the client.

The Local View can be used with financial applications (e.g. trading , market data , portfolio management) where each client (e.g. trader , broker) might need to access specific products / securities / equities data in real time. In this case, the application instance can generate a client side cache customized for the user needs.

To read more on Local View, please, proceed to [Local View Reference](http://docs.gigaspaces.com/xap101/local-view.html).

###### _Space Persistence_

A Space Persistence is a configuration where space data is persisted into permanent storage and retrieved from it. There are many situations where this configuration is required, for example:
* Our online payment system works primarily with the memory space for temporary storage of process data structures, and the permanent storage is used to extend or back up the physical memory of the process running the space.
* Our online payment system works primarily with the database storage and the space is used to make read processing more efficient. Since database access is expensive, the data read from the database is cached in the space, where it is available for subsequently fast read operations.
* When a space is restarted, data from its persistent store can be loaded into the space to speed up incoming query processing.

Read more on persisting the space data [here](http://docs.gigaspaces.com/xap100/java-tutorial-part7.html).

###### _Event Processing_

You might want to organize your system into event-driven structure. Using XAP there are several native features that will help you achieve your goals, among them:
* [Notify Container](http://docs.gigaspaces.com/xap101/notify-container-overview.html) - uses the space inherited support for notifications (continuous query). A notify event operation is mainly used when simulating Topic semantics.
* [Polling Container](http://docs.gigaspaces.com/xap101/polling-container-overview.html) - is an implementation of the polling consumer pattern which uses the Space to receive events. A polling event operation is mainly used when simulating Queue semantics or when using the master-worker design pattern.
* [Archive Container](http://docs.gigaspaces.com/xap101/archive-container.html) - used to transfer historical data into Big-Data storage (for example Cassandra). The typical scenario is when streaming vast number of raw events through the Space, enriching them and then moving them to a Big-Data storage. Typically, there is no intention of keeping them in the space nor querying them in the space.

###### _Space Security_

XAP Security provides comprehensive support for securing your data, services, or both. XAP provides a set of authorities granting privileged access to data, and for performing operations on services.

To find out more about security configuration, please, refer to [Security Guide](http://docs.gigaspaces.com/xap101sec/).

##### Using native write and read operation

`GigaSpace` configured above can be used directly to perform interaction with space. To do so, you can simply inject `GigaSpace` bean into your Repository classes. Let's see an example of such usage. First, here is an example of POJO class:
```java
${Person.java}
```
> Note that class is marked with `@SpaceClass` annotation - it allows Spring XAP to look for entities in your data model and automatically handle their structure. Also, the `getId()` method is marked with `@SpaceId(autogenerate = true)` annotation - it will tell the space to handle ids automatically.

Now `GigaSpace` can be injected and used directly in Repository layer:
```java
${XapPersonRepository.java}
```