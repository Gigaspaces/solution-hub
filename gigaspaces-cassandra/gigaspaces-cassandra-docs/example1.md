# Example 1 : Quick start
 
This quickstart show how to  convert cassandra mapper project to a gigaspaces mapper project it is implemented [here](https://github.com/Gigaspaces/solution-hub/tree/master/gigaspaces-cassandra/gigaspaces-cassandra-docs/gigaspaces-cassandra-mapper-example1).
 
 The [example 1a](https://github.com/Gigaspaces/solution-hub/tree/master/gigaspaces-cassandra/gigaspaces-cassandra-docs/gigaspaces-cassandra-mapper-example1/gigaspaces-cassandra-mapper-example1a) is a start project that use only cassandra.It's actually based on the [cassandra documentation](https://docs.datastax.com/en/developer/java-driver/4.4/manual/mapper/).
 To run it you will need a running cassandra server locally (or add the contact point).
 
 The [example 1b](https://github.com/Gigaspaces/solution-hub/tree/master/gigaspaces-cassandra/gigaspaces-cassandra-docs/gigaspaces-cassandra-mapper-example1/gigaspaces-cassandra-mapper-example1b)
 is the example 1 ported to gigaspaces cassandra datasource (xml configuration version).
 First you need to add the gigaspaces cassandra datasource dependency :  
 ```xml
     <dependency>
            <groupId>org.openspaces</groupId>
            <artifactId>gigaspaces-cassandra-datasource</artifactId>
            <version>${gigaspaces-cassandra.version}</version>
            <scope>compile</scope>
     </dependency>
```

Secondly you will need (if not already done) to add the  java-driver-mapper-processor :


 ```xml
    <build>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>com.datastax.oss</groupId>
                            <artifactId>java-driver-mapper-processor</artifactId>
                            <version>${cassandra.driver.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

You will need to add Gigaspaces annotation to the entity to make it also compatible to [Gigaspaces pojo format](https://docs.gigaspaces.com/15.0/dev-java/pojo-overview.html).

For the cassandra entity :

 ```java
@Entity
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
public class Product {
    @PartitionKey
    private UUID productId;
    private String description;

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

You will have something like :
 ```java
@SpaceClass
@Entity
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
public class Product {
    @PartitionKey
    private UUID productId;
    private String description;

    @SpaceId
    @SpaceRouting
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

You will need to create a spring xml configuration like that :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:os-core="http://www.openspaces.org/schema/core"
	   xmlns:util="http://www.springframework.org/schema/util"
	   xsi:schemaLocation="
	                    http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
	                    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
	                    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
	                    http://www.openspaces.org/schema/core http://www.openspaces.org/schema/15.0/core/openspaces-core.xsd">

	<context:annotation-config />
	<context:property-placeholder location="classpath:/application.properties"/>

	<bean id="cassandraDataSource" class="org.openspaces.persistency.cassandra.pool.CassandraDataSourceFactoryBean">
	</bean>

	<bean id="cassandraResourcePool" class="org.openspaces.persistency.cassandra.pool.CassandraResourcePoolFactoryBean">
		<property name="cassandraDataSource" ref="cassandraDataSource" ></property>
		<property name="minimumNumberOfConnections" value="${cassandra.ds.minconnections}" ></property>
		<property name="maximumNumberOfConnections" value="${cassandra.ds.maxconnections}" ></property>
	</bean>
	<bean id="cassandraTypeRepository" class=" org.openspaces.persistency.cassandra.types.CassandraTypeRepositoryFactoryBean">
		<property name="connectionPool" ref="cassandraResourcePool" ></property>
		<property name="entitiesPackages">
		<util:list  list-class="java.util.ArrayList" value-type="java.lang.String">
			<value>org.openspaces.persistency.cassandra.example1.entities</value>
		</util:list>
	    </property>
		<property name="defaultKeyspace" value="inventory"></property>
	</bean>

	<bean id="cassandraSpaceDataSource" class="org.openspaces.persistency.cassandra.datasource.CassandraSpaceDataSourceFactoryBean">
		<property name="cassandraTypeRepository" ref="cassandraTypeRepository"></property>
		<property name="batchLimit" value="${cassandra.ds.batchlimit}" ></property>
	</bean>

	<bean id="cassandraSpaceSyncEndpoint" class="org.openspaces.persistency.cassandra.CassandraSpaceSynchronizationEndpointFactoryBean">
		<property name="cassandraTypeRepository" ref="cassandraTypeRepository"></property>
	</bean>

	<os-core:embedded-space id="space" space-name="dataSourceSpace"
							space-data-source="cassandraSpaceDataSource"
	                        space-sync-endpoint="cassandraSpaceSyncEndpoint"/>
	<os-core:giga-space id="gigaSpace" space="space" />
</beans>
```
The datasource when no property provided will try to connect to a local endpoint check the [cassandra data source documentation](cassandraDataSource.md) for more information :
```xml
	<bean id="cassandraDataSource" class="org.openspaces.persistency.cassandra.pool.CassandraDataSourceFactoryBean">
	</bean>
```
The cassandra Resource Pool privides a connection resource pool for the datasource you need to provides a minimum and maximum connection for the pool : 
```xml
	<bean id="cassandraResourcePool" class="org.openspaces.persistency.cassandra.pool.CassandraResourcePoolFactoryBean">
		<property name="cassandraDataSource" ref="cassandraDataSource" ></property>
		<property name="minimumNumberOfConnections" value="${cassandra.ds.minconnections}" ></property>
		<property name="maximumNumberOfConnections" value="${cassandra.ds.maxconnections}" ></property>
	</bean>
```

The cassandraTypeRepository hold the connection pool and load all entityHelper for entity of package listed on entitiesPackages.
You can also define defaultKeyspace at this level to override the one from the source.
```xml
	<bean id="cassandraTypeRepository" class=" org.openspaces.persistency.cassandra.types.CassandraTypeRepositoryFactoryBean">
		<property name="connectionPool" ref="cassandraResourcePool" ></property>
		<property name="entitiesPackages">
		<util:list  list-class="java.util.ArrayList" value-type="java.lang.String">
			<value>org.openspaces.persistency.cassandra.example1.entities</value>
		</util:list>
	    </property>
		<property name="defaultKeyspace" value="inventory"></property>
	</bean>
```

The cassandraSpaceDataSource is the definition of our space data source that must point to the cassandra type repository defined earlier.
You can add optionally here query filter to filter your data at the initial load:
```xml
    <bean id="cassandraSpaceDataSource" class="org.openspaces.persistency.cassandra.datasource.CassandraSpaceDataSourceFactoryBean">
		<property name="cassandraTypeRepository" ref="cassandraTypeRepository"></property>
		<property name="batchLimit" value="${cassandra.ds.batchlimit}" ></property>
	</bean>
```
The cassandraSpaceSyncEndpoint is the mirroring service that enable to write back the change to the cassandra it should point to the cassandra type repository.
```xml
	<bean id="cassandraSpaceSyncEndpoint" class="org.openspaces.persistency.cassandra.CassandraSpaceSynchronizationEndpointFactoryBean">
		<property name="cassandraTypeRepository" ref="cassandraTypeRepository"></property>
	</bean>
```

And finally the definition of your space that use both space-data-source to load the data and space-sync-endpoint to sync back the modifications.
```xml
	<os-core:embedded-space id="space" space-name="dataSourceSpace"
							space-data-source="cassandraSpaceDataSource"
	                        space-sync-endpoint="cassandraSpaceSyncEndpoint"/>
	<os-core:giga-space id="gigaSpace" space="space" />
```
