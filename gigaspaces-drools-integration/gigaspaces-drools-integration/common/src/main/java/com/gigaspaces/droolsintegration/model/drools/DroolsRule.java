package com.gigaspaces.droolsintegration.model.drools;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

import java.io.Serializable;
import java.util.Date;


@SpaceClass
public class DroolsRule implements Serializable {

    private static final long serialVersionUID = -1045963978987889918L;

    private String id;
    private String ruleSet;
    private String ruleName;
    private String originalResourceType;
    private Date createDate;

  
    public DroolsRule() {}
    
    public DroolsRule(String ruleName) {
    	this.ruleName = ruleName;
    }

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }
    
    @SpaceRouting
    public String getRuleSet() {
		return ruleSet;
	}
    
    @SpaceIndex(type=SpaceIndexType.BASIC)
    public String getRuleName() {
        return ruleName;
    }

	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getOriginalResourceType() {
		return originalResourceType;
	}

	public void setOriginalResourceType(String originalResourceType) {
		this.originalResourceType = originalResourceType;
	}

	public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}