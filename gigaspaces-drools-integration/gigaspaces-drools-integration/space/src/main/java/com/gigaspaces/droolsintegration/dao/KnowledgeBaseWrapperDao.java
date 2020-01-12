package com.gigaspaces.droolsintegration.dao;

import org.drools.builder.KnowledgeBuilder;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.droolsintegration.model.drools.KnowledgeBaseWrapper;

@Component
public class KnowledgeBaseWrapperDao {

	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	public KnowledgeBaseWrapper read(String ruleSet) {
		return gigaSpace.read(new KnowledgeBaseWrapper(ruleSet));
	}
	
	public void write(KnowledgeBaseWrapper knowledgeBaseWrapper) {
		gigaSpace.write(knowledgeBaseWrapper);
	}
	
	public void addKnowledgePackages(KnowledgeBaseWrapper knowledgeBaseWrapper, KnowledgeBuilder kbuilder) {
		knowledgeBaseWrapper.getKnowledgeBase().addKnowledgePackages(kbuilder.getKnowledgePackages());
		knowledgeBaseWrapper.setTotalKnowledgePackages(knowledgeBaseWrapper.getTotalKnowledgePackages() + 1);
        knowledgeBaseWrapper.setTotalRules(knowledgeBaseWrapper.getTotalRules() + 1);
		
        gigaSpace.write(knowledgeBaseWrapper);
	}
	
}