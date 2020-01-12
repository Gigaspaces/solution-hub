package com.gigaspaces.droolsintegration.model.drools;

import java.io.Serializable;

import org.drools.KnowledgeBase;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;


@SpaceClass
public class KnowledgeBaseWrapper implements Serializable {

    private static final long serialVersionUID = 3738043534134325175L;

    private String id;
    private String ruleSet;
    private KnowledgeBase knowledgeBase;
    private Integer totalKnowledgePackages;
    private Integer totalRules;
   
    public KnowledgeBaseWrapper() {}
    
    public KnowledgeBaseWrapper(String ruleSet) {
    	this.ruleSet = ruleSet;
    }

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }
    
    @SpaceRouting
    public String getRuleSet() {
		return ruleSet;
	}
    
    public void setId(String id) {
        this.id = id;
    }

	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}

	public KnowledgeBase getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}

	public Integer getTotalRules() {
		return totalRules;
	}

	public void setTotalRules(Integer totalRules) {
		this.totalRules = totalRules;
	}

	public Integer getTotalKnowledgePackages() {
		return totalKnowledgePackages;
	}

	public void setTotalKnowledgePackages(Integer totalKnowledgePackages) {
		this.totalKnowledgePackages = totalKnowledgePackages;
	}

}