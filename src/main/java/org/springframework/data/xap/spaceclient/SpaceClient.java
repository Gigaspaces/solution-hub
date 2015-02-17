package org.springframework.data.xap.spaceclient;

import com.gigaspaces.async.AsyncFutureListener;
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

import java.util.concurrent.Future;

/**
 * This is a temporal replacement for GigaSpace/DefaultGigaSpace.
 * <p/>
 * We cannot use GigaSpace interface since it is in openspaces which depends on Spring 3.2 which is not compatible
 * with the latest Spring Data Commons (it depends on Spring 4)
 * <p/>
 * Once XAP supports the latest Spring version, this class can be substituted with GigaSpaces interface.
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
    private ChangeModifiers defaultChangeModifiers = ChangeModifiers.NONE;

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

    @SuppressWarnings("unchecked")
    public <T> T read(ISpaceQuery<T> template) {
        try {
            return (T) space.read(template, getCurrentTransaction(), defaultReadTimeout, defaultReadModifiers.getCode(), true);
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
    public <T> ReadByIdsResult<T> readByIds(IdsQuery<T> query) throws org.springframework.dao.DataAccessException {
        try {
            if (query.getRouting() != null)
                return new ReadByIdsResultImpl<T>((T[]) space.readByIds(query.getTypeName(), query.getIds(), query.getRouting(), getCurrentTransaction(), defaultReadModifiers.getCode(), toInternal(query.getQueryResultType()), false, query.getProjections()));
            else
                return new ReadByIdsResultImpl<T>((T[]) space.readByIds(query.getTypeName(), query.getIds(), query.getRoutings(), getCurrentTransaction(), defaultReadModifiers.getCode(), toInternal(query.getQueryResultType()), false, query.getProjections()));
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

    public <T> ChangeResult<T> change(ISpaceQuery<T> query, ChangeSet changeSet) {
        try {
            return space.change(query, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> ChangeResult<T> change(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers) {
        try {
            return space.change(query, changeSet, getCurrentTransaction(), 0, modifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    ;

    public <T> ChangeResult<T> change(ISpaceQuery<T> query, ChangeSet changeSet, long timeout) {
        try {
            return space.change(query, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> ChangeResult<T> change(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers, long timeout) {
        try {
            return space.change(query, changeSet, getCurrentTransaction(), timeout, modifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    ;

    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, long timeout) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, long timeout,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), 0, modifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), 0, modifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   long timeout) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), timeout, modifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> Future<ChangeResult<T>> asyncChange(ISpaceQuery<T> query, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   long timeout, AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(query, changeSet, getCurrentTransaction(), timeout, modifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> ChangeResult<T> change(T template, ChangeSet changeSet) {
        try {
            return space.change(template, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> ChangeResult<T> change(T template, ChangeSet changeSet, ChangeModifiers modifiers) {
        try {
            return space.change(template, changeSet, getCurrentTransaction(), 0, modifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    ;


    public <T> ChangeResult<T> change(T template, ChangeSet changeSet, long timeout) {
        try {
            return space.change(template, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> ChangeResult<T> change(T template, ChangeSet changeSet, ChangeModifiers modifiers, long timeout) {
        try {
            return space.change(template, changeSet, getCurrentTransaction(), timeout, modifiers);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), 0, defaultChangeModifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, long timeout) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, long timeout,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), timeout, defaultChangeModifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }


    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, ChangeModifiers modifiers) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), 0, modifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), 0, modifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   long timeout) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), timeout, modifiers, null);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public <T> Future<ChangeResult<T>> asyncChange(T template, ChangeSet changeSet, ChangeModifiers modifiers,
                                                   long timeout, AsyncFutureListener<ChangeResult<T>> listener) {
        try {
            return space.asyncChange(template, changeSet, getCurrentTransaction(), timeout, modifiers, listener);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    public Transaction getCurrentTransaction() {
        return null;
    }

    public ISpaceProxy getSpace() {
        return space;
    }

    public void setSpace(ISpaceProxy space) {
        this.space = space;
    }

    public <T> AggregationResult aggregate(ISpaceQuery<T> query, AggregationSet aggregationSet) {
        try {
            return space.aggregate(query, aggregationSet, getCurrentTransaction(), 1);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
