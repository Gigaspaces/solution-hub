package com.test.embedded.one2one;

import java.util.HashSet;
import java.util.Set;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.client.SQLQuery;

public class MainEmbeddedOne2One {

	public static void main(String[] args) {
		
		GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./spaceEmbeddedModel")).gigaSpace();
		System.out.println("-------------  One 2 One - EMBEDDED MODE Example -------------");
		int max = 1000;
		System.out.println("writing "  + (max) + " books and " + max+ " Authors");

		for (int i=0;i<max;i++)
		{
			Book book1 = new Book();
			book1.setId(i);
			book1.setTitle("Book"+i);
			
			Author author  = new Author();
			author.setId(i);
			author.setLastName("Author" + i);
			author.setBook(book1);
			
			space.write(author);
			if (i%100==0)
				System.out.print(".");
		}
		
		System.out.println();
		System.out.println("we have " + space.count(new Author()) + " Books in the space");
		
		System.out.println("Query: select * from Author where lastName=?");
		for (int i=0;i<10;i++)
		{
			SQLQuery<Author> query = new SQLQuery <Author>(Author.class , "lastName=?");
			query.setParameter(1, "Author" + i);
			long start = System.nanoTime();
			
			Author authorFounds [] = space.readMultiple(query);
			Set<Book> booksFound = new HashSet<Book> ();
			for (int j = 0; j < authorFounds.length; j++) {
				booksFound.add(authorFounds[j].getBook());
			}
			
			long end = System.nanoTime();
			double durationMS = (double )(end-start)/(double)1000000;
			System.out.println("Query : lastName=" + "Author" + i + " - Found " + authorFounds.length + " with "  +  booksFound.size()+ " Books - Query Time[microsecond]:" + durationMS + " books:"+booksFound);
		}

		System.out.println("Query: select * from Author where lastName=? and books.title=?");
		for (int i=0;i<10;i++)
		{
			SQLQuery<Author> query = new SQLQuery <Author>(Author.class , "lastName=? and book.title=?");
			query.setParameter(1, "Author" + i);
			query.setParameter(2, "Book" + i);
			long start = System.nanoTime();
			Author authorFounds [] = space.readMultiple(query);
			Set<Book> booksFound = new HashSet<Book> ();
			for (int j = 0; j < authorFounds.length; j++) {
				Author author = authorFounds[j];
				booksFound.add(author.getBook());
			}
			
			long end = System.nanoTime();
			double durationMS = (double )(end-start)/(double)1000000;
			System.out.println("Query : lastName=" + "Author" + i + " - Found " + authorFounds.length + " with "  +  booksFound.size()+ " Books - Query Time[microsecond]:" + durationMS + " books:"+booksFound);
		}
	}
}
