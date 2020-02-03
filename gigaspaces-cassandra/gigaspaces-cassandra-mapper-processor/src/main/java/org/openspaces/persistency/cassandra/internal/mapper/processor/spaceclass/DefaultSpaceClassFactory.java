package org.openspaces.persistency.cassandra.internal.mapper.processor.spaceclass;

import com.datastax.oss.driver.internal.mapper.processor.ProcessorContext;
import com.datastax.oss.driver.internal.mapper.processor.entity.DefaultEntityFactory;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.lang.annotation.Annotation;
import java.util.Set;

public class DefaultSpaceClassFactory extends DefaultEntityFactory {
    // property annotations of which only 1 is allowed on a property
    private static final Set<Class<? extends Annotation>> EXCLUSIVE_PROPERTY_ANNOTATIONS =
            ImmutableSet.of( SpaceRouting.class, SpaceProperty.class);
    public DefaultSpaceClassFactory(ProcessorContext context) {
        super(context);
    }
}
