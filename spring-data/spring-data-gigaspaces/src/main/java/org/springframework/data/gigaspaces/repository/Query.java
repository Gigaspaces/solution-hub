package org.springframework.data.gigaspaces.repository;

import java.lang.annotation.*;

/**
 * <p>Defines SQL-like Gigaspaces query. Overrides query derivation from method name.</p>
 * <p>Use this annotation on your repository methods to manually set the SQL query string.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * &#64;Query("meetingRoom.name = ?")
 * List<SpaceDocument> findByMeetingRoom(String name); will ignore method name and use a query defined in annotation
 * </pre></blockquote>
 *
 * @author Anna_Babich
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {

    String value() default "";

}