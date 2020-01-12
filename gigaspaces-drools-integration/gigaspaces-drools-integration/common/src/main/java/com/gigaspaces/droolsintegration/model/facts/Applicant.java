package com.gigaspaces.droolsintegration.model.facts;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
public class Applicant implements Serializable {

	private static final long serialVersionUID = 2947133252682372447L;
	
	private Integer id;
    private String name;
    private Integer age;

    
    public Applicant() {}
    
    public Applicant(String name) {
    	this.name = name;
    }

    @SpaceId(autoGenerate=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@SpaceRouting
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
    
}