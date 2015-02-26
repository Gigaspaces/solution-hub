package org.springframework.data.xap.repository.support;


import com.gigaspaces.client.ReadByIdsResult;
import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.repository.XapDocumentRepository;
import org.springframework.data.xap.repository.query.Projection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.*;


public class XapDocumentRepositoryImpl<T extends SpaceDocument, ID extends Serializable> implements XapDocumentRepository<T, ID>{


    private GigaSpace space;
    private EntityInformation<T, ID> entityInformation;
    private String typeName;

    public XapDocumentRepositoryImpl(GigaSpace space, EntityInformation<T, ID> entityInformation, SpaceTypeDescriptor typeDescriptor) {
        this.space = space;
        this.entityInformation = entityInformation;
        this.typeName = typeDescriptor.getTypeName();
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
        space.writeMultiple(toArray(entities));
        return entities;
    }

    @Override
    public T findOne(ID id) {
        return space.readById(new IdQuery<T>(typeName, id));
    }

    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        T[] ts = space.readMultiple(new SQLQuery<T>(typeName, ""));
        return newArrayList(ts);
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return findAllByIdsInternal(ids, null);
    }

    @Override
    public long count() {
        SQLQuery<T> query = new SQLQuery<>(typeName, "");
        return space.aggregate(query, new AggregationSet().count("")).getLong(0);
    }

    @Override
    public void delete(ID id) {
        IdQuery<T> idQuery = new IdQuery<T>(typeName, id).setProjections("");
        space.takeById(idQuery);
    }

    @Override
    public void delete(T entity) {
        space.take(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        List<ID> idList = new ArrayList<>();
        for (T entity : entities) {
            ID id = entityInformation.getId(entity);
            idList.add(id);
        }
        Object[] idArray = idList.toArray();
        Class<T> aClass = entityInformation.getJavaType();
        IdsQuery<T> idsQuery = new IdsQuery<T>(aClass, idArray).setProjections("");
        space.takeByIds(idsQuery);
    }

    @Override
    public void deleteAll() {
        SQLQuery<Object> query = new SQLQuery<>(typeName, "").setProjections("");
        space.takeMultiple(query);
    }

    //TODO extract duplicated methods
    private <E> E[] toArray(Iterable<E> elems) {
        ArrayList<E> arrayList = new ArrayList<E>();
        for (E elem : elems) {
            arrayList.add(elem);
        }
        return (E[]) arrayList.toArray();
    }

    private Iterable<T> findAllByIdsInternal(Iterable<ID> ids, Projection projection) {
        IdsQuery<T> query = new IdsQuery<>(typeName, toArray(ids));

        if (projection != null) {
            query.setProjections(projection.getProperties());
        }

        return space.readByIds(query);
    }
}
