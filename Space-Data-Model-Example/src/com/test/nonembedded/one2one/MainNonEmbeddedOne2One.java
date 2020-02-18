package com.test.nonembedded.one2one;

import java.util.ArrayList;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import com.j_spaces.core.client.SQLQuery;

public class MainNonEmbeddedOne2One {


	public static void main(String[] args) {
		GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./spaceNonEmbeddedModel")).gigaSpace();
		System.out.println("-------------  One 2 One - NON-EMBEDDED MODE Example -------------");
		
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
			
			author.setBookId(book1.getId());
			book1.setAuthorId(i);
			
			space.write(book1);
			space.write(author);
			
			if (i%100==0)
				System.out.print(".");
		}
		
		System.out.println();
		System.out.println("we have " + space.count(new Book()) + " Books in the space");
		System.out.println("we have " + space.count(new Author()) + " Authors in the space");
		
		for (int i=0;i<10;i++)
		{
			SQLQuery<Author> query = new SQLQuery <Author>(Author.class , "lastName=?");
			query.setParameter(1, "Author" + i);
			long start = System.nanoTime();
			Author authors [] = space.readMultiple(query);
			ArrayList<Book> books = new ArrayList<Book>() ;
			
			// read the Author Book via its ID
			for (int j=0;j<authors.length;j++)
			{
				Book book = space.readById(Book.class , authors[j].getBookId());
				books.add(book);
			}
			
			long end = System.nanoTime();
			double durationMS = (double )(end-start)/(double)1000000;
			
			System.out.println("Query : lastName=" + "Author" + i + 
					" - Found " + authors.length + " authors with "+books.size() + " Books - Query Time[microsecond]:" + durationMS + " books:" + books);
		}
		
		System.out.println("Query: select * from Author where lastName=? and books.title=?");
		for (int i=0;i<10;i++)
		{
			long start = System.nanoTime();
			String authoridsForTitle = "";
			SQLQuery<Book> bookQuery = new SQLQuery <Book>(Book.class , "title=?");
			bookQuery.setParameter(1, "Book" + i);
			Book booksFounds [] = space.readMultiple(bookQuery);
			for (int j = 0; j < booksFounds.length; j++) {
				Book book = booksFounds[j];
				authoridsForTitle = authoridsForTitle + book.getAuthorId().toString() ;
				if ((j +1)!= booksFounds.length)
					authoridsForTitle = authoridsForTitle + ",";
			}
			
			SQLQuery<Author> query = new SQLQuery <Author>(Author.class , "lastName=? AND id IN ("+ authoridsForTitle+")");
			query.setParameter(1, "Author" + i);
			
			Author authorFounds [] = space.readMultiple(query);
			long end = System.nanoTime();
			double durationMS = (double )(end-start)/(double)1000000;
			System.out.println("Query : lastName=" + "Author" + i + " - Found " + authorFounds.length + " - Query Time[microsecond]:" + durationMS);
		}
	}
}
