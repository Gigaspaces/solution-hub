package org.springframework.data.xap.repository.support;

import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.query.IdQuery;
import com.gigaspaces.query.IdsQuery;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.xap.repository.XapDocumentRepository;

import java.io.Serializable;

public class XapDocumentRepositoryImpl<T extends SpaceDocument, ID extends Serializable> extends SimpleXapRepository<T, ID> implements XapDocumentRepository<T, ID> {
    private String typeName;

    public XapDocumentRepositoryImpl(GigaSpace space, EntityInformation<T, ID> entityInformation, SpaceTypeDescriptor typeDescriptor) {
        super(space, entityInformation);
        this.typeName = typeDescriptor.getTypeName();
        space.getTypeManager().registerTypeDescriptor(typeDescriptor);
    }

    @Override
    protected IdQuery<T> idQuery(ID id) {
        return new IdQuery<>(typeName, id);
    }

    @Override
    protected IdsQuery<T> idsQuery(ID[] ids) {
        return new IdsQuery<>(typeName, ids);
    }

    @Override
    protected SQLQuery<T> sqlQuery(String query) {
        return new SQLQuery<>(typeName, query);
    }

    @Override
    public T take(ID id) {
        return space.takeById(idQuery(id));
    }

    @Override
    public Iterable<T> take(Iterable<ID> ids) {
        return space.takeByIds(idsQuery(toArray(ids)));
    }
}