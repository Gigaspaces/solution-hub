package com.gigaspaces.spring.data.repository;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public interface XapRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
}
