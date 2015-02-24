Spring Data XAP - Change API
============================

> This example runs using Querydsl code, to compile it you will require `Q...` classes for POJOs. To generate them, simply execute next maven command in project root: `mvn clean install`

The Spring Data XAP supports [XAP Change API](http://docs.gigaspaces.com/xap101/change-api.html) allowing to update existing objects in space by specifying only the required change instead of passing the entire updated object. It reduces network traffic between the client and the space. It also can prevent the need of reading the existing object prior to the change operation because the change operation can specify how to change the existing property without knowing its current value.

There are two possible ways you can use Change API within Xap Repositories.

The first option would be to use `XapQueryDslPredicateExecutor.change` method built in Querydsl style. It accepts `QChangeSet` argument that is literally a `ChangeSet` with Querydsl syntax.
> Note that to use Change API in this manner, Querydsl `Q...` classes must be generated.

The second option is to call native Change API by accessing `space()` in `XapRepository`. For that, `GigaSpace.change` methods along with `ChangeSet` class can be used.

> It is preferable to use the first approach since queries will break during compilation and not in run-time. With the second one you could rename POJOs fields and keep invalid field names in the `ChangeSet`

In this example you will see the use of both Querydsl syntax and native Change API syntax. Running the example (`ChangeApiMain` class) must produce the next output:

```
CHANGE API EXAMPLE
...
CHANGE API USING QUERY DSL
Increment age
Before changes Person{id=1, name='Nick', active=true, position='accounting', age=22}
After changes Person{id=1, name='Nick', active=true, position='accounting', age=27}
Set field name
Before changes Person{id=2, name='Mary', active=false, position='teacher', age=29}
After changes Person{id=2, name='Maria', active=false, position='teacher', age=29}
CHANGE API USING NATIVE XAP API
Decrement Paul age
Before changes Person{id=4, name='Paul', active=false, position='accounting', age=43}
Before changes Person{id=4, name='Paul', active=false, position='accounting', age=41}
...
```

To read more on this topic, please, refer to [XAP Change API](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#change).