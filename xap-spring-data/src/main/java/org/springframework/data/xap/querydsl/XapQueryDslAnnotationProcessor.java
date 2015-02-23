package org.springframework.data.xap.querydsl;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.mysema.query.apt.AbstractQuerydslProcessor;
import com.mysema.query.apt.Configuration;
import com.mysema.query.apt.DefaultConfiguration;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.Annotation;
import java.util.Collections;

/**
 * @author Leonid_Poliakov
 */
@SupportedAnnotationTypes({"com.gigaspaces.annotation.pojo.SpaceClass"})
public class XapQueryDslAnnotationProcessor extends AbstractQuerydslProcessor {
    private static final Class<? extends Annotation> ENTITIES = null;
    private static final Class<? extends Annotation> ENTITY = SpaceClass.class;
    private static final Class<? extends Annotation> SUPER_TYPE = null;
    private static final Class<? extends Annotation> EMBEDDABLE = null;
    private static final Class<? extends Annotation> EMBEDDED = null;
    private static final Class<? extends Annotation> SKIP = SpaceExclude.class;

    @Override
    protected Configuration createConfiguration(RoundEnvironment roundEnv) {
        return new DefaultConfiguration(roundEnv, processingEnv.getOptions(), Collections.<String>emptySet(), ENTITIES, ENTITY, SUPER_TYPE, EMBEDDABLE, EMBEDDED, SKIP);
    }
}