package org.openspaces.persistency.cassandra.pool;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.j_spaces.kernel.pool.ResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class CassandraResourcePool extends ResourcePool<ConnectionResource> {
    public static final String    CQL_VERSION  = "4";
    private static final Logger logger = LoggerFactory.getLogger(CassandraResourcePool.class);

    private String    releaseVersion=null;
    private String    nativeProtocolVersion=null;
    public CassandraResourcePool(CassandraDataSource cassandraDataSource,
                                 int minimumNumberOfConnections, int maximumNumberOfConnections) {
        super(new ConnectionResourceFactory(cassandraDataSource),
                minimumNumberOfConnections,
                maximumNumberOfConnections);
        if (cassandraDataSource == null) {
            throw new IllegalArgumentException("dataSource must be set");
        }

        if (minimumNumberOfConnections <= 0) {
            throw new IllegalArgumentException("minimumNumberOfConnections must be positive number");
        }

        if (maximumNumberOfConnections < minimumNumberOfConnections) {
            throw new IllegalArgumentException("maximumNumberOfConnections must not be smaller than" +
                    "minimumNumberOfConnections");
        }
    }
    @PostConstruct
    protected void initVersion(){
        if(releaseVersion==null||nativeProtocolVersion==null) {
            ConnectionResource resource = getResource();
            try {
                CqlSession session = resource.getSession();
                ResultSet rs = session.execute("select release_version,native_protocol_version from system.local");
                Row row = rs.one();
                releaseVersion = row.getString("release_version");
                nativeProtocolVersion = row.getString("native_protocol_version");
            }
            finally {
                resource.release();
            }
            logger.info("retrieve server release version {}",releaseVersion);
            logger.info("Cassandra data source is connected to a server CQLversion={}; release version={}",nativeProtocolVersion,releaseVersion);
            if(!CQL_VERSION.equals(nativeProtocolVersion)){
                logger.warn("Best with CQL version 4, version used is {}",nativeProtocolVersion);
            }

        }
    }

    @PreDestroy
    public void destroy(){

    }


}
