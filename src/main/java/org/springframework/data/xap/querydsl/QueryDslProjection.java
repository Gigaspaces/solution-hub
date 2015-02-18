package org.springframework.data.xap.querydsl;

import com.mysema.query.types.Expression;
import com.mysema.query.types.QTuple;

/**
 * Helper class to marry Spring Data and Query DSL Projections and make API look better.
 *
 * @author Oleksiy_Dyagilev
 */
public class QueryDslProjection {

    private QueryDslProjection() {
    }

    /**
     * use static import for this method
     */
    public static QTuple projection(Expression<?>... exprs) {
        return new QTuple(exprs);
    }

}
