package com.gigaspaces.droolsintegration.analyze;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.definition.KnowledgePackage;
import org.drools.definition.rule.Rule;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.util.RulesConstants;
import com.gigaspaces.droolsintegration.model.drools.KnowledgeBaseWrapper;


@Component
public class PrintOutSingleKnowledgePackage  {
	
	private static Logger log = Logger.getLogger(PrintOutSingleKnowledgePackage.class);

	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
    public static void main(String[] args) {  
        ClassPathXmlApplicationContext applicationContext = null;
    	try {
    		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    		
    		String ruleSet;
	    	if(args != null && args.length > 0)
	    		ruleSet = args[0];
	    	else 
	    		ruleSet = RulesConstants.RULE_SET_APPLICANT_AGE;
	    	
	    	String packageName;
	    	if(args != null && args.length > 1)
	    		packageName = args[1];
	    	else 
	    		packageName = RulesConstants.PACKAGE_NAME_APPLICATION;
    		
	    	PrintOutSingleKnowledgePackage printOutSingleKnowledgePackage = (PrintOutSingleKnowledgePackage) applicationContext.getBean("printOutSingleKnowledgePackage");
	    	printOutSingleKnowledgePackage.execute(ruleSet, packageName);
    	}catch(Exception e) {
    		log.error(e);
    	}finally {
    		if(applicationContext != null) {
    			applicationContext.close(); applicationContext = null;
    		}
    	}
    }
    
    public void execute(String ruleSet, String packageName) {
        log.info("Starting PrintOutSingleKnowledgePackage Execution");

        try {
        	KnowledgeBaseWrapper knowledgeBaseWrapper = gigaSpace.read(new KnowledgeBaseWrapper(ruleSet));
        	if(knowledgeBaseWrapper != null) {
        		KnowledgeBase knowledgeBase = knowledgeBaseWrapper.getKnowledgeBase();
        		
        		KnowledgePackage knowledgePackage = knowledgeBase.getKnowledgePackage(packageName);
	        	if(knowledgePackage != null) {
        			log.info("Begin KnowledgePackage: " + knowledgePackage.getName());
        			        			
        			Collection<Rule> rules = knowledgePackage.getRules();
        			if(rules != null) {
        				for(Rule rule : rules) {
        					log.info("Rule ID: " + rule.getId());
        					log.info("Rule Name: " + rule.getName());
        				}
        			}else {
    	    			log.info("rules are null for ruleSet: " + ruleSet);
    	        	}
        			
        			log.info("End KnowledgePackage: " + knowledgePackage.getName());
	        	}else {
	    			log.info("knowledgePackage is null : " + packageName);
	        	}
        	}else {
    			log.info("knowledgeBaseWrapper is null for ruleSet: " + ruleSet);
    		}
        	
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("End PrintOutSingleKnowledgePackage Execution");
    }
    
}