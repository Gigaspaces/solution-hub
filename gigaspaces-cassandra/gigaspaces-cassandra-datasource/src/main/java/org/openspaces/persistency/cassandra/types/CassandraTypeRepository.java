package org.openspaces.persistency.cassandra.types;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.internal.mapper.entity.EntityHelperBase;
import com.j_spaces.kernel.pool.IResourcePool;
import org.openspaces.persistency.cassandra.pool.CassandraResourcePool;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.openspaces.persistency.cassandra.utils.ReflectionsUtils.getClassesInPackage;

public class CassandraTypeRepository {
    private static final Logger logger = LoggerFactory.getLogger(CassandraTypeRepository.class);

    private final ConcurrentMap<String, CassandraTypeInfo> initialMetaLoadEntriesMap = new ConcurrentHashMap<>();

    private final List<String>                             entitiesPackages;
    private final String                                   defaultKeyspace;
    private final IResourcePool<ConnectionResource>        connectionPool;

    public CassandraTypeRepository(
            IResourcePool<ConnectionResource> connectionPool,
            String                            defaultKeyspace,
            List<String>                      entitiesPackages) {
        this.entitiesPackages = entitiesPackages;
        this.defaultKeyspace=defaultKeyspace;
        this.connectionPool=connectionPool;
    }

    public Map<String, CassandraTypeInfo> getInitialMetaLoadEntriesMap() {
        return Collections.unmodifiableMap(initialMetaLoadEntriesMap);
    }

    private Stream<Class<? extends EntityHelperBase>> getEntitiesHelperClassOnEntitiesPackage(){
        return this.entitiesPackages.stream()
                .flatMap(p->getClassesInPackage(p).stream())
                .peek(c->logger.info("class {} is EntityHelperBase? {}",c,EntityHelperBase.class.isAssignableFrom(c)))
                .filter(c->EntityHelperBase.class.isAssignableFrom(c))
                .peek(c->logger.info("class {} retained",c))
                .map(c->(Class<? extends EntityHelperBase>)c);
    }

    @PostConstruct
    protected void init(){
        logger.info("initialMetadataLoad : searchForEntityHelperBaseOnPackages ");
        List<Class<? extends EntityHelperBase>> entityHelpersClasses = getEntitiesHelperClassOnEntitiesPackage().collect(Collectors.toList());
        logger.info("initialMetadataLoad : found {}",  entityHelpersClasses);

        ConnectionResource resource = connectionPool.getResource();
        try {
            CqlSession session = resource.getSession();

            Set<Class<?>> types = entityHelpersClasses.stream()
                    .peek(c->logger.info("getting type for mapper builder {} ",c))
                    .map(c->{
                        CassandraTypeInfo cassandraTypeInfo = new CassandraTypeInfo(c,defaultKeyspace,session);
                        initialMetaLoadEntriesMap.put(cassandraTypeInfo.getType().getName(),cassandraTypeInfo);
                        //                 mapper.
                        return c;
                    }).collect(Collectors.toSet());
        }
        finally {
            resource.release();
        }
    }

    public IResourcePool<ConnectionResource> getConnectionPool() {
        return connectionPool;
    }

    public void destroy() {
        if(connectionPool instanceof CassandraResourcePool) {
            ((CassandraResourcePool)connectionPool).destroy();
        }
    }
}
