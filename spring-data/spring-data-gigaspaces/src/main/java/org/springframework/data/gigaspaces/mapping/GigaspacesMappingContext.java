package org.springframework.data.gigaspaces.mapping;

import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesMappingContext extends AbstractMappingContext<GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> {

    @Override
    protected <T> GigaspacesPersistentEntity<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new GigaspacesPersistentEntity<>(typeInformation);
    }

    @Override
    protected GigaspacesPersistentProperty createPersistentProperty(Property property, GigaspacesPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new GigaspacesPersistentProperty(property, owner, simpleTypeHolder);
    }

}
