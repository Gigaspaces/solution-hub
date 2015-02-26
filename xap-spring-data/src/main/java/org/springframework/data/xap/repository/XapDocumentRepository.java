package org.springframework.data.xap.repository;

import com.gigaspaces.document.SpaceDocument;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface XapDocumentRepository<T extends SpaceDocument, ID extends Serializable> extends XapRepository<T,ID> {
}