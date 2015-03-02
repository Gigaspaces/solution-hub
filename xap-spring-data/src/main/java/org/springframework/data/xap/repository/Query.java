package org.springframework.data.xap.repository;

import java.lang.annotation.*;

/**
 * Defines SQL-like XAP query
 *
 * @author Anna_Babich
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {

    String value() default "";

}