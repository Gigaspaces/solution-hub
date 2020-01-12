package com.gigaspaces.droolsintegration.rules;

import org.apache.log4j.Logger;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.util.RulesConstants;
import com.gigaspaces.droolsintegration.model.drools.DroolsRuleRemoveEvent;


@Component
public class RemoveRule {
	
	private static Logger log = Logger.getLogger(RemoveRule.class);
	
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
	    	
	    	String ruleName;
	    	if(args != null && args.length > 2)
	    		ruleName = args[2];
	    	else 
	    		ruleName = "ValidateApplicationRule";
	    	
	    	RemoveRule removeRule = (RemoveRule) applicationContext.getBean("removeRule");
	    	removeRule.execute(ruleSet, packageName, ruleName);
    	}catch(Exception e) {
    		log.error(e);
    		e.printStackTrace();
    	}finally {
    		if(applicationContext != null) {
    			applicationContext.close(); applicationContext = null;
    		}
    	}
    }
    
    public void execute(String ruleSet, String packageName, String ruleName) {
        log.info("Starting RemoveRule Execution");
		
        DroolsRuleRemoveEvent droolsRuleRemoveEvent = new DroolsRuleRemoveEvent();
        droolsRuleRemoveEvent.setRuleSet(ruleSet);
        droolsRuleRemoveEvent.setPackageName(packageName);
        droolsRuleRemoveEvent.setRuleName(ruleName);
        droolsRuleRemoveEvent.setProcessed(false);
        
        gigaSpace.write(droolsRuleRemoveEvent);
        
        log.info("End RemoveRule Execution");
    }
    
}