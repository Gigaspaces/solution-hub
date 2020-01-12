package com.gigaspaces.droolsintegration.service.drools;

import org.openspaces.remoting.Routing;

import com.gigaspaces.droolsintegration.util.IterableMapWrapper;

import java.util.List;
import java.util.Map;

/**
 * Interface for rules executing remoting service.
 */
public interface IRulesExecutionService {

	Iterable<Object> executeRules(@Routing String resultSet, Iterable<Object> facts,  Map<String, Object> globals);
        
    Iterable<Object> executeRules(@Routing String resultSet, Iterable<Object> facts);
    
    Iterable<Object> executeRulesWithLimitedResults(@Routing String resultSet, IterableMapWrapper facts,  List<String> resultKeys, Map<String, Object> globals);
    
    Iterable<Object> executeRulesWithLimitedResults(@Routing String resultSet, IterableMapWrapper facts, List<String> resultKeys);

}