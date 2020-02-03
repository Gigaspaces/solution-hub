package org.openspaces.persistency.cassandra.example1;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.type.DataTypes;
import org.openspaces.persistency.cassandra.example1.dao.ProductDao;
import org.openspaces.persistency.cassandra.example1.entities.Product;
import org.openspaces.persistency.cassandra.example1.inventory.InventoryMapper;
import org.openspaces.persistency.cassandra.example1.inventory.InventoryMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

public class Example1aMain {
    private static Logger logger = LoggerFactory.getLogger(Example1aMain.class);
    public static void main(String[] args){
        prepareSchemaForNameSpace("inventory");
        insertDummyDataToProductTable("inventory");
    }
    public static void insertDummyDataToProductTable(String nameSpace){
        try(CqlSession session=CqlSession.builder().build()) {
            InventoryMapper inventoryMapper = new InventoryMapperBuilder(session).build();
            ProductDao productDao = inventoryMapper.productDao(CqlIdentifier.fromCql(nameSpace));
            UUID lastId=null;
            logger.info("insert data :");
            for (int i = 0; i < 1000; i++) {
                Product p = new Product();
                lastId=UUID.randomUUID();
                p.setProductId(lastId);
                p.setDescription("genDummyProd"+i);
                productDao.save(p);
                logger.info("uuid {} added",lastId);
            }
            if(productDao.findById(lastId)==null){
                System.err.println(lastId+" not found");
            }
        }
    }

    public static void prepareSchemaForNameSpace(String nameSpace) {
        try (CqlSession session = CqlSession.builder().build()) {
            createNameSpace(session,nameSpace);
            createProductTableInNameSpace(session,nameSpace);
        }
    }

    public static void createProductTableInNameSpace(CqlSession session, String nameSpace) {
        logger.info("make sure our product table exist in namespace {}", nameSpace);
        ResultSet rs= session.execute(createTable(nameSpace, "PRODUCT")
                .ifNotExists()
                .withPartitionKey("PRODUCT_ID", DataTypes.UUID)
                .withColumn("DESCRIPTION", DataTypes.TEXT)

                .build());
        logger.info("was applied={}",rs.wasApplied());
    }

    public static void createNameSpace(CqlSession session,String nameSpace) {
        logger.info("make sure our {} keyspace exist", nameSpace);
        Map<String, Object> repliOption = new HashMap<>();
        repliOption.put("class", "SimpleStrategy");
        repliOption.put("replication_factor", 1);
        ResultSet rs= session.execute(createKeyspace(nameSpace)
                .ifNotExists()
                .withReplicationOptions(repliOption)
                .build());
        logger.info("was applied={}",rs.wasApplied());
    }
}
