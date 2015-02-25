package org.springframework.data.xap.repository;

import java.lang.annotation.*;

/**
 * Defines SpaceDocument properties for repository class
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpaceDocumentRepository {

    public String typeName() default "";

    public String id() default "";

    public String routing() default "";

    public String[] indexes() default {};

    public String[] extendedIndexes() default {};

}
