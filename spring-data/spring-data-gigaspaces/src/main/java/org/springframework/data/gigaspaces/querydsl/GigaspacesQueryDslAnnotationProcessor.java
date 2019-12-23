package org.springframework.data.gigaspaces.querydsl;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceExclude;
import com.querydsl.apt.Configuration;
import com.querydsl.apt.DefaultConfiguration;
import com.querydsl.apt.QuerydslAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import java.lang.annotation.Annotation;
import java.util.Collections;

/**
 * <p>Looks for classes annotated with {@code SpaceClass} and generates {@code Q...} classes for you to use Querydsl syntax.</p>
 * <p>Use this in conjunction with Maven APT plugin to enable Querydsl for Gigaspaces Repositories.</p>
 *
 * @author Leonid_Poliakov
 * @see com.gigaspaces.annotation.pojo.SpaceClass
 * @see GigaspacesQueryDslPredicateExecutor
 */
@SupportedAnnotationTypes({"com.gigaspaces.annotation.pojo.SpaceClass"})
public class GigaspacesQueryDslAnnotationProcessor extends QuerydslAnnotationProcessor {
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