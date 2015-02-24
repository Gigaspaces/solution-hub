Spring Data XAP - Crud repository
============================

Crud repository allow to use crud operation with minimal efforts. For using it you have to simply extend `XapRepository<T, ID>` interface and define your domain class.

To run the example, refer to `CrudMain` class. It will delegate configuration to `CrudExample` which will show several query operations available in `CrudRepository` interface. Running the example should produce the next output:

```
...
14:52:17.420 [main] INFO  o.s.d.x.examples.basic.crud.CrudMain - CRUD REPOSITORY EXAMPLE
...
14:52:21.383 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Find one (by id = green).. 
14:52:21.388 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}
14:52:21.388 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Find all.. 
14:52:21.420 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - [MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}, MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}]
14:52:21.421 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Save new room.. 
14:52:21.422 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Saved room: MeetingRoom{address=Address{city='London', localAddress='Main Street 18'}, name='red'}
14:52:21.430 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Count objects.. Result is: 4
14:52:21.430 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Delete one by id.. 
14:52:21.432 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Delete another one.. 
14:52:21.433 [main] INFO  o.s.d.x.e.basic.crud.CrudExample - Current number of room is: 2
14:52:21.435 [main] INFO  o.s.data.xap.examples.DataSet - All data has been deleted from space
```