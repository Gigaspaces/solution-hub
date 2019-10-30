Spring Data XAP - Query creation
================================

This example demonstrates how to use query creation mechanism.

1. The first way is to create appropriate method in extension of `XapRepository`. Method should be named in camel style and use [supported keywords](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#appendix-a). Method name and arguments will be parsed and converted to query.
2. An alternative option would be to use `@Query` annotation to explicitly specify the query.
3. Another alternative is to use named queries and specify them in properties file.

To run the example, refer to `QueryMethodsMain` class. It will delegate configuration to `QueryMethodsExample` which will show several possible queries. Running the example should produce the next output:

```
QUERY CREATION EXAMPLE
...
Booking room.. 
Created meeting Meeting{id=1, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}, personList=[Person{id=1, name='Nick', active=true, position='accounting', age=22}, Person{id=2, name='Mary', active=false, position='teacher', age=29}], startTime=10 01-03-2015}
Booking room.. 
Created meeting Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}
Try to create meeting in occupied room.. 
Room has already booked! Try another time or room.
Create meeting for active persons from accounting department.. 
Created meeting Meeting{id=4, meetingRoom=MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, personList=[Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=1, name='Nick', active=true, position='accounting', age=22}], startTime=12 20-04-2015}
Get all meetings after 10 01-03-2015
[Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}, Meeting{id=4, meetingRoom=MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, personList=[Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=1, name='Nick', active=true, position='accounting', age=22}], startTime=12 20-04-2015}]
Get info about meetings in New York rooms
[Meeting{id=1, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}, personList=[Person{id=1, name='Nick', active=true, position='accounting', age=22}, Person{id=2, name='Mary', active=false, position='teacher', age=29}], startTime=10 01-03-2015}, Meeting{id=2, meetingRoom=MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, personList=[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=4, name='Paul', active=false, position='accounting', age=43}], startTime=10 02-03-2015}]
Get all rooms that located in Kyiv (using @Query annotation)
[MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}]
Find persons older than 25, sort them by reducing id
[Person{id=4, name='Paul', active=false, position='accounting', age=43}, Person{id=3, name='John', active=true, position='accounting', age=31}, Person{id=2, name='Mary', active=false, position='teacher', age=29}]
Find active persons, page 1 (two items)
[Person{id=5, name='Jim', active=true, position='security', age=24}, Person{id=1, name='Nick', active=true, position='accounting', age=22}]
page 2
[Person{id=3, name='John', active=true, position='accounting', age=31}]
...
```
