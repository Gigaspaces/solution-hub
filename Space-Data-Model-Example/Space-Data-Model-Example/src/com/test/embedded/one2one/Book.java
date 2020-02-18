package com.test.embedded.one2one;

import java.io.Serializable;

public class Book implements Serializable{
	Integer id;
	String title;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + "]";
	}
}
