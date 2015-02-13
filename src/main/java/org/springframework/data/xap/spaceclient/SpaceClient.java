package org.springframework.data.xap.spaceclient;

import com.gigaspaces.client.*;
import com.gigaspaces.internal.client.QueryResultTypeInternal;
import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.gigaspaces.internal.utils.ObjectUtils;
import com.gigaspaces.query.ISpaceQuery;
import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.gigaspaces.query.QueryResultType;
import com.gigaspaces.query.aggregators.AggregationResult;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.j_spaces.core.LeaseContext;
import net.jini.core.transaction.Transaction;

/**
 * This is a replacement for GigaSpace/DefaultGigaSpace.
 * We cannot use GigaSpace interface since it in openspaces which depends on Spring 3.2 which is not compatible
 * with the latest Spring Data Commons (it depends on Spring 4)
 *
 * @author Anna_Babich
 */
public class SpaceClient {

    private ISpaceProxy space;

    private long defaultReadTimeout = 0;

    private long defaultTakeTimeout = 0;

    private long defaultWriteLease = Long.MAX_VALUE;

    private WriteModifiers defaultWriteModifiers = WriteModifiers.NONE;

    private ReadModifiers defaultReadModifiers = ReadModifiers.NONE;

    private TakeModifiers defaultTakeModifiers = TakeModifiers.NONE;


    public <T> LeaseContext<T> write(T entry) {
        return write(entry, defaultWriteLease);
    }

    @SuppressWarnings("unchecked")
    public <T> LeaseContext<T> write(T entry, long lease) {
        try {
            return space.write(entry, getCurrentTransaction(), lease, 0, defaultWriteModifiers.getCode());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> LeaseContext<T>[] writeMultiple(T[] entries) {
        return writeMultiple(entries, defaultWriteLease);
    }

    @SuppressWarnings("unchecked")
    public <T> LeaseContext<T>[] writeMultiple(T[] entries, long lease) {
        try {
            return space.writeMultiple(entries, getCurrentTransaction(), lease, null, 0, defaultWriteModifiers.getCode());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T readById(Class<T> clazz, Object id) {
        return readById(clazz, id, null, defaultReadTimeout, defaultReadModifiers);
    }

    @SuppressWarnings("unchecked")
    public <T> T readById(Class<T> clazz, Object id, Object routing, long timeout, ReadModifiers modifiers) {
        try {
            return (T) space.readById(
                    ObjectUtils.assertArgumentNotNull(clazz, "class").getName(), id, routing, getCurrentTransaction(), timeout, modifiers.getCode(), false, QueryResultTypeInternal.NOT_SET, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T[] readMultiple(ISpaceQuery<T> template) {
        return readMultiple(template, Integer.MAX_VALUE);
    }

    public <T> T[] readMultiple(ISpaceQuery<T> template, int maxEntries) {
        return readMultiple(template, maxEntries, defaultReadModifiers);
    }


    @SuppressWarnings("unchecked")
    public <T> T[] readMultiple(ISpaceQuery<T> template, int maxEntries, ReadModifiers modifiers) {
        try {
            return (T[]) space.readMultiple(template, getCurrentTransaction(), maxEntries, modifiers.getCode());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> ReadByIdsResult<T> readByIds(Class<T> clazz, Object[] ids) {
        try {
            return new ReadByIdsResultImpl<T>((T[]) space.readByIds(
                    ObjectUtils.assertArgumentNotNull(clazz, "class").getName(), ids, null, getCurrentTransaction(), 1, QueryResultTypeInternal.NOT_SET, false, null));
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T takeById(IdQuery<T> query) {
        try {
            return (T) space.takeById(query.getTypeName(), query.getId(), query.getRouting(), query.getVersion(),
                    getCurrentTransaction(), defaultTakeTimeout, defaultTakeModifiers.getCode(),
                    false, toInternal(query.getQueryResultType()), query.getProjections());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public <T> TakeByIdsResult<T> takeByIds(IdsQuery<T> query) {
        try {
            if (query.getRouting() != null)
                return new TakeByIdsResultImpl<T>((T[]) space.takeByIds(query.getTypeName(), query.getIds(), query.getRouting(), getCurrentTransaction(), defaultTakeModifiers.getCode(), toInternal(query.getQueryResultType()), false, query.getProjections()));
            else
                return new TakeByIdsResultImpl<T>((T[]) space.takeByIds(query.getTypeName(), query.getIds(), query.getRoutings(), getCurrentTransaction(), defaultTakeModifiers.getCode(), toInternal(query.getQueryResultType()), false, query.getProjections()));
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T[] takeMultiple(ISpaceQuery<T> template) {
        return takeMultiple(template, Integer.MAX_VALUE);
    }

    @SuppressWarnings("unchecked")
    public <T> T[] takeMultiple(ISpaceQuery<T> template, int maxEntries) {
        try {
            return (T[]) space.takeMultiple(template, getCurrentTransaction(), maxEntries, defaultTakeModifiers.getCode());
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    private static QueryResultTypeInternal toInternal(QueryResultType queryResultType) {
        if (queryResultType == null)
            return null;
        if (queryResultType == QueryResultType.DEFAULT || queryResultType == QueryResultType.NOT_SET)
            return QueryResultTypeInternal.NOT_SET;
        if (queryResultType == QueryResultType.OBJECT)
            return QueryResultTypeInternal.OBJECT_JAVA;
        if (queryResultType == QueryResultType.DOCUMENT)
            return QueryResultTypeInternal.DOCUMENT_ENTRY;

        throw new IllegalArgumentException("Unsupported query result type: " + queryResultType);
    }
    public Transaction getCurrentTransaction() {
        return null;
    }

    public void setSpace(ISpaceProxy space) {
        this.space = space;
    }

    public ISpaceProxy getSpace() {
        return space;
    }

    public <T> AggregationResult aggregate(ISpaceQuery<T> query, AggregationSet aggregationSet) {
        try {
            return space.aggregate(query, aggregationSet, getCurrentTransaction(), 1);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
