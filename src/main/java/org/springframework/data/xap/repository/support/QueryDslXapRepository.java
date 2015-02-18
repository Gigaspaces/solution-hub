package org.springframework.data.xap.repository.support;

import com.gigaspaces.query.ISpaceQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mysema.query.types.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.querydsl.XapQueryDslConverter;
import org.springframework.data.xap.querydsl.XapQueryDslPredicateExecutor;
import org.springframework.data.xap.repository.query.Projection;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonid_Poliakov
 */
public class QueryDslXapRepository<T, ID extends Serializable> extends SimpleXapRepository<T, ID> implements XapQueryDslPredicateExecutor<T> {
    private SpaceClient space;
    private EntityInformation<T, ID> entityInformation;

    public QueryDslXapRepository(SpaceClient space, EntityInformation<T, ID> entityInformation) {
        super(space, entityInformation);
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public T findOne(Predicate predicate) {
        ISpaceQuery<T> query = createQuery(predicate, null, null);
        return space.read(query);
    }

    @Override
    public T findOne(Predicate predicate, QTuple projection) {
        ISpaceQuery<T> query = createQuery(predicate, null, convertQTupleToProjection(projection));
        return space.read(query);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return readMultiple(createQuery(predicate, null, null));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, QTuple projection) {
        return readMultiple(createQuery(predicate, null, convertQTupleToProjection(projection)));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return readMultiple(createQuery(predicate, null, null, orders));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, QTuple projection, OrderSpecifier<?>... orders) {
        return readMultiple(createQuery(predicate, null, convertQTupleToProjection(projection), orders));
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return findAllWithPagingInternal(predicate, pageable, null);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable, QTuple projection) {
        return findAllWithPagingInternal(predicate, pageable, convertQTupleToProjection(projection));
    }

    private Page<T> findAllWithPagingInternal(Predicate predicate, Pageable pageable, Projection projection) {
        List<T> sortedResults = readMultiple(createQuery(predicate, pageable, projection));
        List<T> pageResults;
        if (pageable.getOffset() < sortedResults.size()) {
            pageResults = sortedResults.subList(pageable.getOffset(), sortedResults.size());
        } else {
            pageResults = Collections.emptyList();
        }
        return new PageImpl<>(pageResults);
    }

    @Override
    public long count(Predicate predicate) {
        ISpaceQuery<T> query = createQuery(predicate, null, null);
        return space.aggregate(query, new AggregationSet().count("")).getLong(0);
    }

    private List<T> readMultiple(ISpaceQuery<T> query) {
        return Arrays.asList(space.readMultiple(query));
    }

    private ISpaceQuery<T> createQuery(Predicate predicate, Pageable pageable, Projection projection, OrderSpecifier<?>... orders) {
        return new XapQueryDslConverter<>(entityInformation.getJavaType()).convert(predicate, pageable, projection, orders);
    }

    @SuppressWarnings("unchecked")
    private Projection convertQTupleToProjection(QTuple qTuple) {
        List<String> fields = new ArrayList<>();
        for (Expression<?> expr : qTuple.getArgs()) {
            if (!(expr instanceof Path)) {
                throw new RuntimeException("Unexpected expression type of projection");
            }
            Path<String> path = ((Path) expr);
            fields.add(convertPathToXapProjection(path));

        }
        return new Projection(fields.toArray(new String[fields.size()]));
    }

    private String convertPathToXapProjection(Path<String> path) {
        return convertPathToXapProjection(path, new ArrayList<String>());
    }

    // recursively traverse and accumulate path into a list of fields
    private String convertPathToXapProjection(Path<?> path, List<String> fieldsPath) {
        if (path.getMetadata().isRoot()) {
            return Joiner.on(".").join(Lists.reverse(fieldsPath));
        } else {
            fieldsPath.add(path.getMetadata().getName());
            return convertPathToXapProjection(path.getMetadata().getParent(), fieldsPath);
        }
    }
}