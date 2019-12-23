package org.springframework.data.gigaspaces.querydsl;

import com.gigaspaces.query.ISpaceQuery;
import com.google.common.collect.ImmutableList;
import com.j_spaces.core.client.SQLQuery;
import com.querydsl.core.support.SerializerBase;
import com.querydsl.core.types.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.repository.query.Projection;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import static org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils.*;
/**
 * @author Leonid_Poliakov
 */
public class GigaspacesQueryDslConverter<T> extends SerializerBase<GigaspacesQueryDslConverter<T>> implements Visitor<Void, Void> {
    private static final GigaspacesQueryDslTemplates TEMPLATES = GigaspacesQueryDslTemplates.DEFAULT;
    private static final String LISTS_SEPARATOR = ", ";
    private static final String PAGING_ROW_NUM = "rownum(%s, %s)";
    private static final String AND_STATEMENT = " and ";
    private static final String ORDER_BY = " order by ";
    private static final String ORDER_ASC = " asc";
    private static final String ORDER_DESC = " desc";

    private Class<T> type;
    private List<Object> parameters;

    public GigaspacesQueryDslConverter(Class<T> type) {
        super(TEMPLATES);
        this.type = type;
        this.parameters = new LinkedList<>();
    }

    @Nullable
    @Override
    public Void visit(FactoryExpression<?> factoryExpression, @Nullable Void context) {
        super.visit(factoryExpression, context);
        return null;
    }

    @Nullable
    @Override
    public Void visit(Operation<?> operation, @Nullable Void context) {
        Operator operator = operation.getOperator();
        List<Expression<?>> arguments = operation.getArgs();
        // try to redefine operation
        if (operator == Ops.NOT) {
            if (arguments != null && arguments.size() == 1) {
                Expression<?> innerExpression = arguments.get(0);
                if (innerExpression instanceof Operation) {
                    Operation innerOperation = (Operation) innerExpression;
                    Operator negative = TEMPLATES.getNegative(innerOperation.getOperator());
                    if (negative != null) {
                        operator = negative;
                        operation = innerOperation;
                        arguments = operation.getArgs();
                    }
                }
            }
        }

        // check if operation is allowed
        if (!TEMPLATES.isAllowed(operator)) {
            throw new UnsupportedOperationException("operator " + operator + " is not supported by Gigaspaces Repositories");
        }

        // check if operator is regex-based
        if (TEMPLATES.isRegex(operator)) {
            if (operator == Ops.STRING_CONTAINS || operator == Ops.STRING_CONTAINS_IC) {
                arguments = replaceWithRegex(".*%s.*", operation, operator == Ops.STRING_CONTAINS_IC);
            } else if (operator == Ops.STARTS_WITH || operator == Ops.STARTS_WITH_IC) {
                arguments = replaceWithRegex("^%s.*", operation, operator == Ops.STARTS_WITH_IC);
            } else if (operator == Ops.ENDS_WITH || operator == Ops.ENDS_WITH_IC) {
                arguments = replaceWithRegex(".*%s$", operation, operator == Ops.ENDS_WITH_IC);
            } else {
                throw new UnsupportedOperationException("operator " + operator + " is not supported by Gigaspaces Repositories");
            }
        }

        // apply operation
        visitOperation(operation.getType(), operator, arguments);
        return null;
    }

    private List<Expression<?>> replaceWithRegex(String regexFormat, Operation<?> operation, boolean ignoreCase) {
        Expression<?> search = operation.getArg(1);
        String searchString = search.toString();
        String regex = String.format(regexFormat, Pattern.quote(searchString));
        if (ignoreCase) {
            regex = "(?i)" + regex;
        }

        List<Expression<?>> arguments = new LinkedList<>();
        for (Expression<?> expression : operation.getArgs()) {
            arguments.add(expression);
        }

        Expression<String> regexArgument = ConstantImpl.create(regex);
        arguments.set(1, regexArgument);
        return arguments;
    }

    @Nullable
    @Override
    public Void visit(ParamExpression<?> paramExpression, @Nullable Void context) {
        super.visit(paramExpression, context);
        return null;
    }

    @Nullable
    @Override
    public Void visit(Path<?> path, @Nullable Void context) {
        final PathType pathType = path.getMetadata().getPathType();
        final Template template = TEMPLATES.getTemplate(pathType);

        if (template != null && path.getType() == String.class) {
            String fullPath = path.toString();
            String root = path.getRoot().toString();
            handleTemplate(template, ImmutableList.of(root, fullPath.substring(root.length() + 1)));
        } else {
            super.visit(path, context);
        }

        return null;
    }

    @Nullable
    @Override
    public Void visit(SubQueryExpression<?> subQueryExpression, @Nullable Void context) {
        return null;
    }

    @Nullable
    @Override
    public Void visit(TemplateExpression<?> templateExpression, @Nullable Void context) {
        super.visit(templateExpression, context);
        return null;
    }

    @Override
    public void visitConstant(Object constant) {
        if (constant instanceof Collection) {
            Collection collection = (Collection) constant;
            String prefix = "";
            for (Object element : collection) {
                append(prefix);
                visitConstant(element);
                prefix = LISTS_SEPARATOR;
            }
        } else {
            append("?");
            parameters.add(constant);
        }
    }

    public ISpaceQuery<T> convert(Predicate predicate, Pageable pageable, Projection projection, OrderSpecifier<?>... orders) {
        // append where clause
        if (predicate != null) {
            handle(predicate);
        }

        if (isPaged(pageable)) {
            // append row num and order clause
            if (getLength() > 0) {
                append(AND_STATEMENT);
            }
            append(String.format(PAGING_ROW_NUM, pageable.getOffset() + 1, pageable.getOffset() + pageable.getPageSize()));
            if (isSorted(pageable.getSort())) {
                append(ORDER_BY);
                String prefix = "";
                for (Sort.Order order : pageable.getSort()) {
                    append(prefix);
                    prefix = LISTS_SEPARATOR;

                    append(order.getProperty());
                    append(order.getDirection() == Sort.Direction.ASC ? ORDER_ASC : ORDER_DESC);
                }
            }
        } else if (orders != null && orders.length > 0) {
            // append order clause
            append(ORDER_BY);
            String prefix = "";
            for (final OrderSpecifier<?> order : orders) {
                append(prefix);
                prefix = LISTS_SEPARATOR;

                handle(order.getTarget());
                append(order.getOrder() == Order.ASC ? ORDER_ASC : ORDER_DESC);
            }
        }

        SQLQuery<T> query = new SQLQuery<>(type, toString());
        query.setParameters(parameters.toArray());

        if (projection != null) {
            query.setProjections(projection.getProperties());
        }

        return query;
    }
}