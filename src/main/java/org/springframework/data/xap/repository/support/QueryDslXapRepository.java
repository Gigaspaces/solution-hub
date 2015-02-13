package org.springframework.data.xap.repository.support;

import com.gigaspaces.query.ISpaceQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.querydsl.XapQueryDslConverter;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Leonid_Poliakov
 */
public class QueryDslXapRepository<T, ID extends Serializable> extends SimpleXapRepository<T, ID> implements QueryDslPredicateExecutor<T> {
    private SpaceClient space;
    private EntityInformation<T, ID> entityInformation;

    public QueryDslXapRepository(SpaceClient space, EntityInformation<T, ID> entityInformation) {
        super(space, entityInformation);
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public T findOne(Predicate predicate) {
        ISpaceQuery<T> query = createQuery(predicate, null);
        return space.read(query);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return readMultiple(createQuery(predicate, null));
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return readMultiple(createQuery(predicate, null, orders));
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        List<T> sortedResults = readMultiple(createQuery(predicate, pageable));
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
        ISpaceQuery<T> query = createQuery(predicate, null);
        return space.aggregate(query, new AggregationSet().count("")).getLong(0);
    }

    private List<T> readMultiple(ISpaceQuery<T> query) {
        return Arrays.asList(space.readMultiple(query));
    }

    private ISpaceQuery<T> createQuery(Predicate predicate, Pageable pageable, OrderSpecifier<?>... orders) {
        return new XapQueryDslConverter<>(entityInformation.getJavaType()).convert(predicate, pageable, orders);
    }

}