package com.gigaspaces.droolsintegration.service.drools.impl;

import java.util.List;
import java.util.Map;

import org.drools.runtime.StatelessKnowledgeSession;
import org.openspaces.remoting.RemotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gigaspaces.droolsintegration.model.drools.KnowledgeBaseWrapper;
import com.gigaspaces.droolsintegration.dao.KnowledgeBaseWrapperDao;
import com.gigaspaces.droolsintegration.service.drools.IRulesExecutionService;
import com.gigaspaces.droolsintegration.util.IterableMapWrapper;


@RemotingService
public class RulesExecutionServiceImpl implements IRulesExecutionService {

    @Autowired
    private KnowledgeBaseWrapperDao knowledgeBaseWrapperDao;
    
    @Transactional
    public Iterable<Object> executeRules(String ruleSet, Iterable<Object> facts,  Map<String, Object> globals) {
    	
    	KnowledgeBaseWrapper knowledgeBaseWrapper = knowledgeBaseWrapperDao.read(ruleSet);
    	if(knowledgeBaseWrapper != null) {
    		StatelessKnowledgeSession session = knowledgeBaseWrapper.getKnowledgeBase().newStatelessKnowledgeSession();
    		
	        //Session scoped globals
	        if(globals != null) {
	            for(String key : globals.keySet()) {
	                session.setGlobal(key, globals.get(key));
	            }
	        }
	        	
	        session.execute(facts);
    	}else {
    		return null;
    	}
    	
        return facts;
    }
    
    public Iterable<Object> executeRules(String resultSet, Iterable<Object> facts) {
        return executeRules(resultSet, facts, null);
    }
    
    @Transactional
    public Iterable<Object> executeRulesWithLimitedResults(String ruleSet, IterableMapWrapper facts, List<String> resultKeys, Map<String, Object> globals) {
    	
    	KnowledgeBaseWrapper knowledgeBaseWrapper = knowledgeBaseWrapperDao.read(ruleSet);
    	if(knowledgeBaseWrapper != null) {
    		StatelessKnowledgeSession session = knowledgeBaseWrapper.getKnowledgeBase().newStatelessKnowledgeSession();
    		
	        //Session scoped globals
	        if(globals != null) {
	            for(String key : globals.keySet()) {
	                session.setGlobal(key, globals.get(key));
	            }
	        }
	        	
	        session.execute(facts);	        
    	}else {
    		return null;
    	}
    	
    	IterableMapWrapper resultFacts = null;
    	if(resultKeys != null && !resultKeys.isEmpty()) {
	    	resultFacts = new IterableMapWrapper();
	    	for(String key : resultKeys) {
	    		resultFacts.put(key, facts.get(key));
	    	}
    	}
	    
        return resultFacts;
    }
    
    public Iterable<Object> executeRulesWithLimitedResults(String resultSet, IterableMapWrapper facts, List<String> resultKeys) {
        return executeRulesWithLimitedResults(resultSet, facts, resultKeys, null);
    }

}