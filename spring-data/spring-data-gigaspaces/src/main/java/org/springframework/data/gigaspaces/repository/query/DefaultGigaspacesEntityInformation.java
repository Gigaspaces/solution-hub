package org.springframework.data.gigaspaces.repository.query;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

import java.io.Serializable;

/**
 * @author Oleksiy_Dyagilev
 */
public class DefaultGigaspacesEntityInformation<T, ID extends Serializable> extends PersistentEntityInformation<T, ID> implements GigaspacesEntityInformation<T, ID> {

    /**
     * Creates a new <code>PersistableEntityInformation</code> for the given {@link org.springframework.data.mapping.PersistentEntity}.
     *
     * @param entity must not be {@literal null}.
     */
    public DefaultGigaspacesEntityInformation(PersistentEntity<T, ?> entity) {
        super(entity);
    }

}