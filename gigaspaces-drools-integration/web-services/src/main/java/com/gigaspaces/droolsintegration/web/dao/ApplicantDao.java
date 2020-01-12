package com.gigaspaces.droolsintegration.web.dao;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.model.facts.Applicant;

@Component
public class ApplicantDao {

	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	public Applicant readById(Integer id) {
		return gigaSpace.readById(Applicant.class, id);
	}
	
}