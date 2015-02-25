Spring Data XAP - Transactions support
======================================

Spring Data XAP comes with a support of declarative Spring transactions based on `OpenSpaces` transaction managers. In order to apply transactional behaviour, the transaction manager must be provided as a reference when constructing the `GigaSpace` bean.

In this example basic transaction manager configuration is provided along the use of `@Transactional` method and rollbacks. Pay attention to next points of transactional behaviour usage:

1. Context configuration (`spring-context.xml`) contains transaction manager declaration and wiring into `GigaSpace` bean

2. Service class `ServiceWithTransaction` got a method annotated with `@Transactional`

3. Current transaction can be got using `space().getCurrentTransaction()` method, as shown in `ServiceWithTransaction`

To run the example, refer to `TransactionMain` class. It will delegate the call to `TransactionExample` which will show transactional save with rollback and without. Running the example should produce the next output:

```
USE TRANSACTIONS
...
Run service method with rollback..
Current transaction: ServerTransaction [id=2, manager=TxnMgrProxy [proxyId=f1f97768-8f63-4e51-a3cc-71fae0a4877e, isDirect=true]]
Saved person: Person{id=1, name='Nick', active=true, position='accountant', age=22}
Set rollback status
Try to get person, that has been saved in transaction: null
Run service method without rollback..
Current transaction: ServerTransaction [id=3, manager=TxnMgrProxy [proxyId=f1f97768-8f63-4e51-a3cc-71fae0a4877e, isDirect=true]]
Saved person: Person{id=2, name='Mary', active=false, position='teacher', age=29}
Try to get person, that has been saved in transaction: Person{id=2, name='Mary', active=false, position='teacher', age=29}
...
```

To read more on this topic, please, refer to [XAP Transactions](http://docs.gigaspaces.com/xap101/transaction-overview.html).