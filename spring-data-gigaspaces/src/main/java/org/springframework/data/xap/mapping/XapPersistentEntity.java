package org.springframework.data.xap.mapping;

import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.TypeInformation;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapPersistentEntity<T> extends BasicPersistentEntity<T, XapPersistentProperty> {

    public XapPersistentEntity(TypeInformation<T> information) {
        super(information);
    }

}