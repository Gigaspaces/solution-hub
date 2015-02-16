package org.springframework.data.xap.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
@NoRepositoryBean
public interface XapRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
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

}
