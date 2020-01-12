package com.gigaspaces.droolsintegration.dao;

import java.util.Date;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.stereotype.Component;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.droolsintegration.model.drools.DroolsRule;
import com.gigaspaces.droolsintegration.model.drools.KnowledgePackage;
import com.gigaspaces.query.IdQuery;

@Component
public class KnowledgePackageDao {
	
	@GigaSpaceContext(name = "gigaSpace")
	private GigaSpace gigaSpace;
	
	public void write(KnowledgePackage knowledgePackage) {
		gigaSpace.write(knowledgePackage);	
	}
	
	public KnowledgePackage read(String ruleSet, String knowledgePackageName) {
		KnowledgePackage knowledgePackage = new KnowledgePackage();
		knowledgePackage.setRuleSet(ruleSet);
		knowledgePackage.setPackageName(knowledgePackageName);
		
		return gigaSpace.read(knowledgePackage);
	}
	
	public void clear(KnowledgePackage knowledgePackage) {
		gigaSpace.clear(knowledgePackage);
	}
	
	public void addRule(KnowledgePackage knowledgePackage, String ruleName, DroolsRule rule) {
		IdQuery<KnowledgePackage> idQuery = new IdQuery<KnowledgePackage>(KnowledgePackage.class, knowledgePackage.getId(), knowledgePackage.getRuleSet());
		gigaSpace.change(idQuery, new ChangeSet().set("lastUpdateDate", new Date(System.currentTimeMillis()))
												 .putInMap("rules", ruleName, rule)
												 .increment("totalRules" , 1));
	}
	
	public void removeRule(KnowledgePackage knowledgePackage, String ruleName) {
		IdQuery<KnowledgePackage> idQuery = new IdQuery<KnowledgePackage>(KnowledgePackage.class, knowledgePackage.getId(), knowledgePackage.getRuleSet());
		gigaSpace.change(idQuery, new ChangeSet().removeFromMap("rules", ruleName)
												 .decrement("totalRules" , 1));
	}
	
}