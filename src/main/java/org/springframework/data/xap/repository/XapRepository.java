package org.springframework.data.xap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public interface XapRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
