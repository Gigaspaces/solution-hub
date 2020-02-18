package com.test.nonembedded.one2many;

import java.util.List;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

@SpaceClass
public class Author {
    Integer id;
    String lastName;
    List<Integer> bookIds;
    
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
	
	public List<Integer> getBookIds() {
		return bookIds;
	}
	public void setBookIds(List<Integer> bookIds) {
		this.bookIds = bookIds;
	}

}