package org.springframework.data.xap.mapping;

import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapMappingContext extends AbstractMappingContext<XapPersistentEntity<?>, XapPersistentProperty> {

    @Override
    protected <T> XapPersistentEntity<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new XapPersistentEntity<>(typeInformation);
    }

    @Override
    protected XapPersistentProperty createPersistentProperty(Field field, PropertyDescriptor descriptor, XapPersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new XapPersistentProperty(field, descriptor, owner, simpleTypeHolder);
    }
}
