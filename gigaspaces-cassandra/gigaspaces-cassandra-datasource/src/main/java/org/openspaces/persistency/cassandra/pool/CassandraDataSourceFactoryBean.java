package org.openspaces.persistency.cassandra.pool;

import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.metadata.NodeStateListener;
import com.datastax.oss.driver.api.core.metadata.schema.SchemaChangeListener;
import com.datastax.oss.driver.api.core.ssl.SslEngineFactory;
import com.datastax.oss.driver.api.core.tracker.RequestTracker;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.net.ssl.SSLContext;
import java.util.UUID;

public class CassandraDataSourceFactoryBean
        implements FactoryBean<CassandraDataSource>, InitializingBean, DisposableBean  {

    DriverConfigLoader configLoader;
    String   applicationVersion;
    String   applicationName;
    UUID     clientId;
    String   profileName;
    String   localDataCenter;
    String[] contactPoints;
    TypeCodec<?>[] typeCodecs;
    NodeStateListener nodeStateListener;
    SchemaChangeListener schemaChangeListener;
    RequestTracker requestTracker;
    AuthProvider authProvider;
    String username;
    String password;
    String authorizationId;
    SslEngineFactory sslEngineFactory;
    SSLContext sslContext;
    String keyspaceName;

    private CassandraDataSource     cassandraDataSource;

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public CassandraDataSourceFactoryBean setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
        return this;
    }

    public SslEngineFactory getSslEngineFactory() {
        return sslEngineFactory;
    }

    public CassandraDataSourceFactoryBean setSslEngineFactory(SslEngineFactory sslEngineFactory) {
        this.sslEngineFactory = sslEngineFactory;
        return this;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public CassandraDataSourceFactoryBean setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CassandraDataSourceFactoryBean setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CassandraDataSourceFactoryBean setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public CassandraDataSourceFactoryBean setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
        return this;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public CassandraDataSourceFactoryBean setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
        return this;
    }

    public RequestTracker getRequestTracker() {
        return requestTracker;
    }

    public CassandraDataSourceFactoryBean setRequestTracker(RequestTracker requestTracker) {
        this.requestTracker = requestTracker;
        return this;
    }

    public SchemaChangeListener getSchemaChangeListener() {
        return schemaChangeListener;
    }

    public CassandraDataSourceFactoryBean setSchemaChangeListener(SchemaChangeListener schemaChangeListener) {
        this.schemaChangeListener = schemaChangeListener;
        return this;
    }

    public NodeStateListener getNodeStateListener() {
        return nodeStateListener;
    }

    public CassandraDataSourceFactoryBean setNodeStateListener(NodeStateListener nodeStateListener) {
        this.nodeStateListener = nodeStateListener;
        return this;
    }

    public TypeCodec<?>[] getTypeCodecs() {
        return typeCodecs;
    }

    public CassandraDataSourceFactoryBean setTypeCodecs(TypeCodec<?>[] typeCodecs) {
        this.typeCodecs = typeCodecs;
        return this;
    }

    public DriverConfigLoader getConfigLoader() {
        return configLoader;
    }

    public CassandraDataSourceFactoryBean setConfigLoader(DriverConfigLoader configLoader) {
        this.configLoader = configLoader;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public CassandraDataSourceFactoryBean setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

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
                configLoader,
                applicationName,
                applicationVersion,
                clientId,
                profileName,
                localDataCenter,
                contactPoints,
                typeCodecs,
                nodeStateListener,
                schemaChangeListener,
                requestTracker,
                authProvider,
                username,
                password,
                authorizationId,
                sslEngineFactory,
                sslContext,
                keyspaceName
        );
    }
}
