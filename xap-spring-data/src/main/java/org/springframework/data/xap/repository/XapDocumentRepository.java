package org.springframework.data.xap.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface XapDocumentRepository<T, ID extends Serializable> extends XapRepository<T,ID> {
}