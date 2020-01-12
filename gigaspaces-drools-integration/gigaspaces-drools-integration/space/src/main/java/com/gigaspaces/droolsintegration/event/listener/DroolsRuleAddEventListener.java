package com.gigaspaces.droolsintegration.event.listener;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.io.ResourceFactory;
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
import com.gigaspaces.droolsintegration.model.drools.DroolsRuleAddEvent;
import com.gigaspaces.droolsintegration.model.drools.KnowledgeBaseWrapper;
import com.gigaspaces.droolsintegration.model.drools.KnowledgePackage;

@EventDriven
@TransactionalEvent(timeout=3000)
@Polling(gigaSpace = "gigaSpace", concurrentConsumers = 1, maxConcurrentConsumers = 1)
public class DroolsRuleAddEventListener  {

    private final Logger log = Logger.getLogger(DroolsRuleAddEventListener.class);
    
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
    public static SQLQuery<DroolsRuleAddEvent> findUnprocessedRule() {
        return new SQLQuery<DroolsRuleAddEvent>(DroolsRuleAddEvent.class, "processed = ?", Boolean.FALSE);
    }

    @SpaceDataEvent
    public DroolsRuleAddEvent addRule(DroolsRuleAddEvent droolsRuleAddEvent) {
    	String ruleSet = droolsRuleAddEvent.getRuleSet();
		String knowledgePackageName = droolsRuleAddEvent.getPackageName();
		String ruleName = droolsRuleAddEvent.getRuleName();
    	
		try {
			KnowledgeBaseWrapper knowledgeBaseWrapper = knowledgeBaseWrapperDao.read(ruleSet);
            if(knowledgeBaseWrapper == null) {
            	knowledgeBaseWrapper = createKnowledgeBaseWrapper(ruleSet);
            }
            
            KnowledgePackage knowledgePackage = knowledgePackageDao.read(ruleSet, knowledgePackageName);
            if(knowledgePackage == null) {
            	knowledgePackage = createKnowledgePackage(knowledgeBaseWrapper, knowledgePackageName, ruleSet);
            }
            
            if(!knowledgePackage.getRules().containsKey(ruleName)) {
            	DroolsRule droolsRule = createDroolsRule(droolsRuleAddEvent);
            	knowledgePackageDao.addRule(knowledgePackage, ruleName, droolsRule);
            	
            	addKnowledgePackages(knowledgeBaseWrapper, droolsRuleAddEvent, ruleName);
            	knowledgeBaseWrapperDao.write(knowledgeBaseWrapper);
                
                log.info(String.format("Rule '%s' compiled successfully", ruleName));
            }else {
                log.info(String.format("Rule '%s' already exists in knowledgePackage '%s'", ruleName, knowledgePackageName));
            }
        }catch(Exception e) {
            log.info(String.format("Rule '%s' failed compilation for ruleset", ruleName));
            log.error(e.getMessage(), e);
        }

		droolsRuleAddEvent.setProcessed(Boolean.TRUE);
        return null;
    }
    
	private KnowledgeBaseWrapper createKnowledgeBaseWrapper(String ruleSet) {
		KnowledgeBaseWrapper knowledgeBaseWrapper = new KnowledgeBaseWrapper(ruleSet);
		
		knowledgeBaseWrapper.setKnowledgeBase(KnowledgeBaseFactory.newKnowledgeBase());
	    knowledgeBaseWrapper.setTotalKnowledgePackages(new Integer(0));
	    knowledgeBaseWrapper.setTotalRules(new Integer(0));
	    	    
	    return knowledgeBaseWrapper;
	}
	
	private void addKnowledgePackages(KnowledgeBaseWrapper knowledgeBaseWrapper, DroolsRuleAddEvent droolsRuleAddEvent, String ruleName) {
		String resourceType = droolsRuleAddEvent.getOriginalResourceType();
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(new PackageBuilderConfiguration(properties));
        kbuilder.add(ResourceFactory.newByteArrayResource(droolsRuleAddEvent.getRuleBytes()), ResourceType.getResourceType(resourceType));

        if(kbuilder.hasErrors()) {
            for(KnowledgeBuilderError error : kbuilder.getErrors()) {
                log.error(error.getMessage() + " on lines " + Arrays.toString(error.getLines()));
            }
            throw new IllegalArgumentException("Could not parse Rule: " + ruleName + " type: " + resourceType);
        }
		
		knowledgeBaseWrapper.getKnowledgeBase().addKnowledgePackages(kbuilder.getKnowledgePackages());
        knowledgeBaseWrapper.setTotalRules(knowledgeBaseWrapper.getTotalRules() + 1);
	}
    
    private KnowledgePackage createKnowledgePackage(KnowledgeBaseWrapper knowledgeBaseWrapper, String knowledgePackageName, String ruleSet) {
		knowledgeBaseWrapper.setTotalKnowledgePackages(knowledgeBaseWrapper.getTotalKnowledgePackages() + 1);
    	
    	KnowledgePackage knowledgePackage = new KnowledgePackage(knowledgePackageName);
    	
    	knowledgePackage.setRuleSet(ruleSet);
    	knowledgePackage.setCreateDate(new Date(System.currentTimeMillis()));
    	knowledgePackage.setRules(new HashMap<String, DroolsRule>());
    	knowledgePackage.setTotalRules(new Integer(0));
    	
    	knowledgePackageDao.write(knowledgePackage);
    	    	
    	return knowledgePackage;
    }
    
    private DroolsRule createDroolsRule(DroolsRuleAddEvent droolsRuleAddEvent) {
    	DroolsRule droolsRule = new DroolsRule();
		
		droolsRule.setRuleName(droolsRuleAddEvent.getRuleName());
		droolsRule.setRuleSet(droolsRuleAddEvent.getRuleSet());
		droolsRule.setOriginalResourceType(droolsRuleAddEvent.getOriginalResourceType());
		droolsRule.setCreateDate(new Date(System.currentTimeMillis()));
 
    	return droolsRule;
    }
    
}