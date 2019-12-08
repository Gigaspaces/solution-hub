package org.springframework.data.gigaspaces.repository.query;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.Part;

import java.util.Iterator;

import static java.lang.String.format;
import static org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils.isPaged;
import static org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils.isSorted;
import static org.springframework.util.Assert.notNull;

public class QueryBuilder {

    Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
    private String query = "";

    public QueryBuilder(Part part) {
        query = new AtomicPredicate(part).toString();
    }

    public QueryBuilder(Sort sort) {
        applySort(sort);
    }

    public QueryBuilder(Pageable pageable) {
        applyPaging(pageable);
        applySort(pageable.getSort());
    }

    public String buildQuery() {
        return query;
    }

    public QueryBuilder and(QueryBuilder queryBuilder) {
        query = query + " AND " + queryBuilder.query;
        return this;
    }

    public QueryBuilder or(QueryBuilder criteria) {
        query = query + " OR " + criteria.query;
        return this;
    }

    public QueryBuilder sort(Sort sort) {
        applySort(sort);
        return this;
    }

    private void applySort(Sort sort) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isSorted(sort)) {
            Iterator<Sort.Order> iterator = sort.iterator();
            if (iterator.hasNext()) {
                stringBuilder.append(" ORDER BY ");
            }
            Iterable<String> orders = Iterables.transform(sort, new Function<Sort.Order, String>() {
                @Override
                public String apply(Sort.Order s) {
                    return s.getProperty() + " " + s.getDirection();
                }
            });
            stringBuilder.append(Joiner.on(", ").join(orders));
        }
        query = query + stringBuilder;
    }

    public QueryBuilder page(Pageable pageable) {
        applyPaging(pageable);
        return this;
    }

    private void applyPaging(Pageable pageable) {
        if(!isPaged(pageable)){
            return;
        }
        String rowNum = query.isEmpty() ? "ROWNUM(%s, %s)" : " AND ROWNUM(%s, %s)";
        query = query + format(rowNum, pageable.getOffset() + 1, pageable.getOffset() + pageable.getPageSize());
        logger.info("applyPaging({})",query);
    }

    public static class AtomicPredicate {

        private final Part part;

        /**
         * Creates a new {@link AtomicPredicate}.
         *
         * @param part must not be {@literal null}.
         */
        public AtomicPredicate(Part part) {
            notNull(part,"[Assertion failed] - part argument is required; it must not be null");
            this.part = part;
        }

        @Override
        public String toString() {
            Part.Type type = part.getType();

            return format("%s %s",
                    part.getProperty().toDotPath(), toClause(type));
        }

        private String toClause(Part.Type type) {
            switch (type) {
                case FALSE:
                case TRUE:
                    return format("%1$s %2$s", getOperator(type), Part.Type.TRUE.equals(type));
                case IS_NULL:
                case IS_NOT_NULL:
                    return format("%s NULL", getOperator(type));
                case BETWEEN:
                    return format("%s ? and ?", getOperator(type));
                default:
                    //return String.format("%s $%s", getOperator(type), value.next());
                    return format("%s ?", getOperator(type));
            }
        }

        /**
         * Maps the given {@link org.springframework.data.repository.query.parser.Part.Type} to an OQL operator.
         *
         * @param type
         * @return
         */
        private String getOperator(Part.Type type) {
            switch (type) {
                case IN:
                    return "in";
                case NOT_IN:
                    return "not in";
                case AFTER:
                case GREATER_THAN:
                    return ">";
                case GREATER_THAN_EQUAL:
                    return ">=";
                case BEFORE:
                case LESS_THAN:
                    return "<";
                case LESS_THAN_EQUAL:
                    return "<=";
                case IS_NOT_NULL:
                    return "is not";
                case NEGATING_SIMPLE_PROPERTY:
                    return "!=";
                case LIKE:
                case STARTING_WITH:
                case ENDING_WITH:
                case CONTAINING:
                    return "like";
                case NOT_LIKE:
                    return "not like";
                case IS_NULL:
                    return "is";
                case FALSE:
                case SIMPLE_PROPERTY:
                case TRUE:
                    return "=";
                case BETWEEN:
                    return "between";
                case REGEX:
                    return "rlike";
                default:
                    throw new UnsupportedOperationException(format("Unsupported operator %s!", type));
            }
        }
    }


}
