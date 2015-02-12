package org.springframework.data.xap.repository.query;

import org.springframework.data.repository.query.parser.Part;
import org.springframework.util.Assert;

import java.util.Iterator;


public class Predicates implements Predicate{

    private final Predicate current;

    /**
     * Creates a new {@link Predicates} wrapper instance.
     *
     * @param predicate must not be {@literal null}.
     */
    private Predicates(Predicate predicate) {
        this.current = predicate;
    }

    private static Predicates create(Predicate predicate) {
        return new Predicates(predicate);
    }

    /**
     * Creates a new Predicate for the given {@link org.springframework.data.repository.query.parser.Part} and index iterator.
     *
     * @param part must not be {@literal null}.
     * @param value must not be {@literal null}.
     * @return
     */
    public static Predicates create(Part part, Iterator<Integer> value) {
        return create(new AtomicPredicate(part, value));
    }

    /**
     * And-concatenates the given {@link Predicate} to the current one.
     *
     * @param predicate must not be {@literal null}.
     * @return
     */
    public Predicates and(final Predicate predicate) {

        return create(new Predicate() {
            @Override
            public String toString() {
                return String.format("%s AND %s", Predicates.this.current.toString(), predicate.toString());
            }
        });
    }

    /**
     * Or-concatenates the given {@link Predicate} to the current one.
     *
     * @param predicate must not be {@literal null}.
     * @return
     */
    public Predicates or(final Predicate predicate) {

        return create(new Predicate() {
            @Override
            public String toString() {
                return String.format("%s OR %s", Predicates.this.current.toString(), predicate.toString());
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.gemfire.repository.query.Predicate#toString(java.lang.String)
     */
    @Override
    public String toString() {
        return current.toString();
    }

    /**
     * Predicate to create a predicate expression for a {@link Part}.
     *
     * @author Oliver Gierke
     */
    public static class AtomicPredicate implements Predicate {

        private final Part part;
        private final Iterator<Integer> value;

        /**
         * Creates a new {@link AtomicPredicate}.
         *
         * @param part must not be {@literal null}.
         * @param value must not be {@literal null}.
         */
        public AtomicPredicate(Part part, Iterator<Integer> value) {

            Assert.notNull(part);
            Assert.notNull(value);

            this.part = part;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.gemfire.repository.query.Predicate#toString(java.lang.String)
         */
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
