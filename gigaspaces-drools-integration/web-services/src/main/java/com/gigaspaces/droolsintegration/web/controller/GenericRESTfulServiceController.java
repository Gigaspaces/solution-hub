package com.gigaspaces.droolsintegration.web.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.j_spaces.core.UnknownTypeException;
import com.gigaspaces.droolsintegration.exceptions.ObjectNotFoundException;
import com.gigaspaces.droolsintegration.web.dao.GenericObjectDao;


@Controller
@RequestMapping(value = "/rest/*")
public class GenericRESTfulServiceController {
	
    private static final Logger logger = Logger.getLogger(GenericRESTfulServiceController.class.getName());
	
    @Autowired
    private GenericObjectDao dao;
    
    /**
	 * @param type
	 * @param id
	 */
	@RequestMapping(value = "/{type:.+}/{id:.+}", method = RequestMethod.GET)
	public @ResponseBody Object getById(@PathVariable String type, @PathVariable String id) throws ObjectNotFoundException, UnknownTypeException {
		
		if(logger.isLoggable(Level.FINE))
	        logger.fine("Attempting to readById with type: " + type + " and id: " + id);
	    
	    Object object = dao.readById(type, id);
	    if(object == null) {
	        throw new ObjectNotFoundException("No Object Matched the Criteria");
	    }
	    return object;
	}
	
	/**
     * @param type 
     */
	@RequestMapping(value = "/{type:.+}", method = RequestMethod.GET)
	public @ResponseBody Object[] getByType(@PathVariable String type) throws ObjectNotFoundException, UnknownTypeException {
		if(logger.isLoggable(Level.FINE))
	        logger.fine("Attempting to read with type: " + type);
	   	
	    Object[] objects = dao.readMultipleByType(type);
	    if(objects == null) {
	        throw new ObjectNotFoundException("No Object Matched the Criteria");
	    }
	    return objects;
	}

	/**
	 * @param type
	 * @param id
	 */
	@RequestMapping(value = "/{type:.+}/{id:.+}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteById(@PathVariable String type, @PathVariable String id) throws ObjectNotFoundException, UnknownTypeException {
		if(logger.isLoggable(Level.FINE))
	        logger.fine("Attempting to takeById with type: " + type + " and id: " + id);
	    
	    Object object = dao.takeById(type, id);
	    if(object == null) {
	        throw new ObjectNotFoundException("No Object Matched the Criteria");
	    }
	    return object;
	}
	
}