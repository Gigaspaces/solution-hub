#### <a name="transaction"/>4.6 Gigaspaces Transactions

Spring Data Gigaspaces comes with a support of declarative Spring transactions based on `OpenSpaces` transaction managers. In order to apply transactional behaviour, the transaction manager must be provided as a reference when constructing the `GigaSpace` bean. For example (using the distributed transaction manager):
```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:gigaspaces-data="http://www.springframework.org/schema/data/gigaspaces"
       xmlns:os-core="http://www.openspaces.org/schema/core"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/data/gigaspaces http://www.springframework.org/schema/data/gigaspaces/spring-data-gigaspaces.xsd
        http://www.openspaces.org/schema/core http://www.openspaces.org/schema/14.5/core/openspaces-core.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd">

  <gigaspaces-data:repositories base-package="com.yourcompany.foo.bar"/>

  <!-- Enables the detection on @Transactional annotations -->
  <tx:annotation-driven transaction-manager="transactionManager"/>

  <!-- Space declaration, nothing transaction-special here -->
  <os-core:space-proxy id="space" name="space"/>

  <!-- GigaSpace bean with provided transaction manager -->
  <os-core:giga-space id="gigaSpace" space="space" tx-manager="transactionManager"/>

  <!-- OpenSpaces distributed transaction manager -->
  <os-core:distributed-tx-manager id="transactionManager"/>

</beans>

```

Now your service layer methods can be marked as `@Transactional`:

```java
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void transactionalMethod(Person person) {
        ...
    }

}
```

To read more on configuring Gigaspaces Transactions and transaction managers, please, refer to [Transactions Reference](http://docs.gigaspaces.com/latest/dev-java/transaction-overview.html).
