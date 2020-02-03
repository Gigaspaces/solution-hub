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
package org.openspaces.persistency.cassandra.pool;

import com.datastax.oss.driver.api.core.CqlSession;
import com.j_spaces.kernel.pool.IResource;
import com.j_spaces.kernel.pool.Resource;
import org.openspaces.persistency.cassandra.error.SpaceCassandraDataSourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link IResource} representing a {@link java.sql.Connection}.
 *
 * @since 9.1.1
 * @author Dan Kilman
 */
public class ConnectionResource extends Resource
        implements IResource {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionResource.class);

    /**
     * We use the data source to create a new underlying connection when
     * some exception occures that requires a need connection to be created.
     * this will will only happen for connections that are part of the pool,
     * because connections that are not part of the pool will be closed when
     * the resource is cleared.
     */
    private final CassandraDataSource cassandraDataSource;

    /*
     * Intentionally left unvolatile. worst case scenario, the user will get a connection exception
     */
    private boolean closed;

    private volatile CqlSession session;

    public ConnectionResource(CassandraDataSource cassandraDataSource) {
        this.cassandraDataSource = cassandraDataSource;
    }

    @Override
    public void clear() {
        if (!isFromPool()) {
            closeSession();
        }
    }

    public CqlSession getSession() {
        if (closed) {
            throw new SpaceCassandraDataSourceException("Resource already closed");
        }
        
        if (session == null) {
            session = cassandraDataSource.createNewSession();
        }
        return session;
    }
    
    /**
     * Must be called by the resource owner
     */
    public void  closeSession() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public void close() {
        closeSession();
        closed = true;
    }
}
