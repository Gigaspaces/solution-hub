package org.springframework.data.xap.repository.support;

import com.gigaspaces.client.ChangeResult;
import com.gigaspaces.query.ISpaceQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.mysema.query.types.*;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.querydsl.QChangeSet;
import org.springframework.data.xap.querydsl.XapQueryDslConverter;
import org.springframework.data.xap.querydsl.XapQueryDslPredicateExecutor;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.data.xap.querydsl.Utils.convertPathToXapFieldString;

/**
 * @author Leonid_Poliakov
 */
public class QueryDslXapRepository<T, ID extends Serializable> extends SimpleXapRepository<T, ID> implements XapQueryDslPredicateExecutor<T> {
    private GigaSpace space;
    private EntityInformation<T, ID> entityInformation;

    public QueryDslXapRepository(GigaSpace space, EntityInformation<T, ID> entityInformation) {
        super(space, entityInformation);
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public T findOne(Predicate predicate) {
        return space.read(createQuery(predicate, null, null));
    }

    @Override
    public T findOne(Predicate predicate, QTuple projection) {
        return space.read(createQuery(predicate, null, projection));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return readMultiple(predicate, null, null);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, QTuple projection) {
        return readMultiple(predicate, null, projection);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return readMultiple(predicate, null, null, orders);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, QTuple projection, OrderSpecifier<?>... orders) {
        return readMultiple(predicate, null, projection, orders);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return findWithPagingInternal(predicate, pageable, null);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable, QTuple projection) {
        return findWithPagingInternal(predicate, pageable, projection);
    }

    @Override
    public long count(Predicate predicate) {
        ISpaceQuery<T> query = createQuery(predicate, null, null);
        return space.aggregate(query, new AggregationSet().count("")).getLong(0);
    }

    @Override
    public ChangeResult<T> change(Predicate predicate, QChangeSet qChangeSet) {
        ISpaceQuery<T> query = createQuery(predicate, null, null);
        return space.change(query, qChangeSet.getNativeChangeSet());
    }

    @Override
    public T takeOne(Predicate predicate) {
        return space.take(createQuery(predicate, null, null));
    }

    @Override
    public T takeOne(Predicate predicate, QTuple projection) {
        return space.take(createQuery(predicate, null, projection));
    }

    @Override
    public Iterable<T> takeAll(Predicate predicate) {
        return takeMultiple(predicate, null, null);
    }

    @Override
    public Iterable<T> takeAll(Predicate predicate, QTuple projection) {
        return takeMultiple(predicate, null, projection);
    }

    private Page<T> findWithPagingInternal(Predicate predicate, Pageable pageable, QTuple projection) {
        return cutPageable(readMultiple(predicate, pageable, projection), pageable);
    }

    private Page<T> cutPageable(List<T> sortedResults, Pageable pageable) {
        List<T> pageResults;
        if (pageable.getOffset() < sortedResults.size()) {
            pageResults = sortedResults.subList(pageable.getOffset(), sortedResults.size());
        } else {
            pageResults = Collections.emptyList();
        }
        return new PageImpl<>(pageResults);
    }

    private List<T> readMultiple(Predicate predicate, Pageable pageable, QTuple projection, OrderSpecifier<?>... orders) {
        ISpaceQuery<T> query = createQuery(predicate, pageable, projection, orders);
        return Arrays.asList(space.readMultiple(query));
    }

    private List<T> takeMultiple(Predicate predicate, Pageable pageable, QTuple projection, OrderSpecifier<?>... orders) {
        ISpaceQuery<T> query = createQuery(predicate, pageable, projection, orders);
        return Arrays.asList(space.takeMultiple(query));
    }

    private ISpaceQuery<T> createQuery(Predicate predicate, Pageable pageable, QTuple projection, OrderSpecifier<?>... orders) {
        return new XapQueryDslConverter<>(entityInformation.getJavaType()).convert(predicate, pageable, convertQTupleToProjection(projection), orders);
    }

    @SuppressWarnings("unchecked")
    private Projection convertQTupleToProjection(QTuple qTuple) {
        if (qTuple == null) {
            return null;
        }
        List<String> fields = new ArrayList<>();
        for (Expression<?> expr : qTuple.getArgs()) {
            if (!(expr instanceof Path)) {
                throw new RuntimeException("Unexpected expression type of projection");
            }
            Path<String> path = ((Path) expr);
            fields.add(convertPathToXapFieldString(path));

        }
        return new Projection(fields.toArray(new String[fields.size()]));
    }

}