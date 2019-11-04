#### <a name="transaction"/>4.6 Gigaspaces Transactions

Spring Data Gigaspaces comes with a support of declarative Spring transactions based on `OpenSpaces` transaction managers. In order to apply transactional behaviour, the transaction manager must be provided as a reference when constructing the `GigaSpace` bean. For example (using the distributed transaction manager):
```xml
${distributed-tx-context.xml}
```

Now your service layer methods can be marked as `@Transactional`:

```java
${ServiceWithTransaction.java}
```

To read more on configuring Gigaspaces Transactions and transaction managers, please, refer to [Transactions Reference](http://docs.gigaspaces.com/latest/dev-java/transaction-overview.html).
