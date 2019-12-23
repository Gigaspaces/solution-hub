package org.springframework.data.gigaspaces.mapping;

import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesPersistentEntity<T> extends BasicPersistentEntity<T, GigaspacesPersistentProperty> {

    public GigaspacesPersistentEntity(TypeInformation<T> information) {
        super(information);
    }

}