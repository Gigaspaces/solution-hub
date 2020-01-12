package com.gigaspaces.droolsintegration.web.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openspaces.remoting.ExecutorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.model.facts.Applicant;
import com.gigaspaces.droolsintegration.model.facts.Application;
import com.gigaspaces.droolsintegration.service.drools.IRulesExecutionService;
import com.gigaspaces.droolsintegration.util.IterableMapWrapper;
import com.gigaspaces.droolsintegration.util.RulesConstants;
import com.gigaspaces.droolsintegration.web.dao.ApplicantDao;

@Component
public class ApplicationService {

	private static Logger log = Logger.getLogger(HolidayDecisionService.class);

    @ExecutorProxy(gigaSpace = "gigaSpace")
    private IRulesExecutionService rulesExecutionService;
    
    @Autowired
    private ApplicantDao applicantDao;
	
	public ApplicationService() {}
	
	public Application processApplication(Integer id) {
		log.info("Starting ApplicationService Execution");
		        
		Applicant applicant = applicantDao.readById(id);
		
		IterableMapWrapper facts = new IterableMapWrapper();
		facts.put(Applicant.class.getSimpleName(), applicant);
		facts.put(Application.class.getSimpleName(), new Application(false, new Date()));
        
		List<String> resultKeyList = new ArrayList<String>();
        resultKeyList.add(Application.class.getSimpleName());
		
		IterableMapWrapper resultFacts = null;
        try {
        	resultFacts = (IterableMapWrapper) rulesExecutionService.executeRulesWithLimitedResults(RulesConstants.RULE_SET_APPLICANT_AGE, facts, resultKeyList);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
        }
        
        Application application = null;
        if(resultFacts != null) {
        	application = (Application) resultFacts.get(Application.class.getSimpleName());
        	
        	if(!application.getProcessed()) {
        		application.setApplicantApproved(false);
        		application.setApplicantId(applicant.getId());
        		application.setApplicantName(applicant.getName());
        	}
        }else {
        	log.error("Rules Execution Service returned a null as its collection of Facts");
        }
		
        log.info("End ApplicationService Execution");
        return application;
	}
	
}