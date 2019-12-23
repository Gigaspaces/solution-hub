package org.springframework.data.gigaspaces.mapping;

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesPersistentProperty extends AnnotationBasedPersistentProperty<GigaspacesPersistentProperty> {


    /**
     * Creates a new {@link org.springframework.data.mapping.model.AnnotationBasedPersistentProperty}.
     *
     * @param property           can be {@literal null}.
     * @param owner              must not be {@literal null}.
     * @param simpleTypeHolder
     */
    public GigaspacesPersistentProperty(Property property, PersistentEntity<?, GigaspacesPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
    }

    @Override
    protected Association<GigaspacesPersistentProperty> createAssociation() {
        return new Association<GigaspacesPersistentProperty>(this, null);
    }
}
