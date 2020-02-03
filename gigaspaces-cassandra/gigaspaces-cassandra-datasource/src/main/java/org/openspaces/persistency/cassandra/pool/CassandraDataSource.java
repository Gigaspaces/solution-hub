package org.openspaces.persistency.cassandra.pool;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;

public class CassandraDataSource {

    private final static Logger logger = LoggerFactory.getLogger(CassandraDataSource.class);
    private final String localDatacenter;
    private final Set<InetSocketAddress> contactPointsLst = new HashSet<>();

    public CassandraDataSource(){
        this(null,null);
    }

    public CassandraDataSource(String localDatacenter,String... contactPoints){
        this.localDatacenter=localDatacenter;
        if(contactPoints!=null) {
            for (String contactPoint : contactPoints) {
                contactPointsLst.addAll(fromString(contactPoint, true));
            }
        }
    }


    private static Collection<InetSocketAddress> fromString(String contactPoint,boolean resolve){
        int idxPort=contactPoint.indexOf(":");
        boolean isTherePort = idxPort>=0;
        if(!isTherePort){
            logger.warn("Ignoring invalid contact point {} (expecting host:port)", contactPoint);
            return Collections.emptySet();
        }
        String host = contactPoint.substring(0, idxPort);
        String portSpec = contactPoint.substring(idxPort + 1);
        int port;
        try {
            port = Integer.parseInt(portSpec);
        } catch (NumberFormatException e) {
            logger.warn("Ignoring invalid contact point {} (expecting a number, got {})", contactPoint, portSpec);
            return Collections.emptySet();
        }
        if (!resolve) {
            return ImmutableSet.of(InetSocketAddress.createUnresolved(host, port));
        }
        try {
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            if (inetAddresses.length > 1) {
                logger.info(
                        "Contact point {} resolves to multiple addresses, will use them all ({})",
                        contactPoint,
                        Arrays.deepToString(inetAddresses));
            }
            Set<InetSocketAddress> result = new HashSet<>();
            for (InetAddress inetAddress : inetAddresses) {
                result.add(new InetSocketAddress(inetAddress, port));
            }
            return result;
        } catch (UnknownHostException e) {
            logger.warn("Ignoring invalid contact point {} (unknown host {})", contactPoint, host);
            return Collections.emptySet();
        }
    }


    public CqlSession createNewSession(){
        CqlSessionBuilder builder=new CqlSessionBuilder().addContactPoints(contactPointsLst);
        if(this.localDatacenter!=null){
            builder.withLocalDatacenter(this.localDatacenter);
        }
        return builder.build();
    }

}
