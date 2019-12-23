package org.springframework.data.gigaspaces.repository.support;

import com.gigaspaces.document.SpaceDocument;
import org.openspaces.core.GigaSpace;
import org.springframework.data.gigaspaces.repository.GigaspacesDocumentRepository;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.data.gigaspaces.mapping.GigaspacesMappingContext;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentEntity;
import org.springframework.data.gigaspaces.mapping.GigaspacesPersistentProperty;
import org.springframework.data.gigaspaces.repository.SpaceDocumentName;
import org.springframework.data.gigaspaces.repository.query.DefaultGigaspacesEntityInformation;
import org.springframework.data.gigaspaces.repository.query.PartTreeGigaspacesRepositoryQuery;
import org.springframework.data.gigaspaces.repository.query.StringBasedGigaspacesRepositoryQuery;
import org.springframework.data.gigaspaces.repository.query.GigaspacesQueryMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Oleksiy_Dyagilev
 */
public class GigaspacesRepositoryFactory extends RepositoryFactorySupport {
    private GigaSpace space;

    private MappingContext<? extends GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> context;

    public GigaspacesRepositoryFactory(GigaSpace space, MappingContext<? extends GigaspacesPersistentEntity<?>, GigaspacesPersistentProperty> context) {
        this.space = space;
        this.context = context == null ? new GigaspacesMappingContext() : context;
    }

    private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
        return QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }

    private static boolean isSpaceDocumentRepository(Class<?> repositoryInterface) {
        return GigaspacesDocumentRepository.class.isAssignableFrom(repositoryInterface);
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        GigaspacesPersistentEntity<T> entity = (GigaspacesPersistentEntity<T>) context.getPersistentEntity(domainClass);
        return new DefaultGigaspacesEntityInformation(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        if (isQueryDslRepository(repositoryInterface)) {
            return new QueryDslGigaspacesRepository<>(space, entityInformation);
        } else if (isSpaceDocumentRepository(repositoryInterface)) {
            String typeName = getSpaceDocumentType(metadata);
            return new GigaspacesDocumentRepositoryImpl<>(space, (EntityInformation<? extends SpaceDocument, ? extends Serializable>) entityInformation, typeName);
        } else {
            return new SimpleGigaspacesRepository<>(space, entityInformation);
        }
    }

    private String getSpaceDocumentType(RepositoryMetadata metadata) {
        SpaceDocumentName annotation = metadata.getRepositoryInterface().getAnnotation(SpaceDocumentName.class);
        if (annotation == null) {
            //TODO check some configuration exceptions
            throw new IllegalArgumentException("GigaspacesDocumentRepository has to be annotated with " + SpaceDocumentName.class.getSimpleName() + " annotation");
        }
        if (StringUtils.isEmpty(annotation.value())) {
            throw new IllegalArgumentException("Type name defined in " + SpaceDocumentName.class.getSimpleName() + " annotation must not be empty");
        }
        return annotation.value();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isSpaceDocumentRepository(metadata.getRepositoryInterface())) {
            return GigaspacesDocumentRepositoryImpl.class;
        }
        if (isQueryDslRepository(metadata.getRepositoryInterface())) {
            return QueryDslGigaspacesRepository.class;
        }
        return SimpleGigaspacesRepository.class;
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(@Nullable QueryLookupStrategy.Key key,
                                                                   QueryMethodEvaluationContextProvider evaluationContextProvider){
        return Optional.of((method, metadata, factory, namedQueries) -> {
            GigaspacesQueryMethod queryMethod = new GigaspacesQueryMethod(method, metadata,factory, context);

            if (queryMethod.hasAnnotatedQuery()) {
                return new StringBasedGigaspacesRepositoryQuery(queryMethod, space);
            }

            String namedQueryName = queryMethod.getNamedQueryName();

            if (namedQueries.hasQuery(namedQueryName)) {
                return new StringBasedGigaspacesRepositoryQuery(namedQueries.getQuery(namedQueryName), queryMethod, space);
            }

            if (isSpaceDocumentRepository(metadata.getRepositoryInterface())) {
                throw new UnsupportedOperationException("Query methods are not supported in document repositories, use @Query annotation to define SQLQuery manually");
            }
            return new PartTreeGigaspacesRepositoryQuery(queryMethod, space);
        });
    }

}