Spring Data XAP - Document Storage Support
==========================================

The Spring Data XAP supports the [Document API](http://docs.gigaspaces.com/xap101/document-api.html) which exposes the space as Document Store. A document, which is represented by the class `SpaceDocument`, is a collection of key-value pairs, where the keys are strings and the values are primitives, `String`, `Date`, other documents, or collections thereof. Most importantly, the Space is aware of the internal structure of a document, and thus can index document properties at any nesting level and expose rich query semantics for retrieving documents.

In this example, simple and extended documents usage is displayed. There are several points to pay attention to:
* document schema is defined in `spring-context.xml` in Space declaration
* type-safe wrapper `PersonDocument` is created over `SpaceDocument` enabling both static and dynamic schema
* repository interfaces `MeetingDocumentRepository` and `PersonDocumentRepository` extends the `XapDocumentRepository` which provides basic CRUD operations over `SpaceDocument`

Running the example (`DocumentApiMain` class) will produce the next output:

```
DOCUMENT STORAGE SUPPORT EXAMPLE
...
SIMPLE DOCUMENT STORAGE
Saving Meeting documents into space: [... {meetingRoom=MeetingRoom{address=null, name='green'},startTime=Tue Mar 03 15:01:14 EET 2015,id=1}, ... {meetingRoom=MeetingRoom{address=null, name='red'},startTime=Tue Mar 03 15:01:14 EET 2015,id=2}]
Current Meeting space state: [... {startTime=Tue Mar 03 15:01:14 EET 2015,id=1,meetingRoom=MeetingRoom{address=null, name='green'}}, ... {startTime=Tue Mar 03 15:01:14 EET 2015,id=2,meetingRoom=MeetingRoom{address=null, name='red'}}]
Got Meetings in green room: [... {startTime=Tue Mar 03 15:01:14 EET 2015,id=1,meetingRoom=MeetingRoom{address=null, name='green'}}]
EXTENDED DOCUMENT STORAGE
Saving Person document into space: ... {id=1,name=Chris,age=22}
Current Person space state: [... {name=Chris,age=22,id=1}]
Saving Person document with custom field into space: ... {name=Paul,id=2,customField=customValue,age=30}
Got Person documents with custom field: [... {age=30,name=Paul,customField=customValue,id=2}]
...
```

To read more on this topic, please, refer to [XAP Document Storage Support](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#document).