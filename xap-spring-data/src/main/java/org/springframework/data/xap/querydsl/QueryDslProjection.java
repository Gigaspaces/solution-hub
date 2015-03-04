package org.springframework.data.xap.querydsl;

import com.mysema.query.types.Expression;
import com.mysema.query.types.QTuple;

/**
 * <p>Helper class to marry Spring Data and Querydsl Projections and make API look better.</p>
 * <p>Use this class to perform XAP projections in Querydsl style.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * repository.findOne(...query..., projection(person.name, person.age)) returns entity with only two fields;
 * </pre></blockquote>
 *
 * @author Oleksiy_Dyagilev
 * @see org.springframework.data.xap.repository.XapRepository
 */
public class QueryDslProjection {

    private QueryDslProjection() {
    }

    /**
     * Converts Querydsl expressions into QTuple used by XAP Projection API.
     * Tip: use static import for this method.
     *
     * @param expressions
     * @return
     */
    public static QTuple projection(Expression<?>... expressions) {
        return new QTuple(expressions);
    }

}