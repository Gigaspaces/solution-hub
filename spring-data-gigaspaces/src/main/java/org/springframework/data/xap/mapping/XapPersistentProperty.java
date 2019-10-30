package org.springframework.data.xap.mapping;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapPersistentProperty extends AnnotationBasedPersistentProperty<XapPersistentProperty> {


    /**
     * Creates a new {@link org.springframework.data.mapping.model.AnnotationBasedPersistentProperty}.
     *
     * @param property           can be {@literal null}.
     * @param owner              must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public XapPersistentProperty(Property property, PersistentEntity<?, XapPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }

    @Override
    protected Association<XapPersistentProperty> createAssociation() {
        return new Association<XapPersistentProperty>(this, null);
    }
}
