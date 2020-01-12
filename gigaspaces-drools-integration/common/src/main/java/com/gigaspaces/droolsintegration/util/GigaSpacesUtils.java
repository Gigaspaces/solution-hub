package com.gigaspaces.droolsintegration.util;

import java.util.HashMap;
import java.util.Map;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.pu.container.spi.ApplicationContextProcessingUnitContainer;
import org.openspaces.pu.container.standalone.StandaloneProcessingUnitContainerProvider;

import com.gigaspaces.metadata.SpacePropertyDescriptor;
import com.gigaspaces.metadata.SpaceTypeDescriptor;
import com.j_spaces.core.UnknownTypeException;
import com.gigaspaces.droolsintegration.exceptions.TypeNotFoundException;

public class GigaSpacesUtils {

	private GigaSpacesUtils() {}
	
	public static GigaSpace configureGigaSpaceProxy(String spaceUrl) {
    	GigaSpace gigaSpace;
    	try {
    		gigaSpace = new GigaSpaceConfigurer(new UrlSpaceConfigurer(spaceUrl)).gigaSpace();	
		}catch(Exception e) {
			e.printStackTrace();
			throw(new RuntimeException(e));
		}
    	return gigaSpace;
    }
	
	public static ApplicationContextProcessingUnitContainer getApplicationContextContainer(String jarPath, String schema, int primary, int backup) {
		StandaloneProcessingUnitContainerProvider provider = new StandaloneProcessingUnitContainerProvider(jarPath);
		
		//Provide cluster information for the specific PU instance
		ClusterInfo clusterInfo = new ClusterInfo();
		clusterInfo.setSchema(schema);
		clusterInfo.setNumberOfInstances(primary);
		clusterInfo.setNumberOfBackups(backup);
		clusterInfo.setInstanceId(1);
		provider.setClusterInfo(clusterInfo);
		
		//Build the Spring application context
		ApplicationContextProcessingUnitContainer container = (ApplicationContextProcessingUnitContainer) provider.createContainer();
		return container;
	}
	
	private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
	static {
		primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
		primitiveWrapperMap.put(Byte.TYPE, Byte.class);
		primitiveWrapperMap.put(Character.TYPE, Character.class);
		primitiveWrapperMap.put(Short.TYPE, Short.class);
		primitiveWrapperMap.put(Integer.TYPE, Integer.class);
		primitiveWrapperMap.put(Long.TYPE, Long.class);
		primitiveWrapperMap.put(Double.TYPE, Double.class);
		primitiveWrapperMap.put(Float.TYPE, Float.class);
		primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
	}
	
	public static Object determineSpaceIdType(GigaSpace gigaSpace, String type, String id) throws UnknownTypeException {
		SpaceTypeDescriptor typeDescriptor = gigaSpace.getTypeManager().getTypeDescriptor(type);
	    if(typeDescriptor == null) {
	        throw new TypeNotFoundException(type);
	    }
	    
	    String idPropertyName = typeDescriptor.getIdPropertyName();
	    SpacePropertyDescriptor idProperty = typeDescriptor.getFixedProperty(idPropertyName);
	    
	    return convertPropertyToPrimitiveType(id, idProperty.getType(), idPropertyName);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object convertPropertyToPrimitiveType(String object, Class type, String propKey) throws UnknownTypeException {
        if(type.isPrimitive()) {
        	type = primitiveWrapperMap.get(type);
        }
		
        if(type.equals(String.class) || type.equals(Object.class))
            return String.valueOf(object);
        
        if(type.equals(Integer.class))
            return Integer.valueOf(object);
        
        if(type.equals(Long.class))
            return Long.valueOf(object);
        
        if(type.equals(Short.class))
            return Short.valueOf(object);
        
        if(type.equals(Double.class))
            return Double.valueOf(object);
        
        if(type.equals(Boolean.class))
            return Boolean.valueOf(object);
        
        if(type.equals(Byte.class))
            return Byte.valueOf(object);
       
        if(type.equals(Float.class))
            return Float.valueOf(object);
        
        if(type.isEnum())
            return Enum.valueOf(type, object);
        
        throw new UnknownTypeException("ERROR: Non-Primitive type was provided ", type.getName());
    }
	
	public static Class<?> primitiveToWrapper(final Class<?> cls) {
		Class<?> convertedClass = cls;
		
		if(cls != null && cls.isPrimitive()) {
			convertedClass = primitiveWrapperMap.get(cls);
		}
		
		return convertedClass;
	}
	
}