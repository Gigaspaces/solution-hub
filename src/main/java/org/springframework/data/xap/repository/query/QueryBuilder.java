package org.springframework.data.xap.repository.query;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.util.Assert;

import java.util.Iterator;

public class QueryBuilder {

    private String query = new String();

    public QueryBuilder(Part part){
        query = new AtomicPredicate(part).toString();
    }

    public QueryBuilder(Sort sort){
        applySort(sort);
    }

    public String buildQuery(){
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

    public QueryBuilder sort(Sort sort){
        applySort(sort);
        return this;
    }

    private void applySort(Sort sort) {
        StringBuilder stringBuilder = new StringBuilder();
        if (sort != null){
            Iterator<Sort.Order> iterator = sort.iterator();
            if (iterator.hasNext()){
                stringBuilder.append("ORDER BY ");
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

    public static class AtomicPredicate {

        private final Part part;

        /**
         * Creates a new {@link AtomicPredicate}.
         *
         * @param part must not be {@literal null}.
         */
        public AtomicPredicate(Part part) {
            Assert.notNull(part);
            this.part = part;
        }

        @Override
        public String toString() {
            Part.Type type = part.getType();

            return String.format("%s %s",
                    part.getProperty().toDotPath(), toClause(type));
        }

        private String toClause(Part.Type type) {
            switch (type) {
                case FALSE:
                case TRUE:
                    return String.format("%1$s %2$s", getOperator(type), Part.Type.TRUE.equals(type));
                case IS_NULL:
                case IS_NOT_NULL:
                    return String.format("%s NULL", getOperator(type));
                default:
                    //return String.format("%s $%s", getOperator(type), value.next());
                    return String.format("%s ?", getOperator(type));
            }
        }

        /**
         * Maps the given {@link org.springframework.data.repository.query.parser.Part.Type} to an OQL operator.
         *
         * @param type
         * @return
         */
        private String getOperator(Part.Type type) {
            //TODO check all operators
            switch (type) {
                case IN:
                    return "IN SET";
                case NOT_IN:
                    return "NOT IN SET";
                case GREATER_THAN:
                    return ">";
                case GREATER_THAN_EQUAL:
                    return ">=";
                case LESS_THAN:
                    return "<";
                case LESS_THAN_EQUAL:
                    return "<=";
                case IS_NOT_NULL:
                case NEGATING_SIMPLE_PROPERTY:
                    return "!=";
                case LIKE:
                case STARTING_WITH:
                case ENDING_WITH:
                case CONTAINING:
                    return "LIKE";
                case FALSE:
                case IS_NULL:
                case SIMPLE_PROPERTY:
                case TRUE:
                    return "=";
                default:
                    throw new IllegalArgumentException(String.format("Unsupported operator %s!", type));
            }
        }
    }


}
