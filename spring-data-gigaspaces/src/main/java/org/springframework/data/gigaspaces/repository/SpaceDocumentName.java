package org.springframework.data.gigaspaces.repository;

import java.lang.annotation.*;

/**
 * <p>Defines {@link com.gigaspaces.document.SpaceDocument} type name when using {@link GigaspacesDocumentRepository}.</p>
 * <p>Use this annotation on your document repository interface to define the type name.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * &#64;SpaceDocumentName("Meeting")
 * public interface MeetingDocumentRepository extends GigaspacesDocumentRepository&lt;SpaceDocument, Integer&gt; {...}
 * </pre></blockquote>
 *
 * @see GigaspacesDocumentRepository
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpaceDocumentName {
    String value();
}