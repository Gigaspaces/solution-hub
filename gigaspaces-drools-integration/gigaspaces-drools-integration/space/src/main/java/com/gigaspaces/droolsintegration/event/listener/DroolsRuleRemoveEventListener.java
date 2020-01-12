package com.gigaspaces.droolsintegration.event.listener;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.TransactionalEvent;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.springframework.beans.factory.annotation.Autowired;

import com.j_spaces.core.client.SQLQuery;
import com.gigaspaces.droolsintegration.dao.KnowledgeBaseWrapperDao;
import com.gigaspaces.droolsintegration.dao.KnowledgePackageDao;
import com.gigaspaces.droolsintegration.model.drools.DroolsRule;
import com.gigaspaces.droolsintegration.model.drools.DroolsRuleRemoveEvent;
import com.gigaspaces.droolsintegration.model.drools.KnowledgeBaseWrapper;
import com.gigaspaces.droolsintegration.model.drools.KnowledgePackage;

@EventDriven
@TransactionalEvent(timeout=3000)
@Polling(gigaSpace = "gigaSpace", concurrentConsumers = 1, maxConcurrentConsumers = 1)
public class DroolsRuleRemoveEventListener  {

    private final Logger log = Logger.getLogger(DroolsRuleRemoveEventListener.class);
    
    @Autowired
    private KnowledgeBaseWrapperDao knowledgeBaseWrapperDao;
    
    @Autowired
    private KnowledgePackageDao knowledgePackageDao;
    
    private Properties properties;

    @PostConstruct
    public void initializeProperties() {
    	properties = new Properties();
        properties.setProperty("drools.dialect.java.compiler.lnglevel", "1.6");
    }
    
    @EventTemplate
    public static SQLQuery<DroolsRuleRemoveEvent> findUnprocessedRule() {
        return new SQLQuery<DroolsRuleRemoveEvent>(DroolsRuleRemoveEvent.class, "processed = ?", Boolean.FALSE);
    }

    @SpaceDataEvent
    public DroolsRuleRemoveEvent removeRule(DroolsRuleRemoveEvent droolsRuleEvent) {
    	String ruleSet = droolsRuleEvent.getRuleSet();
		String packageName = droolsRuleEvent.getPackageName();
		String ruleName = droolsRuleEvent.getRuleName();
    	
		try {
    		KnowledgeBaseWrapper knowledgeBaseWrapper = knowledgeBaseWrapperDao.read(ruleSet);
            if(knowledgeBaseWrapper == null) {
            	log.info(String.format("KnowledgeBase for RuleSet: '%s' does not exist", ruleSet));
            	return null;
            }
            
            if(knowledgeBaseWrapper.getKnowledgeBase().getRule(packageName, ruleName) == null) {            	
            	log.info(String.format("Rule '%s' does not exist", ruleName));
            	return null;
            }
            
            boolean isKnowledgePackageEmpty = removeRuleFromKnowledgePackage(packageName, ruleSet, ruleName);
            
            removeRuleFromKnowledgeBaseWrapper(knowledgeBaseWrapper, packageName, ruleName, isKnowledgePackageEmpty);
            knowledgeBaseWrapperDao.write(knowledgeBaseWrapper);
            
            log.info(String.format("KnowledgePackage '%s' compiled successfully", packageName));
        }catch(Exception e) {
            log.info(String.format("KnowledgePackage '%s' failed compilation for ruleset", droolsRuleEvent.getPackageName()));
            log.error(e.getMessage(), e);
        }

        droolsRuleEvent.setProcessed(Boolean.TRUE);
        return null;
    }
    
    private boolean removeRuleFromKnowledgePackage(String packageName, String ruleSet, String ruleName) {
    	boolean removeKnowledgePackage = false;
    	try {    		
    		KnowledgePackage knowledgePackage = knowledgePackageDao.read(ruleSet, packageName);
    		if(knowledgePackage != null) {
    			Map<String, DroolsRule> ruleMap = knowledgePackage.getRules();
    			if(ruleMap != null && ruleMap.containsKey(ruleName)) {
    				if(ruleMap.size() == 1) {
            			knowledgePackageDao.clear(knowledgePackage);
            			removeKnowledgePackage = true;
                        log.info(String.format("KnowledgePackage '%s' cleared from the space successfully", packageName));
        			}else {
        				knowledgePackageDao.removeRule(knowledgePackage, ruleName);
                        log.info(String.format("Rule '%s' cleared from the space successfully", ruleName));
        			}
    			}
    		}else {
                log.info(String.format("KnowledgePackage '%s' does not exist in the space", packageName));
    		}
        }catch(Exception e) {
            log.info(String.format("Rule '%s' failed to clear from the space", ruleName));
            log.error(e.getMessage(), e);
        }
    	return removeKnowledgePackage;
    }
    
    private void removeRuleFromKnowledgeBaseWrapper(KnowledgeBaseWrapper knowledgeBaseWrapper, String packageName, String ruleName, boolean isEmpty) {
    	KnowledgeBase knowledgeBase = knowledgeBaseWrapper.getKnowledgeBase();
    	if(isEmpty) {
    		knowledgeBase.removeKnowledgePackage(packageName);
    		knowledgeBaseWrapper.setTotalKnowledgePackages(knowledgeBaseWrapper.getTotalKnowledgePackages() - 1);
    	}else {
    		knowledgeBase.removeRule(packageName, ruleName);
    	}
    	
        knowledgeBaseWrapper.setTotalRules(knowledgeBaseWrapper.getTotalRules() - 1);
    }
    
}