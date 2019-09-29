package org.springframework.data.xap.repository.query;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public class DefaultXapEntityInformation<T, ID extends Serializable> extends PersistentEntityInformation<T, ID> implements XapEntityInformation<T, ID> {

    /**
     * Creates a new <code>PersistableEntityInformation</code> for the given {@link org.springframework.data.mapping.PersistentEntity}.
     *
     * @param entity must not be {@literal null}.
     */
    public DefaultXapEntityInformation(PersistentEntity<T, ?> entity) {
        super(entity);
    }

}