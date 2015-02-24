Spring Data XAP - Crud Repository
=================================

Crud repository allows you to use crud operations with minimal efforts. To start with it you have to simply extend `XapRepository<T, ID>` interface and define your domain class.

To run the example, refer to `CrudMain` class. It will delegate configuration to `CrudExample` which will show several query operations available in `CrudRepository` interface. Running the example should produce the next output:

```
...
CRUD REPOSITORY EXAMPLE
...
Find one (by id = green)..
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}
Find all..
[MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}]
Save new room..
Saved room: MeetingRoom{address=Address{city='London', localAddress='Main Street 18'}, name='red'}
Count objects.. Result is: 4
Delete one by id..
Delete another one..
Current number of room is: 2
...
```