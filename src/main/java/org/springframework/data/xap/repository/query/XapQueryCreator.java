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
public class XapQueryCreator extends AbstractQueryCreator{

    public XapQueryCreator(PartTree tree, ParameterAccessor parameters) {
        super(tree, parameters);
    }

    public XapQueryCreator(PartTree tree) {
        super(tree);
    }

    @Override
    protected Object create(Part part, Iterator iterator) {
        //TODO
        return null;
    }

    @Override
    protected Object and(Part part, Object base, Iterator iterator) {
        //TODO
        return null;
    }

    @Override
    protected Object or(Object base, Object criteria) {
        //TODO
        return null;
    }

    @Override
    protected Object complete(Object criteria, Sort sort) {
        //TODO
        return null;
    }
}
