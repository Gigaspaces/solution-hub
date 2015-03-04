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
 * <p>Looks for classes annotated with {@code SpaceClass} and generates {@code Q...} classes for you to use Querydsl syntax.</p>
 * <p>Use this in conjunction with Maven APT plugin to enable Querydsl for XAP Repositories.</p>
 *
 * @author Leonid_Poliakov
 * @see com.gigaspaces.annotation.pojo.SpaceClass
 * @see org.springframework.data.xap.querydsl.XapQueryDslPredicateExecutor
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