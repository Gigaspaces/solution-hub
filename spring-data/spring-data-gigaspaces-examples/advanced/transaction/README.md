Spring Data Gigaspaces - Transactions support
======================================

Spring Data Gigaspaces comes with a support of declarative Spring transactions based on `OpenSpaces` transaction managers. In order to apply transactional behaviour, the transaction manager must be provided as a reference when constructing the `GigaSpace` bean.

In this example basic transaction manager configuration is provided along the use of `@Transactional` method and rollbacks. Pay attention to next points of transactional behaviour usage:

1. Context configuration (`spring-context.xml`) contains transaction manager declaration and wiring into `GigaSpace` bean

2. Service class `TransactionalService` got a method annotated with `@Transactional`

3. Current transaction can be got using `space().getCurrentTransaction()` method, as shown in `TransactionalService`

To run the example, refer to `TransactionMain` class. It will delegate the call to `TransactionExample` which will show transactional save with rollback and without. Running the example should produce the next output:

```
...
USE TRANSACTIONS
...
Created rooms: [MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}, MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}, MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}]
Run service method with expected rollback.. 
Current transaction: ServerTransaction [id=2, manager=TxnMgrProxy [proxyId=4c904d4e-821b-4600-b703-02b03ff516d9, isDirect=true]]
Try to save yellow
yellow room has been saved.
Try to save green
Unavailable name
Run service method with correct data..
Current transaction: ServerTransaction [id=3, manager=TxnMgrProxy [proxyId=4c904d4e-821b-4600-b703-02b03ff516d9, isDirect=true]]
Try to save grey
grey room has been saved.
Try to save white
white room has been saved.
Result room list: 
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}
MeetingRoom{address=Address{city='Kyiv', localAddress='Main Street, 12'}, name='blue'}
MeetingRoom{address=Address{city='Minsk', localAddress='Main Street 44'}, name='grey'}
MeetingRoom{address=Address{city='Amsterdam', localAddress='Main Street 28'}, name='white'}
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}
```

To read more on this topic, please, refer to [Gigaspaces Transactions](http://docs.gigaspaces.com/latest/dev-java/transaction-overview.html).
