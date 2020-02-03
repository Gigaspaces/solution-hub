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

import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoAware;
import org.openspaces.persistency.cassandra.types.CassandraTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * A {@link FactoryBean} for creating a singleton instance of
 * {@link CassandraSpaceDataSource}.
 * 
 * @since 9.1.1
 * @author Dan Kilman
 */
public class CassandraSpaceDataSourceFactoryBean implements 
    FactoryBean<CassandraSpaceDataSource>, InitializingBean, DisposableBean, ClusterInfoAware {
    private static Logger logger = LoggerFactory.getLogger(CassandraSpaceDataSourceFactoryBean.class);

    private CassandraTypeRepository cassandraTypeRepository = null;
    private int                     batchLimit                 = 10000;
    private String[]                initialLoadQueryScanningBasePackages;
    private boolean                 augmentInitialLoadEntries  = true;
    private ClusterInfo             clusterInfo                = null;

    private CassandraSpaceDataSource cassandraSpaceDataSource;

    public CassandraTypeRepository getCassandraTypeRepository() {
        return cassandraTypeRepository;
    }

    public CassandraSpaceDataSourceFactoryBean setCassandraTypeRepository(CassandraTypeRepository cassandraTypeRepository) {
        this.cassandraTypeRepository = cassandraTypeRepository;
        return this;
    }

    public int getBatchLimit() {
        return batchLimit;
    }

    public String[] getInitialLoadQueryScanningBasePackages() {
        return initialLoadQueryScanningBasePackages;
    }

    public boolean isAugmentInitialLoadEntries() {
        return augmentInitialLoadEntries;
    }

    public ClusterInfo getClusterInfo() {
        return clusterInfo;
    }

    public CassandraSpaceDataSource getCassandraSpaceDataSource() {
        return cassandraSpaceDataSource;
    }

    public CassandraSpaceDataSourceFactoryBean setCassandraSpaceDataSource(CassandraSpaceDataSource cassandraSpaceDataSource) {
        this.cassandraSpaceDataSource = cassandraSpaceDataSource;
        return this;
    }

    /**
     * Optional.
     * @param batchLimit Maximum number of rows that will be transferred in batches.
     * (default: 10000).
     * e.g. If batchLimit is set to 10000 and a certain query result set size is 22000,
     * then the query will be translated to 3 queries each with the CQL LIMIT argument set
     * to 10000.
     * @return {@code this} instance.
     */
    public CassandraSpaceDataSourceFactoryBean setBatchLimit(int batchLimit) {
        this.batchLimit = batchLimit;
        return this;
    }

    /**
     * optional.
     * @param initialLoadQueryScanningBasePackages array of base packages to scan for custom initial load query methods
     *                                             marked with the {@link com.gigaspaces.annotation.pojo.SpaceInitialLoadQuery}
     *                                             annotation (default: null, scans nothing).
     * @return {@code this} instance.
     */
    public CassandraSpaceDataSourceFactoryBean setInitialLoadQueryScanningBasePackages(String... initialLoadQueryScanningBasePackages) {
        this.initialLoadQueryScanningBasePackages=initialLoadQueryScanningBasePackages;
        return this;
    }

    /**
     * Injects the {@link ClusterInfo} to be used with the SpaceDataSource
     */
	@Override
	public void setClusterInfo(ClusterInfo clusterInfo) {
        this.clusterInfo = clusterInfo;
	}


    /**
     * optional.
     * @param augmentInitialLoadEntries feature switch for initial load entry augmentation with partition-specific query (default: true).
     * @return {@code this} instance.
     */
    public CassandraSpaceDataSourceFactoryBean setAugmentInitialLoadEntries(boolean augmentInitialLoadEntries) {
        this.augmentInitialLoadEntries = augmentInitialLoadEntries;
        return this;
    }

    @Override
    public void afterPropertiesSet() {
        this.cassandraSpaceDataSource= new CassandraSpaceDataSource(
                cassandraTypeRepository,
                batchLimit,
                initialLoadQueryScanningBasePackages,
                augmentInitialLoadEntries,
                clusterInfo);
        logger.info("Created {}",cassandraSpaceDataSource);
    }

    @Override
    public CassandraSpaceDataSource getObject() throws Exception {
        return cassandraSpaceDataSource;
    }

    @Override
    public Class<?> getObjectType() {
        return CassandraSpaceDataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        cassandraSpaceDataSource.close();
    }
}
