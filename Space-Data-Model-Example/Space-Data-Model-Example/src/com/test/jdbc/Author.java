package com.test.jdbc;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

@SpaceClass
public class Author {
    Integer id;
    String lastName;
    
    @SpaceId(autoGenerate=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@SpaceIndex
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", lastName=" + lastName + "]";
	}	
}