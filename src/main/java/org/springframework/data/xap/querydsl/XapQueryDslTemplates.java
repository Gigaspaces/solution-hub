package org.springframework.data.xap.querydsl;

import com.mysema.query.types.Ops;
import com.mysema.query.types.PathType;
import com.mysema.query.types.Templates;

/**
 * Define how different operations will be written to final SQL query.
 *
 * @author Leonid_Poliakov
 */
public class XapQueryDslTemplates extends Templates {
    public static final XapQueryDslTemplates DEFAULT = new XapQueryDslTemplates();

    protected XapQueryDslTemplates() {
        this('\\');
    }

    protected XapQueryDslTemplates(char escape) {
        super(escape);
        // operations below are described as supported by SQLQuery
        // some of these duplicate the code in parent to make sure those operations are properly supported

        // grouping conditions
        add(Ops.AND, "{0} and {1}", 36);
        add(Ops.OR, "{0} or {1}", 38);

        // logical operations
        add(Ops.EQ, "{0} = {1}", 18);
        add(Ops.NE, "{0} <> {1}", 25);
        add(Ops.LT, "{0} < {1}", 23);
        add(Ops.GT, "{0} > {1}", 21);
        add(Ops.GOE, "{0} >= {1}", 20);
        add(Ops.LOE, "{0} <= {1}", 22);
        add(Ops.LIKE, "{0} like {1}", 26);
        add(Ops.IS_NULL, "{0} is null", 26);
        add(Ops.IS_NOT_NULL, "{0} is not null", 26);
        add(Ops.IN, "{0} in ({1})", 27);
        add(Ops.NOT_IN, "{0} not in ({1})", 27);

        // aggregation
        add(Ops.AggOps.AVG_AGG, "avg({0})");
        add(Ops.AggOps.MAX_AGG, "max({0})");
        add(Ops.AggOps.MIN_AGG, "min({0})");
        add(Ops.AggOps.SUM_AGG, "sum({0})");
        add(Ops.AggOps.COUNT_AGG, "count({0})");
        add(Ops.AggOps.COUNT_ALL_AGG, "count(*)");

        // comparison
        add(Ops.BETWEEN, "{0} between {1} and {2}", 30);

        // variables
        add(Ops.DateTimeOps.SYSDATE, "sysdate");

        // literals
        add(PathType.PROPERTY, "{1s}");
    }
}