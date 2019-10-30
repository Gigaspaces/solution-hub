package org.springframework.data.xap.mapping;

import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapMappingContext extends AbstractMappingContext<XapPersistentEntity<?>, XapPersistentProperty> {

    @Override
    protected <T> XapPersistentEntity<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new XapPersistentEntity<>(typeInformation);
    }

    @Override
    protected XapPersistentProperty createPersistentProperty(Property property, XapPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new XapPersistentProperty(property, owner, simpleTypeHolder);
    }

}
