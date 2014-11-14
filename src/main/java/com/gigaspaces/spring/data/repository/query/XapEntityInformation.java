package com.gigaspaces.spring.data.repository.query;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public interface XapEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {
}
