package org.springframework.data.gigaspaces.querydsl;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QTuple;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * <p>Helper class to marry Spring Data and Querydsl Projections and make API look better.</p>
 * <p>Use this class to perform Gigaspaces Projections in Querydsl style.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * repository.findOne(...query..., projection(person.name, person.age)) returns entity with only two fields;
 * </pre></blockquote>
 *
 * @author Oleksiy_Dyagilev
 * @see GigaspacesRepository
 */
public interface GigaspacesDslProjection {


    /**
     * Converts Querydsl expressions into QTuple used by Gigaspaces Projection API.
     * Tip: use static import for this method.
     *
     * @param expressions
     * @return
     */
    static QTuple projection(Expression<?>... expressions) {
        return Projections.tuple(expressions);
    }

}