package org.springframework.data.xap.querydsl;

import com.mysema.query.types.Operator;
import com.mysema.query.types.Ops;
import com.mysema.query.types.PathType;
import com.mysema.query.types.Templates;

import java.util.HashSet;
import java.util.Set;

/**
 * Define how different operations will be written to final SQL query.
 *
 * @author Leonid_Poliakov
 */
public class XapQueryDslTemplates extends Templates {
    public static final XapQueryDslTemplates DEFAULT = new XapQueryDslTemplates();

    private Set<Operator> allowedOperators;

    protected XapQueryDslTemplates() {
        this('\\');
    }

    protected XapQueryDslTemplates(char escape) {
        super(escape);
        allowedOperators = new HashSet<>();
        // operations below are described as supported by SQLQuery
        // some of these duplicate the code in parent to make sure those operations are properly supported

        // grouping conditions
        template(Ops.AND, "{0} and {1}", 36);
        template(Ops.OR, "{0} or {1}", 38);

        // logical operations
        template(Ops.EQ, "{0} = {1}", 18);
        template(Ops.NE, "{0} <> {1}", 25);
        template(Ops.LT, "{0} < {1}", 23);
        template(Ops.GT, "{0} > {1}", 21);
        template(Ops.GOE, "{0} >= {1}", 20);
        template(Ops.LOE, "{0} <= {1}", 22);
        template(Ops.LIKE, "{0} like {1}", 26);
        template(Ops.IS_NULL, "{0} is null", 26);
        template(Ops.IS_NOT_NULL, "{0} is not null", 26);
        template(Ops.IN, "{0} in ({1})", 27);
        template(Ops.NOT_IN, "{0} not in ({1})", 27);

        // comparison
        template(Ops.BETWEEN, "{0} between {1} and {2}", 30);

        // variables
        template(Ops.DateTimeOps.SYSDATE, "sysdate");

        // literals
        template(PathType.PROPERTY, "{1s}");
    }

    private void template(Operator<?> operator, String pattern) {
        add(operator, pattern);
        allowedOperators.add(operator);
    }

    private void template(Operator<?> operator, String pattern, int precedence) {
        add(operator, pattern, precedence);
        allowedOperators.add(operator);
    }

    public boolean isAllowed(Operator<?> operator) {
        return allowedOperators.contains(operator);
    }
}