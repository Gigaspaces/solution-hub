package org.openspaces.persistency.cassandra.pool;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class CassandraResourcePoolFactoryBean
        implements FactoryBean<CassandraResourcePool>, InitializingBean, DisposableBean  {
    private CassandraDataSource     cassandraDataSource;
    private int                     minimumNumberOfConnections = 5;
    private int                     maximumNumberOfConnections = 30;

    private CassandraResourcePool cassandraResourcePool;
    /**
     * @param cassandraDataSource An instance of {@link CassandraDataSource} configured
     * to use CQL version 4.
     * @return {@code this} instance.
     */
    public CassandraResourcePoolFactoryBean setCassandraDataSource(CassandraDataSource cassandraDataSource){
        this.cassandraDataSource = cassandraDataSource;
        return this;
    }

    /**
     * Optional.
     * @param minimumNumberOfConnections Minimum number of cassandra CQL connections to maintain in the
     * connection pool. (default: 5)
     * @return {@code this} instance.
     */
    public CassandraResourcePoolFactoryBean setMinimumNumberOfConnections(int minimumNumberOfConnections) {
        this.minimumNumberOfConnections=minimumNumberOfConnections;
        return this;
    }

    /**
     * Optional.
     * @param maximumNumberOfConnections Maximum number of cassandra-jdbc connections to maintain in the
     * connection pool. (default: 30)
     * @return {@code this} instance.
     */

    public CassandraResourcePoolFactoryBean setMaximumNumberOfConnections(int maximumNumberOfConnections) {
        this.maximumNumberOfConnections = maximumNumberOfConnections;
        return this;
    }

    @Override
    public void destroy()  {
        cassandraResourcePool.destroy();
    }

    @Override
    public CassandraResourcePool getObject() {
        return cassandraResourcePool;
    }

    @Override
    public Class<?> getObjectType() {
        return CassandraResourcePool.class;
    }

    @Override
    public void afterPropertiesSet() {
        this.cassandraResourcePool= new CassandraResourcePool(
                cassandraDataSource,
                minimumNumberOfConnections,
                maximumNumberOfConnections);
        this.cassandraResourcePool.initVersion();
    }
}
