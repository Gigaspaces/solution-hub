package org.openspaces.persistency.cassandra.iterator;

import com.gigaspaces.datasource.DataIterator;
import org.openspaces.persistency.cassandra.pool.ConnectionResource;
import org.openspaces.persistency.cassandra.types.CassandraTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class CassandraInitialDataLoadIterator implements DataIterator<Object> {

    private final Logger logger = LoggerFactory.getLogger(CassandraInitialDataLoadIterator.class);
    private final ConnectionResource connectionResource;
    private final Map<String, CassandraTypeInfo> initialMetaLoadEntriesMap;
    private boolean released = false;
    private final Iterator<Map.Entry<String, CassandraTypeInfo>> iteratorOnTypeToLoad;
    private CassandraQueryIterator cassandraQueryIterator = null;

    public CassandraInitialDataLoadIterator(
            Map<String, CassandraTypeInfo> initialMetaLoadEntriesMap,
            ConnectionResource connectionResource
            ){
        this.connectionResource = connectionResource;
        this.initialMetaLoadEntriesMap=initialMetaLoadEntriesMap;
        this.iteratorOnTypeToLoad = initialMetaLoadEntriesMap.entrySet().iterator();
    }

    @Override
    public void close() {
        if (cassandraQueryIterator != null) {
            cassandraQueryIterator.closeOnlyLocalResource();
        }
        connectionResource.release();
        released = true;
    }

    @Override
    public boolean hasNext() {
        if(released) {
            return false;
        }
        while(cassandraQueryIterator==null||!cassandraQueryIterator.hasNext()){
            if(cassandraQueryIterator!=null) {
                cassandraQueryIterator.closeOnlyLocalResource();
                cassandraQueryIterator=null;
            }
            if(!iteratorOnTypeToLoad.hasNext()){
                return false;
            }
            Map.Entry<String, CassandraTypeInfo> nextType = iteratorOnTypeToLoad.next();
            logger.info("changing selector to next type {}",nextType.getKey());
            CassandraTypeInfo cassandraTypeInfo = nextType.getValue();
            cassandraQueryIterator = new CassandraQueryIterator(
                    cassandraTypeInfo,
                    connectionResource,
                    cassandraTypeInfo.getAllSelectQuery(connectionResource.getSession()));
        }
        return cassandraQueryIterator.hasNext();
    }

    @Override
    public Object next() {
        return cassandraQueryIterator.next();
    }
}
