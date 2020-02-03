package org.openspaces.persistency.cassandra.example1.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.openspaces.persistency.cassandra.example1.entities.Product;

import java.util.UUID;

@Dao
public interface ProductDao {

    @Select
    Product findById(UUID productId);

    @Query("SELECT count(*) from ${keyspaceId}.${tableId}")
    long count();

    @Insert
    void save(Product product);

    @Delete
    void delete(Product product);
}