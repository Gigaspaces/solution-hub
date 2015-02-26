package org.springframework.data.xap.repository.support;

import com.gigaspaces.document.SpaceDocument;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.gigaspaces.metadata.SpaceTypeDescriptorBuilder;
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
import org.springframework.data.xap.repository.SpaceDocumentRepository;
import org.springframework.data.xap.repository.XapDocumentRepository;
import org.springframework.data.xap.repository.query.DefaultXapEntityInformation;
import org.springframework.data.xap.repository.query.PartTreeXapRepositoryQuery;
import org.springframework.data.xap.repository.query.StringBasedXapRepositoryQuery;
import org.springframework.data.xap.repository.query.XapQueryMethod;

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

    @Override
    protected Object getTargetRepository(RepositoryMetadata metadata) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
        if (isQueryDslRepository(repositoryInterface)) {
            return new QueryDslXapRepository<>(space, entityInformation);
        } else  if (isSpaceDocumentRepository(repositoryInterface)){
            SpaceTypeDescriptor typeDescriptor = createSpaceTypeDescriptor(metadata);
            return new XapDocumentRepositoryImpl<>(space, (EntityInformation<? extends SpaceDocument, Serializable>)entityInformation, typeDescriptor);
        } else {
            return new SimpleXapRepository<>(space, entityInformation);
        }
    }

    private SpaceTypeDescriptor createSpaceTypeDescriptor(RepositoryMetadata metadata) {
        SpaceDocumentRepository annotation = metadata.getRepositoryInterface().getAnnotation(SpaceDocumentRepository.class);
        if (annotation == null){
            //TODO check some configuration exceptions
            throw new IllegalArgumentException("XapDocumentRepository has to be annotated with SpaceDocumentRepository annotation");
        }
        return new SpaceTypeDescriptorBuilder(annotation.typeName())
                .idProperty(annotation.id())
                .routingProperty(annotation.routing())
                .create();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isSpaceDocumentRepository(metadata.getRepositoryInterface())){
            return XapDocumentRepositoryImpl.class;
        }
        return isQueryDslRepository(metadata.getRepositoryInterface()) ? QueryDslXapRepository.class : SimpleXapRepository.class;
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
