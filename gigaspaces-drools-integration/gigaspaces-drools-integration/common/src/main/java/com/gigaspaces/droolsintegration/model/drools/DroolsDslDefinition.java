package com.gigaspaces.droolsintegration.model.drools;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * The contents of a single DSL file.
 */
@SpaceClass(persist = false, replicate = false)
public class DroolsDslDefinition implements Serializable {

    private static final long serialVersionUID = -7187529469674707974L;

    private String id;
    private String ruleSet;
    private byte[] dslBytes;

  
    public DroolsDslDefinition() {}

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

    public byte[] getDslBytes() {
        return dslBytes;
    }

    public void setDslBytes(byte[] dslBytes) {
        this.dslBytes = dslBytes.clone();
    }

}