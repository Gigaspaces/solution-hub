/*******************************************************************************
 * Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.openspaces.persistency.cassandra.datasource;

import com.gigaspaces.datasource.*;
import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.j_spaces.kernel.pool.IResourcePool;
import com.j_spaces.kernel.pool.IResourceProcedure;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.persistency.ClusterInfoAwareSpaceDataSource;
import org.openspaces.persistency.cassandra.error.SpaceCassandraDataSourceException;
import org.openspaces.persistency.cassandra.iterator.CassandraInitialDataLoadIterator;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.openspaces.persistency.cassandra.types.CassandraTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 
 * A Cassandra implementation of {@link com.gigaspaces.datasource.SpaceDataSource}.
 * 
 * @since 9.1.1
 * @author Dan Kilman
 */
public class CassandraSpaceDataSource extends ClusterInfoAwareSpaceDataSource {
    private static final Logger                   logger = LoggerFactory.getLogger(CassandraSpaceDataSource.class);

    private final IResourcePool<ConnectionResource>  connectionPool;
    private final int                                batchLimit;
    private final CassandraTypeRepository            cassandraTypeRepository;
    //private final DefaultConsistencyLevel           readConsistencyLevel;
    //private final DefaultConsistencyLevel           writeConsistencyLevel;

    private final Object                            lock         = new Object();
    private boolean                                 closed       = false;

    public CassandraSpaceDataSource(
            CassandraTypeRepository          cassandraTypeRepository,
            int                              batchLimit,
            String[]                         initialLoadQueryScanningBasePackages,
            boolean                          augmentInitialLoadEntries,
			ClusterInfo                      clusterInfo	) {
        this.connectionPool = cassandraTypeRepository.getConnectionPool();
        this.cassandraTypeRepository = cassandraTypeRepository;
        if (batchLimit <= 0) {
            throw new IllegalArgumentException("batchSize must be a positive number");
        }

        this.batchLimit = batchLimit;


        this.initialLoadQueryScanningBasePackages = initialLoadQueryScanningBasePackages;
        this.augmentInitialLoadEntries = augmentInitialLoadEntries;
		this.clusterInfo = clusterInfo;
    }

    /**
     * Closes open jdbc connections and the hector client connection pool.
     */
    public void close() {
        synchronized (lock) {
            if (closed) {
                return;
            }
            connectionPool.forAllResources(new IResourceProcedure<ConnectionResource>() {
                public void invoke(ConnectionResource resource) {
                    resource.close();
                }
            });
            closed = true;
        }
    }
    
    @Override
    public DataIterator<Object> getDataIterator(DataSourceQuery query) {
        String typeName = query.getTypeDescriptor().getTypeName();

        CQLQueryContext queryContext = null;
        if (query.supportsTemplateAsDocument()) {
            SpaceDocument templateDocument = query.getTemplateAsDocument();
            Map<String, Object> properties = templateDocument.getProperties();
            queryContext = new CQLQueryContext(properties, null, null);
        } else if (query.supportsAsSQLQuery()) {
            DataSourceSQLQuery sqlQuery = query.getAsSQLQuery();
            Object[] params = sqlQuery.getQueryParameters();
            queryContext = new CQLQueryContext(null, sqlQuery.getQuery(), params);
        } else {
            throw new SpaceCassandraDataSourceException("Unsupported data source query", null);
        }
        return null;
    }

    /*
    private Object getKeyValue(CQLQueryContext queryContext, ColumnFamilyMetadata metadata) {
        if (!queryContext.hasProperties()) {
            return null;
        }
        
        return queryContext.getProperties().get(metadata.getKeyName());
    }*/

    /*
    private boolean templateHasPropertyOtherThanKey(
            CQLQueryContext queryContext,
            ColumnFamilyMetadata metadata) {
        // This test is not really needed as it is only called after getKeyValue returned a
        // value differet than null, and this same test is performed there
        if (!queryContext.hasProperties()) {
            return true;
        }
        
        for (Entry<String, Object> entry : queryContext.getProperties().entrySet()) {
            if (!metadata.getKeyName().equals(entry.getKey()) &&
                entry.getValue() != null) {
                return true;
            }
        }
        
        return false;
    }*/
    
    @Override
    public Object getById(DataSourceIdQuery idQuery) {
        String typeName = idQuery.getTypeDescriptor().getTypeName();
        Object id = idQuery.getId();
        return getByIdImpl(typeName, id);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public DataIterator<Object> getDataIteratorByIds(DataSourceIdsQuery idsQuery)
    {
        String typeName = idsQuery.getTypeDescriptor().getTypeName();
        Object[] ids = idsQuery.getIds();
//        Map<Object, SpaceDocument> documentsByKeys = hectorClient.readDocumentsByKeys(mapper, typeName, ids);
        return null;//new DataIteratorAdapter<Object>((Iterator)documentsByKeys.values().iterator());
    }
    
    private Object getByIdImpl(String typeName, Object id) {
        return null;//hectorClient.readDocmentByKey(mapper, typeName, id);
    }


    @Override
    public DataIterator<SpaceTypeDescriptor> initialMetadataLoad() {
        
        return super.initialMetadataLoad();
    }

    @Override
    public DataIterator<Object> initialDataLoad() {
        return new CassandraInitialDataLoadIterator(cassandraTypeRepository.getInitialMetaLoadEntriesMap(),connectionPool.getResource());
    }
    
    /**
     * Returns <code>false</code>, inheritance is not supported.
     * @return <code>false</code>.
     */
    @Override
    public boolean supportsInheritance() {
        return false;
    }

}
