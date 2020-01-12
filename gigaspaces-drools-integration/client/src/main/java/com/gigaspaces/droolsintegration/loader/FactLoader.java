package com.gigaspaces.droolsintegration.loader;

import org.apache.log4j.Logger;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.model.facts.Applicant;
import com.gigaspaces.droolsintegration.util.DataGenerationUtils;

@Component
public class FactLoader {

	private static Logger log = Logger.getLogger(FactLoader.class);  
	
	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	public static void main(String[] args) {    	
    	ClassPathXmlApplicationContext applicationContext = null;
    	try {    		
    		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	    	
	    	int count = 40;
	    	if(args != null && args.length > 0) {
	    		count = Integer.parseInt(args[0]);
	    	}
	    	
	    	FactLoader factLoader = (FactLoader) applicationContext.getBean("factLoader");
	    	factLoader.execute(count);
    	}catch(Exception e) {
    		log.error(e);
    	}finally {
    		if(applicationContext != null) {
    			applicationContext.close(); applicationContext = null;
    		}
    	}
	}
	
	private void execute(int count) {
		for(int i=0; i<count; i++) {
			Applicant applicant = new Applicant();
			applicant.setId(i);
			applicant.setName(DataGenerationUtils.pickRandomName());
			applicant.setAge(DataGenerationUtils.pickRandomAge());
			
			gigaSpace.write(applicant);
		}
		
		System.out.println("Populated Applicants to the Space\n");
	}
	
}