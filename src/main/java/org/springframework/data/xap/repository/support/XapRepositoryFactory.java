package org.springframework.data.xap.repository.support;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.xap.mapping.XapMappingContext;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;
import org.springframework.data.xap.repository.query.DefaultXapEntityInformation;
import org.springframework.data.xap.repository.query.PartTreeXapRepositoryQuery;
import org.springframework.data.xap.repository.query.StringBasedXapRepositoryQuery;
import org.springframework.data.xap.repository.query.XapQueryMethod;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactory extends RepositoryFactorySupport {
    private SpaceClient space;

    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;

    public XapRepositoryFactory(SpaceClient space, MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        this.space = space;
        this.context = context == null ? new XapMappingContext() : context;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        XapPersistentEntity<T> entity = (XapPersistentEntity<T>) context.getPersistentEntity(domainClass);
        return new DefaultXapEntityInformation<>(entity);
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        return new SimpleXapRepository<>(space, entityInformation);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleXapRepository.class;
    }

    /*
 * (non-Javadoc)
 *
 * @see springframework.data.repository.core.support.RepositoryFactorySupport
 * 	#getQueryLookupStrategy(org.springframework.data.repository.query.QueryLookupStrategy.Key)
 */
    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
        return new QueryLookupStrategy() {
            @Override
            public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
                XapQueryMethod queryMethod = new XapQueryMethod(method, metadata, context);

                if (queryMethod.hasAnnotatedQuery()) {
                    return new StringBasedXapRepositoryQuery(queryMethod, space);
                }

                String namedQueryName = queryMethod.getNamedQueryName();

                if (namedQueries.hasQuery(namedQueryName)) {
                    return new StringBasedXapRepositoryQuery(namedQueries.getQuery(namedQueryName), queryMethod,
                            space);
                }

                return new PartTreeXapRepositoryQuery(queryMethod, space);
            }
        };
    }
}
