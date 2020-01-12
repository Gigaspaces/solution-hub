package com.gigaspaces.droolsintegration.rules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.remoting.ExecutorProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.util.IterableMapWrapper;
import com.gigaspaces.droolsintegration.util.RulesConstants;
import com.gigaspaces.droolsintegration.model.facts.Applicant;
import com.gigaspaces.droolsintegration.model.facts.Application;
import com.gigaspaces.droolsintegration.service.drools.IRulesExecutionService;


@Component
public class ExecuteRule  {
	
	private static Logger log = Logger.getLogger(ExecuteRule.class);

	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
    /**
     * Remoting service for executing business rules that are stored in the space.
     */
    @ExecutorProxy(gigaSpace = "gigaSpace")
    private IRulesExecutionService rulesExecutionService;
    
    /**
     * Main point of execution. Simply fires up the Spring context.
     *
     * @param args Command line args.
     */
    public static void main(String[] args) {  
        ClassPathXmlApplicationContext applicationContext = null;
    	try {    		
    		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    		ExecuteRule executeRule = (ExecuteRule) applicationContext.getBean("executeRule");
    		
    		Integer id;
	    	if(args != null && args.length > 0)
	    		id = new Integer(args[0]);
	    	else 
	    		id = 1;
    		
	    	executeRule.execute(id);
    	}catch(Exception e) {
    		log.error(e);
    	}finally {
    		if(applicationContext != null) {
    			applicationContext.close(); applicationContext = null;
    		}
    	}
    }
    
    public void execute(Integer id) {
        log.info("Starting ExecuteRules Execution");

        Applicant applicant = gigaSpace.readById(Applicant.class, id);
		
		Application application = new Application();
		application.setProcessed(false);
        
        IterableMapWrapper facts = new IterableMapWrapper();
		facts.put(Applicant.class.getSimpleName(), applicant);
        facts.put(Application.class.getSimpleName(), application);
       
        List<String> resultKeyList = new ArrayList<String>();
        resultKeyList.add(Application.class.getSimpleName());
        
        try {
        	IterableMapWrapper resultFacts = (IterableMapWrapper) rulesExecutionService.executeRulesWithLimitedResults(RulesConstants.RULE_SET_APPLICANT_AGE, facts, resultKeyList);
        	
        	Application result = (Application) resultFacts.get(Application.class.getSimpleName());
        	if(!result.getProcessed()) {
        		result.setApplicantApproved(false);
        		result.setApplicantId(applicant.getId());
        		result.setApplicantName(applicant.getName());
        	}
        	
        	log.info("Date Approved: " + result.getDateApproved());
        	log.info("Applicant Id: " + result.getApplicantId());
        	log.info("Applicant Name: " + result.getApplicantName());
        	log.info("Application Processed: " + result.getProcessed());
        	
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("End ExecuteRules Execution");
    }
    
}