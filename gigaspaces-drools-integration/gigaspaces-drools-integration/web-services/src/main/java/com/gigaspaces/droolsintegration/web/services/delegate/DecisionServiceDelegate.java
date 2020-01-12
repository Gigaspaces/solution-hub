package com.gigaspaces.droolsintegration.web.services.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.model.facts.Application;
import com.gigaspaces.droolsintegration.web.services.ApplicationService;
import com.gigaspaces.droolsintegration.web.services.HolidayDecisionService;

@Component
public class DecisionServiceDelegate {
	
	@Autowired
	private HolidayDecisionService holidayDecisionService;
	
	@Autowired
	private ApplicationService applicationService;
	
	public void holidayDecisionService(String name, String month) {
		holidayDecisionService.checkIfItsJuly(name, month);
	}
	
	public Application processApplicationService(Integer id) {
		return applicationService.processApplication(id);
	}
	
}