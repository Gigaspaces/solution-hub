package com.gigaspaces.droolsintegration.model.drools;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.io.Serializable;


@SpaceClass
public class DroolsRuleAddEvent implements Serializable {

	private static final long serialVersionUID = 4287824699802749425L;
	
	private String id;
    private String ruleSet;
    private String ruleName;
    private String packageName;
    private String originalResourceType;
    private Boolean processed;
    private byte[] ruleBytes;
  
    public DroolsRuleAddEvent() {}

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }
    
    @SpaceRouting
    public String getRuleSet() {
		return ruleSet;
	}
    
    @SpaceIndex
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

	public Boolean isProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	
	public byte[] getRuleBytes() {
		return ruleBytes;
	}

	public void setRuleBytes(byte[] ruleBytes) {
		this.ruleBytes = ruleBytes;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}