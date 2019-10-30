Spring Data XAP - Querydsl support
==================================

> This example runs using Querydsl code, to compile it you will require `Q...` classes for POJOs. To generate them, simply execute next maven command in project root: `mvn clean install`

The Querydsl framework let's you write type-safe queries in Java instead of using good old query strings. It gives you several advantages: code completion in your IDE, domain types and properties can be accessed in a type-safe manner which reduces the probability of query syntax errors during run-time. If you want to read more about Querydsl, please, proceed to [Querydsl website](http://www.querydsl.com/).

The priceless feature of Querydsl is that if you change the POJO (remove or rename its fields), the project will not built properly until you change all the queries on those fields. This with help you avoid having application to run defective queries causing run-time exception and event worse - causing data corruption without any error.

In this example, pay attention to the two configuration points that allow querydsl code to run:

1. Parent `pom.xml` contains configuration of `Maven APT` plugin to run `XapQueryDslAnnotationProcessor` - this creates a `Q...` class for each POJO annotated with `@SpaceClass`

2. `PredicatePersonRepository` extends `XapQueryDslPredicateExecutor` defining `Person` as a domain class

To run the example, refer to `QueryDslMain` class. It will delegate configuration to `QueryDslExample` which will show several query operations available in `XapQueryDslPredicateExecutor` interface. Running the example should produce the next output:

```
QUERY DSL EXAMPLE
...
Find person with name Nick
Person{id=1, name='Nick', active=true, position='accounting', age=22}
Find persons older than 26, sort them by name
Person{id=5, name='Jim', active=true, position='security', age=24}
Person{id=1, name='Nick', active=true, position='accounting', age=22}
Find persons by name list
Person{id=1, name='Nick', active=true, position='accounting', age=22}
Person{id=2, name='Mary', active=false, position='teacher', age=29}
Find persons, whose names contain J
Person{id=5, name='Jim', active=true, position='security', age=24}
Person{id=3, name='John', active=true, position='accounting', age=31}
Find persons, whose names contain 4 letters
Person{id=1, name='Nick', active=true, position='accounting', age=22}
Person{id=2, name='Mary', active=false, position='teacher', age=29}
Person{id=3, name='John', active=true, position='accounting', age=31}
Person{id=4, name='Paul', active=false, position='accounting', age=43}
...
```

> Note that `QPerson.person` is imported statically - this allows to make queries more readable

To read more on this topic, please, refer to [Querydsl Support](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#querydsl).