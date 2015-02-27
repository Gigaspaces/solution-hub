package org.springframework.data.xap.repository.query;

import com.gigaspaces.document.SpaceDocument;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.data.xap.repository.SpaceDocumentRepository;

/**
 * @author Anna_Babich.
 */
public class StringBasedXapRepositoryQuery extends XapRepositoryQuery{

    private final XapQueryMethod method;
    private final GigaSpace space;
    private final String query;

    public StringBasedXapRepositoryQuery(String query, XapQueryMethod method, GigaSpace space){
        super(method);
        this.method = method;
        this.space = space;
        this.query = query;
    }

    public StringBasedXapRepositoryQuery(XapQueryMethod method, GigaSpace space){
        this(method.getAnnotatedQuery(), method, space);
    }

    @Override
    public Object execute(Object[] parameters) {
        SQLQuery sqlQuery = new SQLQuery(getTypeName(method), query);
        sqlQuery.setParameters(parameters);
        return space.readMultiple(sqlQuery);
    }

    private String getTypeName(XapQueryMethod method){
        if (isSpaceDocumentQuery(method)){
            SpaceDocumentRepository annotation = method.getMetadata().getRepositoryInterface().getAnnotation(SpaceDocumentRepository.class);
            return annotation.typeName();
        }   else {
            return method.getEntityInformation().getJavaType().getCanonicalName();
        }
    }

    private boolean isSpaceDocumentQuery(XapQueryMethod method){
        return SpaceDocument.class.isAssignableFrom(method.getMetadata().getDomainType());
    }

}

