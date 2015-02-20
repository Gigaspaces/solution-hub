package org.springframework.data.xap.querydsl;

import com.gigaspaces.client.ChangeResult;
import com.gigaspaces.client.ChangeSet;
import com.mysema.query.types.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.xap.repository.query.Projection;

/**
 * Extension with XAP specific features
 *
 * @author Oleksiy_Dyagilev
 */
public interface XapQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {

    /**
     * Returns a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none was found.
     *
     * @param predicate
     * @param projection
     * @return a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none was found.
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if the predicate yields more than one result.
     */
    T findOne(Predicate predicate, QTuple projection);

    /**
     * Returns all entities matching the given {@link Predicate}. In case no match could be found an empty
     * {@link Iterable} is returned.
     *
     * @param predicate
     * @param projection
     * @return all entities matching the given {@link Predicate}.
     */
    Iterable<T> findAll(Predicate predicate, QTuple projection);

    /**
     * Returns all entities matching the given {@link Predicate} applying the given {@link com.mysema.query.types.OrderSpecifier}s. In case no
     * match could be found an empty {@link Iterable} is returned.
     *
     * @param predicate
     * @param orders
     * @param projection
     * @return all entities matching the given {@link Predicate} applying the given {@link com.mysema.query.types.OrderSpecifier}s.
     */
    Iterable<T> findAll(Predicate predicate, QTuple projection, OrderSpecifier<?>... orders);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}. In case no match could be found, an empty
     * {@link org.springframework.data.domain.Page} is returned.
     *
     * @param predicate
     * @param pageable
     * @param projection
     * @return a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}.
     */
    Page<T> findAll(Predicate predicate, Pageable pageable, QTuple projection);

    /**
     * Changes existing objects in space, returning a change result which provides details of the operation affect.
     * The change operation is designed for performance optimization, By allowing to change an existing object unlike
     * with regular updating write operation which usually requires reading the object before applying to update to it.
     * As part of the optimization, when the operation is replicated, on a best effort it will
     * try to replicate only the required data which is needed to apply the changes on the entry in the replicated target.
     *
     * @param predicate predicate to match entities
     * @param changeSet Changes to apply to the matched entry
     * @return A <code>ChangeResult</code> containing the details of the change operation affect.
     */
    ChangeResult<T> change(Predicate predicate, QChangeSet changeSet);

}
