package com.gigaspaces.spring.data.repository.support;

import com.gigaspaces.client.ReadByIdsResult;
import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.gigaspaces.spring.data.repository.XapRepository;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.extensions.QueryExtension;
import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public class SimpleXapRepository<T, ID extends Serializable> implements XapRepository<T, ID> {

    private GigaSpace space;
    private EntityInformation<T, ID> entityInformation;

    public SimpleXapRepository(GigaSpace space, EntityInformation<T, ID> entityInformation) {
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public <S extends T> S save(S entity) {
        space.write(entity);
        // TODO: think about auto-generated id
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        space.writeMultiple(toArray(entities));
        // TODO: think about auto-generated id
        return entities;
    }

    @Override
    public T findOne(ID id) {
        Class<T> aClass = entityInformation.getJavaType();
        return space.readById(aClass, id);
    }

    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        Class<T> aClass = entityInformation.getJavaType();
        SQLQuery<T> query = new SQLQuery<>(aClass, "");
        T[] found = space.readMultiple(query);
        return new ArrayList<>(Arrays.asList(found));
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        Class<T> aClass = entityInformation.getJavaType();
        return space.readByIds(aClass, toArray(ids));
    }

    @Override
    public long count() {
        Class<T> aClass = entityInformation.getJavaType();
        SQLQuery<T> query = new SQLQuery<>(aClass, "");
        return QueryExtension.count(space, query, "");
    }

    @Override
    public void delete(ID id) {
        Class<T> aClass = entityInformation.getJavaType();
        IdQuery<T> idQuery = new IdQuery<T>(aClass, id).setProjections("");
        space.takeById(idQuery);
    }

    @Override
    public void delete(T entity) {
        delete(entityInformation.getId(entity));
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        // TODO: consider replacing with distributed task to not return deleted entities back on client
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
        // TODO: consider replacing with distributed task to not return deleted entities back on client
        Class<T> aClass = entityInformation.getJavaType();
        SQLQuery<T> query = new SQLQuery<>(aClass, "").setProjections("");
        space.takeMultiple(query);
    }

    @SuppressWarnings("unchecked")
    private <E> E[] toArray(Iterable<E> elems) {
        ArrayList<E> arrayList = new ArrayList<E>();
        for (E elem : elems) {
            arrayList.add(elem);
        }
        return (E[]) arrayList.toArray();
    }
}
