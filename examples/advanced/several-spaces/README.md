Spring Data XAP - Using several spaces
======================================

```
...
16:26:46.919 [main] INFO  o.s.d.x.e.a.s.SeveralSpacesMain - SEVERAL SPACES USING EXAMPLE
...
Space name of the personRepository space1
Space name of the roomRepository space2
Save persons and rooms to repositories.. 
Get persons from repository: 
Person{id=3, name='John', active=true, position='accountant', age=31}
Person{id=5, name='Jim', active=true, position='security', age=24}
Get rooms from repository: 
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}
Try to get rooms from personRepository space
[]
Try to get persons from meetingRoomRepository space
[]
Clean up the first space:
All data has been deleted from space
Clean up the second space:
All data has been deleted from space
```
