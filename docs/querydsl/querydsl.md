#### <a name="querydsl"/>4.2 Querydsl Support

The Querydsl framework let's you write type-safe queries in Java instead of using good old query strings. It gives you several advantages: code completion in your IDE, domain types and properties can be accessed in a type-safe manner which reduces the probability of query syntax errors during run-time. If you want to read more about Querydsl, please, proceed to [Querydsl website](http://www.querydsl.com/).

Several steps are needed to start using XAP Repositories Querydsl support. First, mark wanted repository as a `XapQueryDslPredicateExecutor` along with `XapRepository`:
```java
${PersonRepository.java}
```

> Note that you define the type of data to be accessed with Querydsl.

Then, add source processor to your maven build (`pom.xml`) using Maven Annotation Processing Tool plugin:
```xml
${maven-plugin.xml}
```

This configuration will call `XapQueryDslAnnotationProcessor` before compiling your project sources. It will look for POJOs marked with `@SpaceClass` annotation and generate `Q...` classes for them that allow you to build up Querydsl `Predicate`s. Before using such classes, you have to call this processor with `process-sources` maven goal, or just call `install` if you are already using it:
```
${maven-commands.txt}
```

Now you can query your repository using Querydsl `Predicate`s:
```java
${PersonServiceImpl.java}
```

Full list of supported `Predicate` methods can be found in [Appendix B](#appendix-b).