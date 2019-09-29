package org.springframework.data.xap.repository;

import java.lang.annotation.*;

/**
 * <p>Defines {@link com.gigaspaces.document.SpaceDocument} type name when using {@link org.springframework.data.xap.repository.XapDocumentRepository}.</p>
 * <p>Use this annotation on your document repository interface to define the type name.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * &#64;SpaceDocumentName("Meeting")
 * public interface MeetingDocumentRepository extends XapDocumentRepository&lt;SpaceDocument, Integer&gt; {...}
 * </pre></blockquote>
 *
 * @see org.springframework.data.xap.repository.XapDocumentRepository
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpaceDocumentName {
    String value();
}