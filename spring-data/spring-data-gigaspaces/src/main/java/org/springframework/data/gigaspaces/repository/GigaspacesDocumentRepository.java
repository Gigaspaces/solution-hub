package org.springframework.data.gigaspaces.repository;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * <p>Declares a repository to store and process documents instead on entities with fixed schema. A document, which is
 * represented by the class {@link SpaceDocument}, is a collection of key-value pairs, where the keys are strings and
 * the values are primitives, `String`, `Date`, other documents, or collections thereof. Most importantly, the Space is
 * aware of the internal structure of a document, and thus can index document properties at any nesting level and expose
 * rich query semantics for retrieving documents.</p>
 * <p>Use this interface as a base for you document stores.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * &#64;SpaceDocumentName("Meeting")
 * public interface MeetingDocumentRepository extends GigaspacesDocumentRepository&lt;SpaceDocument, Integer&gt; {...}
 * </pre></blockquote>
 *
 * @param <T>  the type of entity to be stored in repository
 * @param <ID> the type of entity id field
 * @see GigaspacesRepository
 */
@NoRepositoryBean
public interface GigaspacesDocumentRepository<T extends SpaceDocument, ID extends Serializable> extends GigaspacesRepository<T, ID> {
}