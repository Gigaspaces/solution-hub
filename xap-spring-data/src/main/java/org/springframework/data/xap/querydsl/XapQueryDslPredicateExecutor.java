package org.springframework.data.xap.querydsl;

import com.gigaspaces.client.ChangeResult;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.QTuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * <p>Extension of {@link org.springframework.data.querydsl.QueryDslPredicateExecutor} with available XAP specific features: Projection and Change API</p>
 * <p>Use this interface as a base for your Querydsl repositories.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * public interface TypeRepository extends XapRepository&lt;Type, String&gt;, XapQueryDslPredicateExecutor&lt;Type&gt; {...}
 * </pre></blockquote>
 *
 * @author Oleksiy_Dyagilev
 */
public interface XapQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {

    /**
     * Returns a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none
     * was found. Applies projection to returned entity.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none was found.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    T findOne(Predicate predicate, QTuple projection);

    /**
     * Returns all entities matching the given {@link Predicate}. In case no match could be found an empty
     * {@link Iterable} is returned. Applies projection to returned entities.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return all entities matching the given {@link Predicate}.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    Iterable<T> findAll(Predicate predicate, QTuple projection);

    /**
     * Returns all entities matching the given {@link Predicate} applying the given
     * {@link com.mysema.query.types.OrderSpecifier}s. In case no match could be found an empty {@link Iterable} is
     * returned. Applies projection to returned entities.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param orders     any number of {@link com.mysema.query.types.OrderSpecifier} to perform sorting
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return all entities matching the given {@link Predicate} applying the given {@link com.mysema.query.types.OrderSpecifier}s.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    Iterable<T> findAll(Predicate predicate, QTuple projection, OrderSpecifier<?>... orders);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}. In case
     * no match could be found, an empty {@link org.springframework.data.domain.Page} is returned. Applies projection to
     * returned entities.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param pageable   definition of limit, skip and sorting for pagination
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    Page<T> findAll(Predicate predicate, Pageable pageable, QTuple projection);

    /**
     * Changes existing objects in space, returning a change result which provides details of the operation effect.
     * The change operation is designed for performance optimization, by allowing to change an existing object unlike
     * with regular updating write operation which usually require reading the object before applying an update to it.
     * As a part of the optimization, when the operation is replicated, on a best effort it will try to replicate only
     * the required data which is needed to apply the changes on the entry in the replicated target.
     *
     * @param predicate Querydsl style predicate used as a query
     * @param changeSet Changes to apply to the matched entry
     * @return A {@code ChangeResult} containing the details of the change operation affect.
     */
    ChangeResult<T> change(Predicate predicate, QChangeSet changeSet);

    /**
     * Returns a single entity matching the given {@link Predicate} or {@literal null} if none was found. Removes
     * returned entity from the space.
     *
     * @param predicate Querydsl style predicate used as a query
     * @return a single entity matching the given {@link Predicate} or {@literal null} if none was found.
     */
    T takeOne(Predicate predicate);

    /**
     * Returns all entities matching the given {@link Predicate}. In case no match could be found an empty
     * {@link Iterable} is returned. Removes returned entities from the space.
     *
     * @param predicate Querydsl style predicate used as a query
     * @return all entities matching the given {@link Predicate}.
     */
    Iterable<T> takeAll(Predicate predicate);

    /**
     * Returns a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none was found.
     * Removes returned entity from the space. Applies projection to returned entity.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return a single entity matching the given {@link com.mysema.query.types.Predicate} or {@literal null} if none was found.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    T takeOne(Predicate predicate, QTuple projection);

    /**
     * Returns all entities matching the given {@link Predicate}. In case no match could be found an empty
     * {@link Iterable} is returned. Removes returned entities from the space. Applies projection to returned entities.
     *
     * @param predicate  Querydsl style predicate used as a query
     * @param projection Querydsl style projection, create it with {@link org.springframework.data.xap.querydsl.QueryDslProjection}
     * @return all entities matching the given {@link Predicate}.
     * @see org.springframework.data.xap.querydsl.QueryDslProjection
     */
    Iterable<T> takeAll(Predicate predicate, QTuple projection);

}