package com.gigaspaces.droolsintegration.model.drools;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


@SpaceClass
public class KnowledgePackage implements Serializable {

	private static final long serialVersionUID = -7320869702574658090L;
	
	private String id;
    private String ruleSet;
    private String packageName;
    private Map<String, DroolsRule> rules;
    private Date createDate;
    private Date lastUpdateDate;
    private Integer totalRules;

  
    public KnowledgePackage() {}
    
    public KnowledgePackage(String packageName) {
    	this.packageName = packageName;
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
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Map<String, DroolsRule> getRules() {
		return rules;
	}

	public void setRules(Map<String, DroolsRule> rules) {
		this.rules = rules;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRuleSet(String ruleSet) {
		this.ruleSet = ruleSet;
	}

	public Integer getTotalRules() {
		return totalRules;
	}

	public void setTotalRules(Integer totalRules) {
		this.totalRules = totalRules;
	}
	
}