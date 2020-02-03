package org.openspaces.persistency.cassandra.types;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatementBuilder;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.MapperException;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.mapper.entity.saving.NullSavingStrategy;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.delete.DeleteSelection;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures;
import com.datastax.oss.driver.internal.mapper.DefaultMapperContext;
import com.datastax.oss.driver.internal.mapper.entity.EntityHelperBase;
import com.datastax.oss.protocol.internal.ProtocolConstants;
import org.openspaces.persistency.cassandra.error.SpaceCassandraTypeIntrospectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

public class CassandraTypeInfo {
    private static Logger logger = LoggerFactory.getLogger(CassandraTypeInfo.class);

    private final Class<? extends EntityHelperBase> entityHelpersClass;
    private final Class<?>      type;
    private final CqlIdentifier defaultKeyspaceId;
    private final CqlIdentifier tableId=null; //TODO: add support
    private final EntityHelperBase<?> entityHelper;
    private final Constructor<? extends EntityHelperBase> entityHelperConstructor;
    private static final CqlIdentifier APPLIED = CqlIdentifier.fromInternal("[applied]");
    private final List<String> primaryKeys;

    private PreparedStatement findByIdStatement;
    private PreparedStatement saveStatement;
    private PreparedStatement deleteStatement;

    public CassandraTypeInfo(
            Class<? extends EntityHelperBase> entityHelpersClass,
            String defaultKeyspace,
            CqlSession session) throws SpaceCassandraTypeIntrospectionException {
        this.entityHelpersClass=entityHelpersClass;
        this.defaultKeyspaceId=CqlIdentifier.fromCql(defaultKeyspace);
        try {
            entityHelperConstructor = entityHelpersClass.getDeclaredConstructor(MapperContext.class);
        } catch (NoSuchMethodException e) {
            String msg = "Fail to retrieve entityHelpersClass="+entityHelpersClass.getName()+" constructor with mapperContext";
            logger.error(msg,e);
            throw new SpaceCassandraTypeIntrospectionException(msg,e);
        }
        this.entityHelper = getEntityHelper(session) ;
        try {
            Field f = entityHelpersClass.getDeclaredField("primaryKeys");
            f.setAccessible(true);
            primaryKeys = (List<String>) f.get(this.entityHelper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            String msg = "Fail to retrieve entityHelpersClass="+entityHelpersClass.getName()+" constructor with mapperContext";
            logger.error(msg,e);
            throw new SpaceCassandraTypeIntrospectionException(msg,e);
        }
        this.type=entityHelper.getEntityClass();
/*        this.spaceTypeDescriptor = new SpaceTypeDescriptorBuilder(this.type.getName())
                .create();*/

        logger.info("retrieve entity class {} for {} ",type,entityHelpersClass);
        initAsync(getMapperContext(session));
    }

    public Select getBaseSelectQuery(CqlSession session){
        EntityHelperBase<?> entityHelperBase = getEntityHelper(session);
        return entityHelperBase.selectStart();
    }

    public Select getAllSelectQuery(CqlSession session){
        Select select= getBaseSelectQuery(session);
        return select.all();
    }

    public EntityHelperBase<?> getEntityHelper(){
        return entityHelper;
    }

    private EntityHelperBase<?> getEntityHelper(CqlSession session) throws SpaceCassandraTypeIntrospectionException{
        try {
            return entityHelperConstructor.newInstance(getMapperContext(session));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String msg = "Fail to create new entityHelper "+entityHelpersClass.getName()+" with "+entityHelperConstructor;
            logger.error(msg,e);
            throw new SpaceCassandraTypeIntrospectionException(msg,e);
        }
    }

    public DefaultMapperContext getMapperContext(CqlSession session){
        return new DefaultMapperContext(
                session,
                defaultKeyspaceId,
                new ConcurrentHashMap<>()); // TODO : think about if we need or not custom state ?
    }

    public Class<? extends EntityHelperBase> getEntityHelpersClass() {
        return entityHelpersClass;
    }

    public Select selectStart() {
        return entityHelper.selectStart();
    }

    public Select selectByPrimaryKeyParts(int parameterCount) {
        Select select = selectStart();
        for (int i = 0; i < parameterCount && i < primaryKeys.size(); i++) {
            String columnName = primaryKeys.get(i);
            select = select.whereColumn(columnName).isEqualTo(QueryBuilder.bindMarker(columnName));
        }
        return select;
    }

    public CqlIdentifier getKeyspaceId(){
        return defaultKeyspaceId;
    }

    protected void throwIfKeyspaceMissing(CqlSession session) {
        if (this.getKeyspaceId() == null && !session.getKeyspace().isPresent()) {
            throw new MapperException(
                    String.format(
                            "Missing keyspace. Suggestions: use SessionBuilder.withKeyspace() "
                                    + "when creating your session, specify a default keyspace on %s with @%s"
                                    + "(defaultKeyspace), or use a @%s method with a @%s parameter",
                            this.type.getSimpleName(),
                            Entity.class.getSimpleName(),
                            DaoFactory.class.getSimpleName(),
                            DaoKeyspace.class.getSimpleName()));
        }
    }

    public Select selectByPrimaryKey() {
        return selectByPrimaryKeyParts(primaryKeys.size());
    }

    public DeleteSelection deleteStart(CqlSession session) {
        throwIfKeyspaceMissing(session);
        return (defaultKeyspaceId == null)
                ? QueryBuilder.deleteFrom(getEntityHelper().getTableId())
                : QueryBuilder.deleteFrom(defaultKeyspaceId, getEntityHelper().getTableId());
    }

    public Delete deleteByPrimaryKeyParts(CqlSession session,int parameterCount) {
        if (parameterCount <= 0) {
            throw new MapperException("parameterCount must be greater than 0");
        }
        DeleteSelection deleteSelection = deleteStart(session);
        String columnName = primaryKeys.get(0);
        Delete delete = deleteSelection.whereColumn(columnName).isEqualTo(QueryBuilder.bindMarker(columnName));
        for (int i = 1; i < parameterCount && i < primaryKeys.size(); i++) {
            columnName = primaryKeys.get(i);
            delete = delete.whereColumn(columnName).isEqualTo(QueryBuilder.bindMarker(columnName));
        }
        return delete;
    }

    public Delete deleteByPrimaryKey(CqlSession session) {
        return deleteByPrimaryKeyParts(session,primaryKeys.size());
    }

    public Class<?> getType() {
        return type;
    }

    public CqlIdentifier getDefaultKeyspaceId() {
        return defaultKeyspaceId;
    }

    public  <EntityT> EntityT asEntity(Row row, EntityHelper<EntityT> entityHelper) {
        return (row == null
                // Special case for INSERT IF NOT EXISTS. If the row did not exists, the query returns
                // only [applied], we want to return null to indicate there was no previous entity
                || (row.getColumnDefinitions().size() == 1
                && row.getColumnDefinitions().get(0).getName().equals(APPLIED)))
                ? null
                : entityHelper.get(row);
    }

    protected static CompletionStage<PreparedStatement> prepare(
            SimpleStatement statement, MapperContext context) {
        return context.getSession().prepareAsync(statement);
    }

    public void insert(CqlSession session, Object obj){
        if(!obj.getClass().isAssignableFrom(type)){
            throw new IllegalArgumentException("object "+obj+" not an instance of "+type);
        }
        BoundStatementBuilder boundStatementBuilder = boundSaveStatementBuilder();
        ((EntityHelperBase)getEntityHelper()).set(type.cast(obj), boundStatementBuilder, NullSavingStrategy.DO_NOT_SET);
        session.execute(boundStatementBuilder.build());
    }

    private void initAsync(MapperContext context) throws IllegalStateException{
        logger.debug("[{}] Initializing new instance for keyspace = {} and table = {}",
                context.getSession().getName(),
                context.getKeyspaceId(),
                context.getTableId());
        throwIfProtocolVersionV3(context);
        try {
            List<CompletionStage<PreparedStatement>> prepareStages = new ArrayList<>();
            // Prepare the statement for `findById(java.util.UUID)`:
            SimpleStatement findByIdStatement_simple = selectByPrimaryKeyParts(primaryKeys.size()).build();
            logger.debug("[{}] Preparing query `{}` for method findById(java.util.UUID)",
                    context.getSession().getName(),
                    findByIdStatement_simple.getQuery());
            CompletionStage<PreparedStatement> findByIdStatement = prepare(findByIdStatement_simple, context);
            prepareStages.add(findByIdStatement);
            // Prepare the statement for `save(org.openspaces.persistency.cassandra.example1.entities.Product)`:
            SimpleStatement saveStatement_simple = entityHelper.insert().build();
            logger.debug("[{}] Preparing query `{}` for method save(org.openspaces.persistency.cassandra.example1.entities.Product)",
                    context.getSession().getName(),
                    saveStatement_simple.getQuery());
            CompletionStage<PreparedStatement> saveStatement = prepare(saveStatement_simple, context);
            prepareStages.add(saveStatement);
            // Prepare the statement for `delete(org.openspaces.persistency.cassandra.example1.entities.Product)`:
            SimpleStatement deleteStatement_simple = deleteByPrimaryKeyParts(context.getSession(),primaryKeys.size()).build();
            logger.debug("[{}] Preparing query `{}` for method delete(org.openspaces.persistency.cassandra.example1.entities.Product)",
                    context.getSession().getName(),
                    deleteStatement_simple.getQuery());
            CompletionStage<PreparedStatement> deleteStatement = prepare(deleteStatement_simple, context);
            prepareStages.add(deleteStatement);
            // Initialize all method invokers
            CompletableFutures.allSuccessful(prepareStages)
                    .thenRun(()->{
                        this.findByIdStatement = CompletableFutures.getCompleted(findByIdStatement);
                        this.saveStatement     = CompletableFutures.getCompleted(saveStatement);
                        this.deleteStatement   = CompletableFutures.getCompleted(deleteStatement);
                    });
        } catch (Throwable t) {
            logger.error("failed to initialize",t);
            throw new IllegalStateException(t);
        }
    }

    protected static void throwIfProtocolVersionV3(MapperContext context) {
        if (context.getSession().getContext().getProtocolVersion().getCode()
                <= ProtocolConstants.Version.V3) {
            throw new MapperException(
                    String.format(
                            "You cannot use %s.%s for protocol version V3.",
                            NullSavingStrategy.class.getSimpleName(), NullSavingStrategy.DO_NOT_SET.name()));
        }
    }

    public BoundStatementBuilder boundSaveStatementBuilder() {
        return saveStatement.boundStatementBuilder();
    }

}
