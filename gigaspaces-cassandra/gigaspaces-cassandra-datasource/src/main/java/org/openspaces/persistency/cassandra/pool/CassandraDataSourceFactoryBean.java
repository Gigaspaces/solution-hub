package org.openspaces.persistency.cassandra.pool;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.UUID;

public class CassandraDataSourceFactoryBean
        implements FactoryBean<CassandraDataSource>, InitializingBean, DisposableBean  {

    String   applicationVersion;
    String   applicationName;
    UUID     clientId;
    String   localDataCenter;
    String[] contactPoints;

    private CassandraDataSource     cassandraDataSource;

    public UUID getClientId() {
        return clientId;
    }

    public CassandraDataSourceFactoryBean setClientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public CassandraDataSourceFactoryBean setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getLocalDataCenter() {
        return localDataCenter;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public CassandraDataSourceFactoryBean setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
        return this;
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
                applicationName,
                applicationVersion,
                clientId,
                localDataCenter,
                contactPoints
        );
    }
}
