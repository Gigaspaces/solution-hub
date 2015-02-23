#### <a name="change"/>4.3 XAP Change API

The Spring Data XAP supports [XAP Change API](http://docs.gigaspaces.com/xap101/change-api.html) allowing to update existing objects in space by specifying only the required change instead of passing the entire updated object. It reduces network traffic between the client and the space. It also can prevent the need of reading the existing object prior to the change operation because the change operation can specify how to change the existing property without knowing its current value.

There are two possible ways you can use Change API within Xap Repositories. The first option is to call native Change API by accessing `space()` in `XapRepository`. For that, `GigaSpace.change` methods along with `ChangeSet` class can be used. Full explanation and code examples can be found at [Change API](http://docs.gigaspaces.com/xap101/change-api.html).

The second option would be to use `XapQueryDslPredicateExecutor.change` method built in Querydsl style. It accepts `QChangeSet` argument that is literally a `ChangeSet` with Querydsl syntax:
```java
${PersonServiceImpl.java}
```
> To start using Querydsl Change API syntax, refer to [Querydsl Support](#querydsl)

Full list of supported change methods can be found in [Appendix C](#appendix-c).