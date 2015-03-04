package org.springframework.data.xap.repository;

import com.j_spaces.core.LeaseContext;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * <p>XAP specific {@link org.springframework.data.repository.Repository} interface. Contains features from
 * {@link org.springframework.data.repository.CrudRepository} and
 * {@link org.springframework.data.repository.PagingAndSortingRepository}. Is also supplied with XAP features: take
 * methods, Change and Projection APIs.</p>
 * <p>Use this interface as a base for you XAP repositories.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * public interface PersonRepository extends XapRepository&lt;Person, Integer&gt; {...}
 * </pre></blockquote>
 *
 * @param <T>  the type of entity to be stored in repository
 * @param <ID> the type of entity id field
 * @author Oleksiy_Dyagilev
 */
@NoRepositoryBean
public interface XapRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    /**
     * Provides access to GigaSpaces XAP native API.
     *
     * @return GigaSpace object used by this repository.
     */
    GigaSpace space();

    /**
     * Retrieves an entity by its id. Applies projection to returned entity.
     *
     * @param id         must not be {@literal null}.
     * @param projection a set of field names to be returned
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(ID id, Projection projection);

    /**
     * Returns all instances of the type with applied projection.
     *
     * @param projection a set of field names to be returned
     * @return all entities
     */
    Iterable<T> findAll(Projection projection);

    /**
     * Returns all instances of the type with the given IDs. Applies projection to returned entities.
     *
     * @param ids        a set of entity ids to return
     * @param projection a set of field names to be returned
     * @return the entities with given ids or an empty {@code Iterable}
     */
    Iterable<T> findAll(Iterable<ID> ids, Projection projection);

    /**
     * Returns all entities sorted by the given options with applied projection.
     *
     * @param sort       a sort option for the query
     * @param projection a set of field names to be returned
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort, Projection projection);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities meeting the paging restriction provided in the
     * {@code Pageable} object. Applies projection to returned entities.
     *
     * @param pageable   an object containing paging information
     * @param projection a set of field names to be returned
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable, Projection projection);

    /**
     * Saves entity with a limited life span.
     *
     * @param entity the entity to save
     * @param lease  the lease value
     * @param unit   the unit of lease time
     * @param <S>    saved entity type
     * @return information about saved object lease
     */
    <S extends T> LeaseContext<S> save(S entity, long lease, TimeUnit unit);

    /**
     * Saves multiple entities with a limited life span.
     *
     * @param entities a collection of entities to save
     * @param lease    the lease value
     * @param unit     the unit of lease time
     * @return information about saved objects leases
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<LeaseContext<S>> save(Iterable<S> entities, long lease, TimeUnit unit);

    /**
     * Gets entity by id and deletes it.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     */
    T takeOne(ID id);

    /**
     * Gets multiple entities by id and deletes them.
     *
     * @param ids a set of entity ids to return
     * @return the entities with given ids or an empty {@code Iterable}
     */
    Iterable<T> takeAll(Iterable<ID> ids);

    /**
     * Gets all entities and deletes them.
     *
     * @return all entities from the space
     */
    Iterable<T> takeAll();

}