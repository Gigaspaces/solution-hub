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
package org.openspaces.persistency.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatementBuilder;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import com.datastax.oss.driver.internal.mapper.entity.EntityHelperBase;
import com.gigaspaces.sync.*;
import com.j_spaces.kernel.pool.IResourcePool;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.openspaces.persistency.cassandra.types.CassandraTypeInfo;
import org.openspaces.persistency.cassandra.types.CassandraTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * A Cassandra implementation of {@link SpaceSynchronizationEndpoint}.
 * 
 * @since 9.1.1
 * @author Dan Kilman
 */
public class CassandraSpaceSynchronizationEndpoint
        extends SpaceSynchronizationEndpoint {

    private static Logger logger = LoggerFactory.getLogger(CassandraSpaceSynchronizationEndpoint.class);

    private final IResourcePool<ConnectionResource> connectionPool;
    private final CassandraTypeRepository cassandraTypeRepository;

    public CassandraSpaceSynchronizationEndpoint(CassandraTypeRepository cassandraTypeRepository) {
        if(cassandraTypeRepository==null){
            throw new IllegalArgumentException("cassandraTypeRepository cannot be null");
        }
        this.connectionPool = cassandraTypeRepository.getConnectionPool();
        this.cassandraTypeRepository = cassandraTypeRepository;
    }


    @Override
    public void onTransactionSynchronization(TransactionData transactionData) {
        doSynchronization(transactionData.getTransactionParticipantDataItems());
    }

    @Override
    public void onOperationsBatchSynchronization(OperationsBatchData batchData) {
        doSynchronization(batchData.getBatchDataItems());
    }

    private void doSynchronization(DataSyncOperation[] dataSyncOperations) {
        logger.info("Starting batch operation");

        ConnectionResource connectionResource = connectionPool.getResource();
        try {
            CqlSession session = connectionResource.getSession();
            for (DataSyncOperation dataSyncOperation : dataSyncOperations) {
                Object spaceObj = dataSyncOperation.getDataAsObject();
                CassandraTypeInfo cassandraTypeInfo = cassandraTypeRepository.getInitialMetaLoadEntriesMap().get(spaceObj.getClass().getName());
                logger.info("spaceObj={} of type={} found cassandraTypeInfo={}", spaceObj, spaceObj.getClass().getName(), cassandraTypeInfo);
                EntityHelperBase<?> entityHelperBase = cassandraTypeInfo.getEntityHelper();
                switch(dataSyncOperation.getDataSyncOperationType()) {
                    case WRITE:
                        cassandraTypeInfo.insert(session, spaceObj);

                        //execute(boundStatement);
                }
/*           String keyName = metadata.getKeyName();
            Object keyValue = spaceDoc.getProperty(keyName);

            if (keyValue == null) {
                throw new SpaceCassandraSynchronizationException("Data sync operation missing id property value", null);
            }

            ColumnFamilyRow columnFamilyRow;
            switch(dataSyncOperation.getDataSyncOperationType()) {
                case WRITE:
                    columnFamilyRow = mapper.toColumnFamilyRow(metadata,
                            spaceDoc,
                            ColumnFamilyRowType.Write,
                            true / * useDynamicPropertySerializerForDynamicColumns* /);
                    break;
                case UPDATE:
                    columnFamilyRow = mapper.toColumnFamilyRow(metadata,
                            spaceDoc,
                            ColumnFamilyRowType.Update,
                            true /* useDynamicPropertySerializerForDynamicColumns* /);
                    break;
                case PARTIAL_UPDATE:
                    columnFamilyRow = mapper.toColumnFamilyRow(metadata,
                            spaceDoc,
                            ColumnFamilyRowType.PartialUpdate,
                            true /* useDynamicPropertySerializerForDynamicColumns* /);
                    break;
                case REMOVE:
                    columnFamilyRow = new ColumnFamilyRow(metadata, keyValue, ColumnFamilyRowType.Remove);
                    break;
                default:
                {
                    throw new IllegalStateException("Unsupported data sync operation type: " +
                            dataSyncOperation.getDataSyncOperationType());
                }
            }

            if (logger.isTraceEnabled()) {
                logger.trace("Adding row: " + columnFamilyRow + " to current batch");
            }

            List<ColumnFamilyRow> rows = cfToRows.get(metadata.getColumnFamilyName());
            if (rows == null) {
                rows = new LinkedList<ColumnFamilyRow>();
                cfToRows.put(metadata.getColumnFamilyName(), rows);
            }
            rows.add(columnFamilyRow);

          */
            }
        }
        finally {
            connectionResource.release();
        }

        logger.trace("Performing batch operation");

    }


    @Override
    public void onIntroduceType(IntroduceTypeData introduceTypeData) {
        
    }
    
    @Override
    public void onAddIndex(AddIndexData addIndexData) {
    }
    
}
