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

import org.openspaces.persistency.cassandra.types.CassandraTypeRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * A {@link FactoryBean} for creating a singleton instance of
 * {@link CassandraSpaceSynchronizationEndpoint}.
 * 
 * @since 9.1.1
 * @author Dan Kilman
 */
public class CassandraSpaceSynchronizationEndpointFactoryBean implements 
    FactoryBean<CassandraSpaceSynchronizationEndpoint>, InitializingBean {

    private CassandraSpaceSynchronizationEndpoint cassandraSynchronizationEndpointInterceptor ;
    CassandraTypeRepository cassandraTypeRepository;

    public CassandraTypeRepository getCassandraTypeRepository() {
        return cassandraTypeRepository;
    }

    public CassandraSpaceSynchronizationEndpointFactoryBean setCassandraTypeRepository(CassandraTypeRepository cassandraTypeRepository) {
        this.cassandraTypeRepository = cassandraTypeRepository;
        return this;
    }

    @Override
    public void afterPropertiesSet() {
        cassandraSynchronizationEndpointInterceptor = new CassandraSpaceSynchronizationEndpoint(cassandraTypeRepository);
    }
    
    @Override
    public CassandraSpaceSynchronizationEndpoint getObject() {
        return cassandraSynchronizationEndpointInterceptor;
    }

    @Override
    public Class<?> getObjectType() {
        return CassandraSpaceSynchronizationEndpoint.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
