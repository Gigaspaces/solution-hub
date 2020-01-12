package com.gigaspaces.droolsintegration.web.dao;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.UnknownTypeException;
import com.j_spaces.core.client.SQLQuery;
import com.gigaspaces.droolsintegration.exceptions.TypeNotFoundException;
import com.gigaspaces.droolsintegration.util.GigaSpacesUtils;

@Component
public class GenericObjectDao {

	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object readById(String type, String id) throws UnknownTypeException {
		Object spaceId = GigaSpacesUtils.determineSpaceIdType(gigaSpace, type, id);
	    
		Class classType;
		try {
			classType = Class.forName(type);
		}catch(ClassNotFoundException cnfe) {
			throw new TypeNotFoundException(cnfe.getMessage());
		}
	    
		IdQuery<Object> idQuery = new IdQuery<Object>(classType, spaceId);
	    return gigaSpace.readById(idQuery);	    
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] readMultipleByType(String type) {
		Class classType;
		try {
			classType = Class.forName(type);
		}catch(ClassNotFoundException cnfe) {
			throw new TypeNotFoundException(cnfe.getMessage());
		}
	    
		return gigaSpace.readMultiple(new SQLQuery(classType, ""));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object takeById(String type, String id)  throws UnknownTypeException  {
		Object spaceId = GigaSpacesUtils.determineSpaceIdType(gigaSpace, type, id);
	    
		Class classType;
		try {
			classType = Class.forName(type);
		}catch(ClassNotFoundException cnfe) {
			throw new TypeNotFoundException(cnfe.getMessage());
		}
	    
		IdQuery<Object> idQuery = new IdQuery<Object>(classType, spaceId);
	    return  gigaSpace.takeById(idQuery);
	}
	
}
