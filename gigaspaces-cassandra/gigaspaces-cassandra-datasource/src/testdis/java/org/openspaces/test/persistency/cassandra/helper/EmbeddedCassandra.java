/**
 * 
 */
package org.openspaces.test.persistency.cassandra.helper;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.gigaspaces.logger.GSLogConfigLoader;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.openspaces.test.persistency.cassandra.helper.config.CassandraTestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Remote;


public class EmbeddedCassandra implements IEmbeddedCassandra, Remote
{
    public static final String            RPC_PORT_PROP        = "cassandra.rpc_port";
    public static final int               DEFAULT_RPC_PORT     = 9160;

    private static final String           CQL_VERSION          = "3.0.0";
    private static final String           USERNAME             = "default";
    private static final String           PASSWORD             = "password";
    private static final String           SYSTEM_KEYSPACE_NAME = "system";
    private static final String           LOCALHOST            = "localhost";

    private final Logger _logger              = LoggerFactory.getLogger(getClass().getName());

    private final int                     _rpcPort;


    public EmbeddedCassandra(){
        GSLogConfigLoader.getLoader();
        _rpcPort = Integer.getInteger(RPC_PORT_PROP, DEFAULT_RPC_PORT);
        String rootDir = System.getProperty("tst.cassandra.root.dir","target/cassandra");
        String yaml = System.getProperty("cassandra.config").replaceFirst("file:/","");
        _logger.info("Starting Embedded Cassandra with keyspace root dir {} and yaml={}",rootDir,yaml);
        cleanup();
        System.setProperty("log4j.configuration","/log4j.xml");
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra(new File(yaml),rootDir,50000);
        } catch (IOException e) {
            _logger.error("Failed to start Embedded Cassandra",e);
            throw new UncheckedIOException(e);
        }
        _logger.info("Started Embedded Cassandra");
    }

    @Override
    public void createKeySpace(String keySpace){
        try{
            createKeySpaceImpl(keySpace);
        }
        catch (Exception e) {
            _logger.error("Could not create keyspace {} for embedded Cassandra",keySpace , e);
        }
    }
    
    @Override
    public void dropKeySpace(String keySpace) {
        try{
            dropKeySpaceImpl(keySpace);
        }
        catch (Exception e){
            _logger.error( "Could not drop keyspace {} for embedded Cassandra", keySpace, e);
        }
    }
    
    @Override
    public void destroy() {
        EmbeddedCassandraServerHelper.stopEmbeddedCassandra();
    }

    private void cleanup(){
        String rootDir = System.getProperty("tst.cassandra.root.dir","target/cassandra");
        try{
            _logger.info("Cleanup root dir {}",rootDir);
            CassandraTestUtils.deleteFileOrDirectory(new File(rootDir));
            Files.createDirectories(Paths.get(rootDir));
        }
        catch (IOException e){
            _logger.error( "Failed deleting cassandra directory {}",rootDir, e);
        }
    }

    private void createKeySpaceImpl(String keySpace) {
        executeUpdate("CREATE KEYSPACE " + keySpace + " " + 
                      "WITH strategy_class = 'SimpleStrategy' " + 
                      "AND strategy_options:replication_factor = 1");
    }

    private void dropKeySpaceImpl(String keySpace) {
        executeUpdate("DROP KEYSPACE " + keySpace);
    }
    
    private void executeUpdate(String statement)
    {
        /*
        CassandraDataSource ds = new CassandraDataSource(LOCALHOST,
                                                         _rpcPort,
                                                         SYSTEM_KEYSPACE_NAME,
                                                         USERNAME,
                                                         PASSWORD,
                                                         CQL_VERSION,
                                              "ANY");
        */
        try (CqlSession session = CqlSession.builder().build()) {
            ResultSet rs = session.execute(statement);
        }
    }
    
}
