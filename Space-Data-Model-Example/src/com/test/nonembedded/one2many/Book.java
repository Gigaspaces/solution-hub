package com.test.nonembedded.one2many;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

@SpaceClass
public class Book {
	Integer id;
	Integer authorId;
	String title;

	@SpaceId (autoGenerate=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@SpaceIndex
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	@SpaceIndex
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	

	@Override
	public String toString() {
		return "Book [id=" + id + ", authorId=" + authorId + "]";
	}
	
}
