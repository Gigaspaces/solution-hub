package org.springframework.data.xap.repository.query;

import org.springframework.data.repository.query.parser.Part;
import org.springframework.util.Assert;

public class QueryBuilder {

    private String query;

    public QueryBuilder(Part part){
        query = new AtomicPredicate(part).toString();
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
				/*
				NOTE unfortunately, 'NOT LIKE' operator is not supported by GemFire's Query/OQL syntax
				case NOT_LIKE:
					return "NOT LIKE";
				*/
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
