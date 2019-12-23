package org.springframework.data.gigaspaces.repository.support;

import com.gigaspaces.client.ChangeResult;
import com.gigaspaces.query.ISpaceQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.PathBuilder;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.gigaspaces.querydsl.QChangeSet;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslConverter;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslPredicateExecutor;
import org.springframework.data.gigaspaces.repository.query.Projection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString;

/**
 * @author Leonid_Poliakov
 */
public class QueryDslGigaspacesRepository<T, ID extends Serializable> extends SimpleGigaspacesRepository<T, ID> implements GigaspacesQueryDslPredicateExecutor<T> {
    private GigaSpace space;
    private EntityInformation<T, ID> entityInformation;

    public QueryDslGigaspacesRepository(GigaSpace space, EntityInformation<T, ID> entityInformation) {
        super(space, entityInformation);
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public Optional<T> findOne(Predicate predicate) {
        return Optional.ofNullable(space.read(createQuery(predicate, null, null)));
    }

    @Override
    public Optional<T> findOne(Predicate predicate, QTuple projection) {
        return Optional.ofNullable(space.read(createQuery(predicate, null, projection)));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return readMultiple(predicate, null, null);
    }

    private OrderSpecifier<?>[] toOrderSpecifier(Sort sort){
        Class clazz=entityInformation.getJavaType();
        PathBuilder orderByExpression = new PathBuilder(clazz, "object");
        return sort.stream().map(o->
            new OrderSpecifier(o.isAscending() ? Order.ASC
                    : Order.DESC, orderByExpression.get(o.getProperty())))
                .toArray(OrderSpecifier[]::new);

    }

    @Override
    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        return readMultiple(predicate, null, null,toOrderSpecifier(sort));
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
    public Iterable<T> findAll(OrderSpecifier<?>... orders) {
        return findAll(null, orders);
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
    public boolean exists(Predicate predicate) {
        return count(predicate)!=0;
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
        return new PageImpl<>(readMultiple(predicate, pageable, projection));
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
        return new GigaspacesQueryDslConverter<>(entityInformation.getJavaType()).convert(predicate, pageable, convertQTupleToProjection(projection), orders);
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
            fields.add(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path));

        }
        return new Projection(fields.toArray(new String[fields.size()]));
    }
}