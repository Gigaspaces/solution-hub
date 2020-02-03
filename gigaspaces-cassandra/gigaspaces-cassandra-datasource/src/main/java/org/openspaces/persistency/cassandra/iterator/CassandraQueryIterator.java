package org.openspaces.persistency.cassandra.iterator;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.internal.mapper.entity.EntityHelperBase;
import com.gigaspaces.datasource.DataIterator;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.openspaces.persistency.cassandra.types.CassandraTypeInfo;

import java.util.Iterator;

public class CassandraQueryIterator  implements DataIterator<Object> {

    private final CassandraTypeInfo typeInfo ;
    private final ConnectionResource connectionResource;
    private final Select             select;
    private final CqlSession         session;
    private final ResultSet          resultSet;
    private final Iterator<Row>      rowIterator;
    private final EntityHelperBase<?> entityHelper;
    private boolean released = false;

    public CassandraQueryIterator(CassandraTypeInfo typeInfo,
                                  ConnectionResource connectionResource,
                                  Select             select){
        this.typeInfo=typeInfo;
        this.connectionResource=connectionResource;
        this.select=select;
        this.session=connectionResource.getSession();
        this.resultSet = session.execute(select.asCql());
        this.rowIterator = resultSet.iterator();
        this.entityHelper = typeInfo.getEntityHelper();

    }

    public void closeOnlyLocalResource() {

    }

    @Override
    public void close() {
        if (!released) {
            closeOnlyLocalResource();
            connectionResource.release();
            released = true;
        }
    }

    @Override
    public boolean hasNext() {
        return this.rowIterator.hasNext();
    }

    @Override
    public Object next() {
        return typeInfo.asEntity(this.rowIterator.next(),entityHelper);
    }
}
