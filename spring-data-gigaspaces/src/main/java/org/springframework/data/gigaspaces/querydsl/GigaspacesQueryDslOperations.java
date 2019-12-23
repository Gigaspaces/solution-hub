package org.springframework.data.gigaspaces.querydsl;

import com.querydsl.core.types.Operator;

/**
 * @author Leonid_Poliakov
 */
public enum GigaspacesQueryDslOperations implements Operator {
    // negative comparisons
    NOT_LIKE(Boolean.class),NOT_BETWEEN(Boolean.class),NOT_EMPTY(Boolean.class);
    private Class<?> type;
    GigaspacesQueryDslOperations(Class<?> type){
        this.type=type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }


}