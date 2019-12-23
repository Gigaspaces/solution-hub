package org.springframework.data.gigaspaces.repository.support;

import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.data.gigaspaces.repository.GigaspacesDocumentRepository;
import org.springframework.data.repository.core.EntityInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GigaspacesDocumentRepositoryImpl<T extends SpaceDocument, ID extends Serializable> extends SimpleGigaspacesRepository<T, ID> implements GigaspacesDocumentRepository<T, ID> {
    private String typeName;

    public GigaspacesDocumentRepositoryImpl(GigaSpace space, EntityInformation<T, ID> entityInformation, String typeName) {
        super(space, entityInformation);
        this.typeName = typeName;
        SpaceTypeDescriptor typeDescriptor = space.getTypeManager().getTypeDescriptor(typeName);
        if (typeDescriptor == null) {
            throw new IllegalStateException("Type descriptor is not registered for SpaceDocuments with type: " + typeName);
        }
        Class<T> domainClass = entityInformation.getJavaType();
        if (SpaceDocument.class.isAssignableFrom(domainClass) && domainClass != SpaceDocument.class) {
            Class<? extends SpaceDocument> wrapperClass = typeDescriptor.getDocumentWrapperClass();
            if (wrapperClass != domainClass) {
                throw new IllegalStateException("Domain class must be the same as wrapper class in SpaceDocument type descriptor; expected: " + domainClass + ", found: " + wrapperClass + ", type name: " + typeName);
            }
        }
    }

    @Override
    protected IdQuery<T> idQuery(ID id) {
        return new IdQuery<>(typeName, id);
    }

    @Override
    protected IdsQuery<T> idsQuery(Object[] ids) {
        return new IdsQuery<>(typeName, ids);
    }

    @Override
    protected SQLQuery<T> sqlQuery(String query) {
        return new SQLQuery<>(typeName, query);
    }

    @Override
    public T takeOne(ID id) {
        return space.takeById(idQuery(id));
    }

    @Override
    public Iterable<T> takeAll(Iterable<ID> ids) {
        return space.takeByIds(idsQuery(toArray(ids)));
    }

    @Override
    public Iterable<T> takeAll() {
        return new ArrayList<T>(Arrays.asList(space.takeMultiple((sqlQuery("")))));
    }
}