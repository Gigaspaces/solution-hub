package org.openspaces.persistency.cassandra.pool;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.auth.AuthProvider;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.metadata.NodeStateListener;
import com.datastax.oss.driver.api.core.metadata.schema.SchemaChangeListener;
import com.datastax.oss.driver.api.core.ssl.SslEngineFactory;
import com.datastax.oss.driver.api.core.tracker.RequestTracker;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.*;

public class CassandraDataSource {

    private final static Logger logger = LoggerFactory.getLogger(CassandraDataSource.class);
    private final DriverConfigLoader configLoader;
    private final String profileName;
    private final String localDatacenter;
    private final Set<InetSocketAddress> contactPointsLst = new HashSet<>();
    private final String applicationVersion;
    private final String applicationName;
    private final UUID   clientId;
    private final TypeCodec<?>[] typeCodecs;
    private final NodeStateListener nodeStateListener;
    private final SchemaChangeListener schemaChangeListener;
    private final RequestTracker requestTracker;
    private final AuthProvider authProvider;
    private final String username;
    private final String password;
    private final String authorizationId;
    private final SslEngineFactory sslEngineFactory;
    private final SSLContext sslContext;
    private final String keyspaceName;

    public CassandraDataSource(
            DriverConfigLoader configLoader,
            String applicationName,
            String applicationVersion,
            UUID   clientId,
            String profileName,
            String localDatacenter,
            String[] contactPoints,
            TypeCodec<?>[] typeCodecs,
            NodeStateListener nodeStateListener,
            SchemaChangeListener schemaChangeListener,
            RequestTracker requestTracker,
            AuthProvider authProvider,
            String username,
            String password,
            String authorizationId,
            SslEngineFactory sslEngineFactory,
            SSLContext sslContext,
            String keyspaceName){
        this.configLoader=configLoader;
        this.profileName=profileName;
        this.applicationName=applicationName;
        this.typeCodecs=typeCodecs;
        this.clientId=clientId;
        this.applicationVersion=applicationVersion;
        this.localDatacenter=localDatacenter;
        this.nodeStateListener=nodeStateListener;
        this.schemaChangeListener=schemaChangeListener;
        this.requestTracker=requestTracker;
        this.authProvider=authProvider;
        this.username=username;
        this.password=password;
        this.authorizationId=authorizationId;
        this.sslEngineFactory=sslEngineFactory;
        this.sslContext=sslContext;
        this.keyspaceName=keyspaceName;

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
        if(this.configLoader!=null){
            builder.withConfigLoader(configLoader);
        }
        if(this.localDatacenter!=null){
            if(profileName!=null){
                builder.withLocalDatacenter(this.profileName,this.localDatacenter);
            }
            else {
                builder.withLocalDatacenter(this.localDatacenter);
            }
        }
        if(typeCodecs!=null){
            builder.addTypeCodecs(typeCodecs);
        }
        if(this.applicationName!=null){
            builder.withApplicationName(applicationName);
        }
        if(this.applicationVersion!=null){
            builder.withApplicationVersion(applicationVersion);
        }
        //Todo:check if it is relevant
        if(this.clientId!=null){
            builder.withClientId(clientId);
        }
        if(this.nodeStateListener!=null){
            builder.withNodeStateListener(this.nodeStateListener);
        }
        if(this.schemaChangeListener!=null){
            builder.withSchemaChangeListener(this.schemaChangeListener);
        }
        if(this.requestTracker!=null){
            builder.withRequestTracker(this.requestTracker);
        }
        if(this.authProvider!=null){
            builder.withAuthProvider(this.authProvider);
        }
        if(this.username!=null) {
            if(this.authorizationId!=null){
                builder.withAuthCredentials(username,password,authorizationId);
            }
            else{
                builder.withAuthCredentials(username,password);
            }
        }
        if(this.sslEngineFactory!=null){
            builder.withSslEngineFactory(this.sslEngineFactory);
        }
        if(this.sslContext!=null){
            builder.withSslContext(this.sslContext);
        }
        if(this.keyspaceName!=null) {
            builder.withKeyspace(this.keyspaceName);
        }
        return builder.build();
    }

}
