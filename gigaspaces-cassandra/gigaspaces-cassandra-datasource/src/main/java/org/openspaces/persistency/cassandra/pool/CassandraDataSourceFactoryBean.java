package org.openspaces.persistency.cassandra.pool;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class CassandraDataSourceFactoryBean
        implements FactoryBean<CassandraDataSource>, InitializingBean, DisposableBean  {
    String   localDataCenter;
    String[] contactPoints;

    private CassandraDataSource     cassandraDataSource;

    public String getLocalDataCenter() {
        return localDataCenter;
    }

    public CassandraDataSourceFactoryBean setLocalDataCenter(String localDataCenter) {
        this.localDataCenter = localDataCenter;
        return this;
    }

    public String[] getContactPoints() {
        return contactPoints;
    }

    public CassandraDataSourceFactoryBean setContactPoints(String[] contactPoints) {
        this.contactPoints = contactPoints;
        return this;
    }

    public CassandraDataSource getCassandraDataSource() {
        return cassandraDataSource;
    }


    @Override
    public void destroy() {
    }

    @Override
    public CassandraDataSource getObject() {
        return cassandraDataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return CassandraDataSource.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cassandraDataSource= new CassandraDataSource(
                localDataCenter,
                contactPoints
        );
    }
}
