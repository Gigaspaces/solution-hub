package com.gigaspaces.droolsintegration.model.facts;

import java.io.Serializable;

public class Holiday implements Serializable {

	private static final long serialVersionUID = -6984009714990801688L;
	
	private String name;
    private String when;

    public Holiday() {}

    public Holiday(String name, String when) {
        this.name = name;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

}