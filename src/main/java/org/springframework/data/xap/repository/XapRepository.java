package org.springframework.data.xap.repository;

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
     * Returns all instances of the type with applied projection.
     *
     * @param projection projection
     * @return all entities
     */
    Iterable<T> findAll(Projection projection);

}
