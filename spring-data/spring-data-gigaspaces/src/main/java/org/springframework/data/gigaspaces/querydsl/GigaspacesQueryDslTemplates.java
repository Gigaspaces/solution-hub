package org.springframework.data.gigaspaces.querydsl;

import com.querydsl.core.types.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Defines how different operations will be written to final SQL query.
 *
 * @author Leonid_Poliakov
 */
public class GigaspacesQueryDslTemplates extends Templates {
    public static final GigaspacesQueryDslTemplates DEFAULT = new GigaspacesQueryDslTemplates();

    private Set<Operator> allowedOperators;
    private Map<Operator, Operator> oppositesMapping;
    private Set<Operator> regexOperators;

    protected GigaspacesQueryDslTemplates() {
        this('\\');
    }

    protected GigaspacesQueryDslTemplates(char escape) {
        super(escape);
        allowedOperators = new HashSet<>();
        regexOperators = new HashSet<>();
        oppositesMapping = new HashMap<>();
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
        template(Ops.STRING_IS_EMPTY, "{0} = ''");

        // comparison
        template(Ops.BETWEEN, "{0} between {1} and {2}", 30);

        // negative comparison (will replace not(operation) with these)
        negativeTemplate(GigaspacesQueryDslOperations.NOT_EMPTY, Ops.STRING_IS_EMPTY, "{0} <> ''");
        negativeTemplate(GigaspacesQueryDslOperations.NOT_BETWEEN, Ops.BETWEEN, "({0} < {1} or {0} > {2})", 30);
        negativeTemplate(GigaspacesQueryDslOperations.NOT_LIKE, Ops.LIKE, "{0} not like {1}", 26);

        // variables
        template(Ops.DateTimeOps.SYSDATE, "sysdate");

        // literals
        template(PathType.PROPERTY, "{1s}");

        // regex like
        template(Ops.MATCHES, "{0} rlike {1}");

        // string matchers
        regexTemplate(Ops.STRING_CONTAINS);
        regexTemplate(Ops.STRING_CONTAINS_IC);
        regexTemplate(Ops.STARTS_WITH);
        regexTemplate(Ops.STARTS_WITH_IC);
        regexTemplate(Ops.ENDS_WITH);
        regexTemplate(Ops.ENDS_WITH_IC);
    }

    private void template(Operator operator, String pattern) {
        add(operator, pattern);
        allowedOperators.add(operator);
    }

    private void template(Operator operator, String pattern, int precedence) {
        add(operator, pattern, precedence);
        allowedOperators.add(operator);
    }

    private void regexTemplate(Operator operator) {
        add(operator, "{0} rlike {1}");
        allowedOperators.add(operator);
        regexOperators.add(operator);
    }

    private void negativeTemplate(Operator operator, Operator opposite, String pattern) {
        add(operator, pattern);
        oppositesMapping.put(opposite, operator);
        allowedOperators.add(operator);
    }

    private void negativeTemplate(Operator operator, Operator opposite, String pattern, int precedence) {
        add(operator, pattern, precedence);
        oppositesMapping.put(opposite, operator);
        allowedOperators.add(operator);
    }

    public boolean isAllowed(Operator operator) {
        return allowedOperators.contains(operator);
    }

    public boolean isRegex(Operator operator) {
        return regexOperators.contains(operator);
    }

    public Operator getNegative(Operator operator) {
        return oppositesMapping.get(operator);
    }
}