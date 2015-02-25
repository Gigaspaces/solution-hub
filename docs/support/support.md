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

A Local View is a Client Side Cache that maintains a subset of the master space’s data, allowing the client to read distributed data without performing any remote calls or data serialization. Data is streamed into the client local view based on predefined criteria (a collection of SQLQuery objects) specified by the client when the local view is created.

To read more on Local View, please, proceed to [Local View Reference](http://docs.gigaspaces.com/xap101/local-view.html).

###### _Space Persistence_

A Space Persistence is a configuration where space data is persisted into permanent storage and retrieved from it.

Read more on persisting the space data [here](http://docs.gigaspaces.com/xap100/java-tutorial-part7.html).

###### _Event Processing_

You might want to organize your system into event-driven structure. Using XAP there are several native features that will help you achieve your goals, among them: [Notify Container](http://docs.gigaspaces.com/xap101/notify-container-overview.html), [Polling Container](http://docs.gigaspaces.com/xap101/polling-container-overview.html), [Archive Container](http://docs.gigaspaces.com/xap101/archive-container.html).

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

##### Modeling Your Data

Spring Data XAP comes with the transparent support of XAP native features. Among them, some additional configuration can be applied to your POJOs to boost up the performance, reduce memory usage or just ease the model understanding. When building data model using Spring Data XAP you might want to pay attention to the next features:

###### _Indexing_

The most well-known data store function that allows to boost up common queries performance is index support. XAP provides several options here: basic, compound and unique indexes. All of these features can be applied by simply annotating POJO classes or their fields, e.g. with `@SpaceIndex` or `@CompoundSpaceIndex` annotations. Please, refer to [Indexing](http://docs.gigaspaces.com/xap101/indexing-overview.html) for more details and examples of POJO classes.

###### _Storage Types_

You can define the form in which objects will be stored in Space either with annotations on each POJO in your model or with defining default Storage Type for the whole Space. This is done to save up time on serialization/de-serialization, reduce memory usage or to define schema that will change in time. Three Storage Types are available for POJOs: `OBJECT`, `BINARY` and `COMPRESSED`. Again, whole configuration can be applied just by annotation your model classes. To read more on this feature, please, refer to [Storage Types](http://docs.gigaspaces.com/xap101/storage-types---controlling-serialization.html).

###### _Exclusion_

You can mark some POJO properties with `@SpaceExclude` to disable writing their values to the Space. It will also affect Querydsl `Q...` classes generation from POJOs - marked fields won't be available for querying in Querydsl style.

###### _Other Annotation-based Features_

There are lots of other useful annotation-based configuration possibilities available to you. For the full list of them, please, refer to [Annotation based Metadata](http://docs.gigaspaces.com/xap101/pojo-annotation-overview.html).