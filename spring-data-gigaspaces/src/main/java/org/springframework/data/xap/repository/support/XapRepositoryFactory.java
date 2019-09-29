package org.springframework.data.xap.repository.support;

import com.gigaspaces.document.SpaceDocument;
import org.openspaces.core.GigaSpace;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.xap.mapping.XapMappingContext;
import org.springframework.data.xap.mapping.XapPersistentEntity;
import org.springframework.data.xap.mapping.XapPersistentProperty;
import org.springframework.data.xap.repository.SpaceDocumentName;
import org.springframework.data.xap.repository.XapDocumentRepository;
import org.springframework.data.xap.repository.query.DefaultXapEntityInformation;
import org.springframework.data.xap.repository.query.PartTreeXapRepositoryQuery;
import org.springframework.data.xap.repository.query.StringBasedXapRepositoryQuery;
import org.springframework.data.xap.repository.query.XapQueryMethod;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author Oleksiy_Dyagilev
 */
public class XapRepositoryFactory extends RepositoryFactorySupport {
    private GigaSpace space;

    private MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context;

    public XapRepositoryFactory(GigaSpace space, MappingContext<? extends XapPersistentEntity<?>, XapPersistentProperty> context) {
        this.space = space;
        this.context = context == null ? new XapMappingContext() : context;
    }

    private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
        return QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

    private static boolean isSpaceDocumentRepository(Class<?> repositoryInterface) {
        return XapDocumentRepository.class.isAssignableFrom(repositoryInterface);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        XapPersistentEntity<T> entity = (XapPersistentEntity<T>) context.getPersistentEntity(domainClass);
        return new DefaultXapEntityInformation<>(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        if (isQueryDslRepository(repositoryInterface)) {
            return new QueryDslXapRepository<>(space, entityInformation);
        } else if (isSpaceDocumentRepository(repositoryInterface)) {
            String typeName = getSpaceDocumentType(metadata);
            return new XapDocumentRepositoryImpl<>(space, (EntityInformation<? extends SpaceDocument, ? extends Serializable>) entityInformation, typeName);
        } else {
            return new SimpleXapRepository<>(space, entityInformation);
        }
    }

    private String getSpaceDocumentType(RepositoryMetadata metadata) {
        SpaceDocumentName annotation = metadata.getRepositoryInterface().getAnnotation(SpaceDocumentName.class);
        if (annotation == null) {
            //TODO check some configuration exceptions
            throw new IllegalArgumentException("XapDocumentRepository has to be annotated with " + SpaceDocumentName.class.getSimpleName() + " annotation");
        }
        if (StringUtils.isEmpty(annotation.value())) {
            throw new IllegalArgumentException("Type name defined in " + SpaceDocumentName.class.getSimpleName() + " annotation must not be empty");
        }
        return annotation.value();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isSpaceDocumentRepository(metadata.getRepositoryInterface())) {
            return XapDocumentRepositoryImpl.class;
        }
        if (isQueryDslRepository(metadata.getRepositoryInterface())) {
            return QueryDslXapRepository.class;
        }
        return SimpleXapRepository.class;
    }

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
                    return new StringBasedXapRepositoryQuery(namedQueries.getQuery(namedQueryName), queryMethod, space);
                }

                if (isSpaceDocumentRepository(metadata.getRepositoryInterface())) {
                    throw new UnsupportedOperationException("Query methods are not supported in document repositories, use @Query annotation to define SQLQuery manually");
                }
                return new PartTreeXapRepositoryQuery(queryMethod, space);
            }
        };
    }

}