package org.springframework.data.gigaspaces.repository.query;

import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public interface GigaspacesEntityInformation<T, ID extends Serializable> extends EntityInformation<T, ID> {
}
