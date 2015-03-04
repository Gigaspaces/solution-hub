package org.springframework.data.xap.repository;

import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;

/**
 * Extension of {@link PagingAndSortingRepository} to provide XAP specific features
 *
 * @author Oleksiy_Dyagilev
 */
@NoRepositoryBean
public interface XapRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    /**
     * Provides access to GigaSpaces XAP native API
     *
     * @return
     */
    GigaSpace space();

    /**
     * Retrieves an entity by its id.
     *
     * @param id         must not be {@literal null}.
     * @param projection projection
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    T findOne(ID id, Projection projection);

    /**
     * Returns all instances of the type with applied projection.
     *
     * @param projection projection
     * @return all entities
     */
    Iterable<T> findAll(Projection projection);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @param projection projection
     * @return
     */
    Iterable<T> findAll(Iterable<ID> ids, Projection projection);

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @param projection
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort, Projection projection);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @param projection
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable, Projection projection);

    /**
     * Saves entities with a limited life span.
     *
     * @param entity
     * @param lease
     * @param <S>
     * @return the saved entity
     */
    <S extends T> S save(S entity, long lease);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> save(Iterable<S> entities, long lease);

    /**
     * Gets entity by id and deletes it.
     *
     * @param id
     * @return
     */
    T takeOne(ID id);

    /**
     * Gets multiple entities by id and deletes them.
     *
     * @param ids
     * @return
     */
    Iterable<T> take(Iterable<ID> ids);

    /**
     * Gets all entities and deletes them.
     *
     * @return
     */
    Iterable<T> takeAll();
}
