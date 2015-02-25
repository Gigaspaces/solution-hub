package org.springframework.data.xap.repository.support;


import com.gigaspaces.metadata.SpaceTypeDescriptor;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.repository.XapDocumentRepository;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;


public class XapDocumentRepositoryImpl<T, ID extends Serializable> implements XapDocumentRepository<T, ID>{


    private GigaSpace space;
    private EntityInformation<T, ID> entityInformation;

    public XapDocumentRepositoryImpl(GigaSpace space, EntityInformation<T, ID> entityInformation, SpaceTypeDescriptor typeDescriptor) {
        this.space = space;
        this.entityInformation = entityInformation;
        space.getTypeManager().registerTypeDescriptor(typeDescriptor);
    }


    @Override
    public GigaSpace space() {
        return null;
    }

    @Override
    public T findOne(ID id, Projection projection) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Projection projection) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids, Projection projection) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Sort sort, Projection projection) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable, Projection projection) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> S save(S entity) {
        space.write(entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void delete(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
