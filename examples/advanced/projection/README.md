Spring Data XAP - Projection API
================================

> This example runs using Querydsl code, to compile it you will require `Q...` classes for POJOs. To generate them, simply execute next maven command in project root: `mvn clean install`

The Spring Data XAP supports [XAP Projection](http://docs.gigaspaces.com/xap101/query-partial-results.html) which allows to read only certain properties for the objects (a.k.a. delta read). This approach reduces network overhead, garbage memory generation and CPU overhead due to decreased serialization time.

`XapRepository` interface provides you with basic `find` methods extended with `Projection` argument.

You can also supply your query methods with `Projection`, just by adding an additional argument to the method declaration.

In the example you will see the usage of Projection API with both Querydsl syntax and XAP Projection syntax. Running the example should produce next output:

```
PROJECTION EXAMPLE
...
PROJECTION USING QUERY DSL
Find person by name, projection with name and age
Person{id=null, name='Nick', active=null, position='null', age=22}
Find room by name, projection with address only
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='null'}
PROJECTION USING SPECIAL PARAMETER
Projection with embedded field address.city
MeetingRoom{address=Address{city='New York', localAddress='null'}, name='null'}
MeetingRoom{address=Address{city='Kyiv', localAddress='null'}, name='null'}
MeetingRoom{address=Address{city='New York', localAddress='null'}, name='null'}
Projection with field name
MeetingRoom{address=null, name='blue'}
MeetingRoom{address=null, name='green'}
MeetingRoom{address=null, name='orange'}
Projection with several fields: name and position
Person{id=null, name='John', active=null, position='accounting', age=null}
Person{id=null, name='Paul', active=null, position='accounting', age=null}
Person{id=null, name='Jim', active=null, position='security', age=null}
Person{id=null, name='Nick', active=null, position='accounting', age=null}
Person{id=null, name='Mary', active=null, position='teacher', age=null}
...
```

> It is preferable to use Querydsl syntax since projections will break during compilation and not in run-time. With the second approach you could rename POJOs fields and keep invalid field names in the `Projection`

To read more on this topic, please, refer to [XAP Projection API](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#projection).