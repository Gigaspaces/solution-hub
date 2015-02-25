package org.springframework.data.xap.repository;

import com.gigaspaces.document.SpaceDocument;

@SpaceDocumentRepository(
        typeName = "Product",
        id = "CatalogNumber",
        routing = "Category"
)
public interface ProductDocumentRepository extends XapDocumentRepository<SpaceDocument, String> {
}
