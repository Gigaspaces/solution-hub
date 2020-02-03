package org.openspaces.persistency.cassandra.example1;


import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.j_spaces.core.client.SQLQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.openspaces.persistency.cassandra.example1.dao.ProductDao;
import org.openspaces.persistency.cassandra.example1.entities.Product;
import org.openspaces.persistency.cassandra.example1.inventory.InventoryMapper;
import org.openspaces.persistency.cassandra.example1.inventory.InventoryMapperBuilder;
import org.openspaces.persistency.cassandra.pool.CassandraDataSource;
import org.openspaces.pu.container.ProcessingUnitContainer;
import org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:/cassandra.xml")
public class Test1bWithMultipleGSPartitions {

    private GigaSpace processingSpace;

    @Autowired
    CassandraDataSource cassandraDataSource;

    @Test
    public void simpleUnitTest() {
        SQLQuery<Product> query = new SQLQuery<Product>(Product.class, "");
        long countInit = processingSpace.count(query);

        Product data = new Product();
        data.setProductId(UUID.randomUUID());
        data.setDescription("Test data");
        processingSpace.write(data);
        long countEnd = processingSpace.count(query);
        assertEquals(countInit+1,countEnd);
        try(CqlSession session = cassandraDataSource.createNewSession()) {
            InventoryMapper inventoryMapper = new InventoryMapperBuilder(session).withDefaultKeyspace(CqlIdentifier.fromCql("inventory")).build();
            ProductDao dao = inventoryMapper.productDao(CqlIdentifier.fromCql("inventory"),"product");
            assertEquals(countEnd,dao.count());
        }
    }

    @BeforeEach
    public void init() throws IOException {
        IntegratedProcessingUnitContainerProvider provider = new IntegratedProcessingUnitContainerProvider();
        provider.addConfigLocation("classpath:/gigaspaces-with-cassandra-ds-beans-test.xml");
        ClusterInfo clusterInfo = new ClusterInfo();
        clusterInfo.setSchema("partitioned");
        clusterInfo.setNumberOfInstances(4);
        provider.setClusterInfo(clusterInfo);

        ProcessingUnitContainer c = provider.createContainer();

        processingSpace = new GigaSpaceConfigurer(new SpaceProxyConfigurer("dataSourceSpace")).gigaSpace();
    }

}
