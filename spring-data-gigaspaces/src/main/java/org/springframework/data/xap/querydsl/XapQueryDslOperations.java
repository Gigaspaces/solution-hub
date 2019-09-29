package org.springframework.data.xap.querydsl;

import com.mysema.query.types.Operator;
import com.mysema.query.types.OperatorImpl;

/**
 * @author Leonid_Poliakov
 */
public class XapQueryDslOperations {
    private static final String NS = XapQueryDslOperations.class.getName();

    // negative comparisons
    public static final Operator<Boolean> NOT_LIKE = new OperatorImpl<Boolean>(NS, "NOT_LIKE");
    public static final Operator<Boolean> NOT_BETWEEN = new OperatorImpl<Boolean>(NS, "NOT_BETWEEN");
    public static final Operator<Boolean> NOT_EMPTY = new OperatorImpl<Boolean>(NS, "NOT_EMPTY");

}