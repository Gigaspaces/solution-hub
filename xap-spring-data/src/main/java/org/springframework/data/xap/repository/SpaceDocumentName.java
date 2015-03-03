package org.springframework.data.xap.repository;

import java.lang.annotation.*;

/**
 * Defines SpaceDocument name in the storage
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpaceDocumentName {
    String value();
}