package com.test.embedded.one2many;

import java.util.List;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceIndexes;

public class Author {
    Integer id;
    String lastName;
    List<Book> books;
    
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
	
	@Override
	public String toString() {
		return "Author [id=" + id + ", lastName=" + lastName + "]";
	}

	@SpaceIndex(path = "[*].title")
	public List<Book> getBooks() {
		return books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
