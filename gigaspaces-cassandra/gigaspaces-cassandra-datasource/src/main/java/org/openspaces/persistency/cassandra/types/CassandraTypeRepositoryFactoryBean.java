package org.openspaces.persistency.cassandra.types;

import com.j_spaces.kernel.pool.IResourcePool;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public class CassandraTypeRepositoryFactoryBean implements FactoryBean<CassandraTypeRepository>, InitializingBean, DisposableBean {

    private IResourcePool<ConnectionResource> connectionPool;
    private String                            defaultKeyspace;
    private List<String>                      entitiesPackages;
    private CassandraTypeRepository           cassandraTypeRepository;



    @Override
    public void destroy() throws Exception {
        cassandraTypeRepository.destroy();
    }

    @Override
    public CassandraTypeRepository getObject() throws Exception {
        return cassandraTypeRepository;
    }

    @Override
    public Class<?> getObjectType() {
        return CassandraTypeRepository.class;
    }

    @Override
    public void afterPropertiesSet() {
        this.cassandraTypeRepository = new CassandraTypeRepository(
                connectionPool,
                defaultKeyspace,
                entitiesPackages);
        this.cassandraTypeRepository.init();
    }

    public IResourcePool<ConnectionResource> getConnectionPool() {
        return connectionPool;
    }

    public CassandraTypeRepositoryFactoryBean setConnectionPool(IResourcePool<ConnectionResource> connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public String getDefaultKeyspace() {
        return defaultKeyspace;
    }

    public CassandraTypeRepositoryFactoryBean setDefaultKeyspace(String defaultKeyspace) {
        this.defaultKeyspace = defaultKeyspace;
        return this;
    }

    public List<String> getEntitiesPackages() {
        return entitiesPackages;
    }

    public CassandraTypeRepositoryFactoryBean setEntitiesPackages(List<String> entitiesPackages) {
        this.entitiesPackages = entitiesPackages;
        return this;
    }
}
