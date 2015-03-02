package org.springframework.data.xap.repository.support;

import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.gigaspaces.query.aggregators.AggregationSet;
import com.j_spaces.core.client.SQLQuery;
import net.jini.core.lease.Lease;
import org.openspaces.core.GigaSpace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.repository.XapRepository;
import org.springframework.data.xap.repository.query.Projection;
import org.springframework.data.xap.repository.query.QueryBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public class SimpleXapRepository<T, ID extends Serializable> implements XapRepository<T, ID> {
    protected GigaSpace space;
    protected EntityInformation<T, ID> entityInformation;

    public SimpleXapRepository(GigaSpace space, EntityInformation<T, ID> entityInformation) {
        this.space = space;
        this.entityInformation = entityInformation;
    }

    @Override
    public GigaSpace space() {
        return space;
    }

    @Override
    public <S extends T> S save(S entity) {
        save(entity, Lease.FOREVER);
        return entity;
    }

    @Override
    public <S extends T> S save(S entity, long lease) {
        space.write(entity, lease);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        save(entities, Lease.FOREVER);
        return entities;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities, long lease) {
        space.writeMultiple(toArray(entities), lease);
        return entities;
    }

    @Override
    public T take(ID id) {
        Class<T> aClass = entityInformation.getJavaType();
        return space.takeById(aClass, id);
    }

    @Override
    public Iterable<T> take(Iterable<ID> ids) {
        Class<T> aClass = entityInformation.getJavaType();
        return space.takeByIds(aClass, toArray(ids));
    }

    @Override
    public T findOne(ID id) {
        return findOneInternal(id, null);
    }

    @Override
    public T findOne(ID id, Projection projection) {
        return findOneInternal(id, projection);
    }

    private T findOneInternal(ID id, Projection projection) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        IdQuery<T> idQuery = idQuery(id);

        if (projection != null) {
            idQuery.setProjections(projection.getProperties());
        }

        return space.read(idQuery);
    }

    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        return findAllSortedInternal(null, null, null);
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return findAllByIdsInternal(ids, null);
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids, Projection projection) {
        return findAllByIdsInternal(ids, projection);
    }

    private Iterable<T> findAllByIdsInternal(Iterable<ID> ids, Projection projection) {
        IdsQuery<T> query = idsQuery(toArray(ids));
        if (projection != null) {
            query.setProjections(projection.getProperties());
        }
        return space.readByIds(query);
    }

    @Override
    public Iterable<T> findAll(Projection projection) {
        return findAllSortedInternal(projection, null, null);
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return findAllSortedInternal(null, sort, null);
    }

    @Override
    public Iterable<T> findAll(Sort sort, Projection projection) {
        return findAllSortedInternal(projection, sort, null);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return new PageImpl<>(findAllSortedInternal(null, null, pageable));
    }

    @Override
    public Page<T> findAll(Pageable pageable, Projection projection) {
        return new PageImpl<>(findAllSortedInternal(projection, null, pageable));
    }

    private List<T> findAllSortedInternal(Projection projection, Sort sort, Pageable pageable) {
        StringBuilder stringBuilder = new StringBuilder("");

        // paging or sorting
        if (pageable != null) {
            stringBuilder.append(new QueryBuilder(pageable).buildQuery());
        } else if (sort != null) {
            stringBuilder.append(new QueryBuilder(sort).buildQuery());
        }

        SQLQuery<T> query = sqlQuery(stringBuilder.toString());

        // projection
        if (projection != null) {
            query.setProjections(projection.getProperties());
        }

        T[] found = space.readMultiple(query);
        return new ArrayList<>(Arrays.asList(found));
    }

    @Override
    public long count() {
        SQLQuery<T> query = sqlQuery("");
        return space.aggregate(query, new AggregationSet().count("")).getLong(0);
    }

    @Override
    public void delete(ID id) {
        IdQuery<T> idQuery = idQuery(id).setProjections("");
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
        IdsQuery<T> idsQuery = idsQuery(idList.toArray()).setProjections("");
        space.takeByIds(idsQuery);
    }

    @Override
    public void deleteAll() {
        // TODO: consider replacing with distributed task to not return deleted entities back on client
        SQLQuery<T> query = sqlQuery("").setProjections("");
        space.takeMultiple(query);
    }

    @SuppressWarnings("unchecked")
    protected <E> E[] toArray(Iterable<E> elements) {
        List<E> arrayList = new LinkedList<>();
        for (E element : elements) {
            arrayList.add(element);
        }
        return (E[]) arrayList.toArray();
    }

    protected IdQuery<T> idQuery(ID id) {
        return new IdQuery<>(entityInformation.getJavaType(), id);
    }

    protected IdsQuery<T> idsQuery(Object[] ids) {
        return new IdsQuery<>(entityInformation.getJavaType(), ids);
    }

    protected SQLQuery<T> sqlQuery(String query) {
        return new SQLQuery<>(entityInformation.getJavaType(), query);
    }
}
