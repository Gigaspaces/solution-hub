package org.openspaces.persistency.cassandra.internal.mapper.processor.spaceclass;

import com.datastax.oss.driver.internal.mapper.processor.entity.CqlNameGenerator;
import com.datastax.oss.driver.internal.mapper.processor.entity.DefaultEntityDefinition;
import com.datastax.oss.driver.internal.mapper.processor.entity.PropertyDefinition;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableSet;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.squareup.javapoet.ClassName;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultSpaceClassDefinition extends DefaultEntityDefinition{
    // SpaceId
    public DefaultSpaceClassDefinition(ClassName className, String javaName, String defaultKeyspace, Optional<String> customCqlName, List<PropertyDefinition> partitionKey, List<PropertyDefinition> clusteringColumns, List<PropertyDefinition> regularColumns, List<PropertyDefinition> computedValues, CqlNameGenerator cqlNameGenerator) {
        super(className, javaName, defaultKeyspace, customCqlName, partitionKey, clusteringColumns, regularColumns, computedValues, cqlNameGenerator);
    }
}
