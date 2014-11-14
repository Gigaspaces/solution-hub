package com.gigaspaces.spring.data.repository.mapping;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapPersistentProperty extends AnnotationBasedPersistentProperty<XapPersistentProperty> {


    /**
     * Creates a new {@link org.springframework.data.mapping.model.AnnotationBasedPersistentProperty}.
     *
     * @param field              must not be {@literal null}.
     * @param propertyDescriptor can be {@literal null}.
     * @param owner              must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public XapPersistentProperty(Field field, PropertyDescriptor propertyDescriptor, PersistentEntity<?, XapPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
    }

    @Override
    protected Association<XapPersistentProperty> createAssociation() {
        return new Association<XapPersistentProperty>(this, null);
    }
}
