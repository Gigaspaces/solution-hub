package com.test.nonembedded.one2one;

import java.io.Serializable;
import java.util.List;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

@SpaceClass
public class Author {
    Integer id;
    String lastName;
    Integer bookId;
    
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

	
	@SpaceIndex
	public Integer getBookId() {
		return bookId;
	}
	
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public String toString() {
		return "Author [id=" + id + ", lastName=" + lastName + ", bookId="
				+ bookId + "]";
	}
}