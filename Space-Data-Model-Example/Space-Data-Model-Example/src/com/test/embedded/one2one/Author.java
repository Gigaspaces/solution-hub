package com.test.embedded.one2one;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;

public class Author {
    Integer id;
    String lastName;
    Book book;
    
    @SpaceId
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
	
	@SpaceIndex(path = "title")
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", lastName=" + lastName + ", book=" + book+ "]";
	}
}
