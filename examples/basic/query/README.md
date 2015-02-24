Spring Data XAP - Query creation
============================

This example demonstrate how to use query creation mechanism. 

1. The first way: to create appropriate method in extension of `XapRepository`. Method should be named in camel style and use [supported keywords] (https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#appendix-a). Method name and parameters would be parsed and converted to query. 
2. Also you can use `@Query` annotation to explicitly specify query.

To run the example, refer to `QueryMethodsMain` class. It will delegate configuration to `QueryMethodsExample` which will show several possible queries. Running the example should produce the next output:

```
...
15:11:01.456 [main] INFO  o.s.d.x.e.b.query.QueryMethodsMain - QUERY CREATION EXAMPLE
...
15:11:04.860 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Booking room.. 
15:11:04.917 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Created meeting Meeting{id=1, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}, personList=[Person{id=1, name='Nick', active=true, position='accounting', age=22}, Person{id=2, name='Mary', active=false, position='teacher', age=29}], startTime=10 01-03-2015}
15:11:04.917 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Booking room.. 
15:11:04.918 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Created meeting Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}
15:11:04.918 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Try to create meeting in occupied room.. 
15:11:04.919 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Room has already booked! Try another time or room.
15:11:04.919 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Create meeting for active persons from accounting department.. 
15:11:04.943 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Created meeting Meeting{id=4, meetingRoom=MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, personList=[Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=1, name='Nick', active=true, position='accounting', age=22}], startTime=12 20-04-2015}
15:11:04.943 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Get all meetings after 10 01-03-2015
15:11:04.946 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}, Meeting{id=4, meetingRoom=MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, personList=[Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=1, name='Nick', active=true, position='accounting', age=22}], startTime=12 20-04-2015}]
15:11:04.946 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Get info about meetings in New York rooms
15:11:04.950 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [Meeting{id=1, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}, personList=[Person{id=1, name='Nick', active=true, position='accounting', age=22}, Person{id=2, name='Mary', active=false, position='teacher', age=29}], startTime=10 01-03-2015}, Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}]
15:11:04.950 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Get all rooms that located in Kyiv (using @Query annotation)
15:11:04.951 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}]
15:11:04.951 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Find persons older than 25, sort them by reducing id
15:11:04.971 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [Person{id=4, name='Paul', active=false, position='accounting', age=43}, Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=2, name='Mary', active=false, position='teacher', age=29}]
15:11:04.971 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - Find active persons, page 1 (two items)
15:11:04.975 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=1, name='Nick', active=true, position='accounting', age=22}]
15:11:04.975 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - page 2
15:11:04.976 [main] INFO  o.s.d.x.e.b.q.QueryMethodsExample - [Person{id=3, name='John', active=true, position='accounting', age=31}]
15:11:04.979 [main] INFO  o.s.data.xap.examples.DataSet - All data has been deleted from space
```
