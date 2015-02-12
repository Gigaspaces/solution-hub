package org.springframework.data.xap.repository.query;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.ParameterAccessor;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;

import java.util.Iterator;

/**
 * @author Anna_Babich.
 */
public class XapQueryCreator extends AbstractQueryCreator<String, Predicates>{

    public XapQueryCreator(PartTree tree, ParameterAccessor parameters) {
        super(tree, parameters);
    }

    public XapQueryCreator(PartTree tree) {
        super(tree);
    }

    @Override
    protected Predicates create(Part part, Iterator iterator) {
        return Predicates.create(part, iterator);
    }

    @Override
    protected Predicates and(Part part, Predicates base, Iterator iterator) {
        return base.and(Predicates.create(part, iterator));
    }

    @Override
    protected Predicates or(Predicates base, Predicates criteria) {
        return base.or(criteria);
    }

    @Override
    protected String complete(Predicates criteria, Sort sort) {
        return criteria.toString();
    }
}
