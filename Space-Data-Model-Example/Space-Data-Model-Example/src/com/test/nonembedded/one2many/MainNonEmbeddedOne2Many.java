package com.test.nonembedded.one2many;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import com.j_spaces.core.client.SQLQuery;

public class MainNonEmbeddedOne2Many {


	public static void main(String[] args) {
		GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./spaceNonEmbeddedModel")).gigaSpace();
		System.out.println("-------------  One 2 Many - NON-EMBEDDED MODE Example -------------");
		
		int max = 1000;
		System.out.println("writing "  + (max *2) + " books and " + max+ " Authors");
		int bookIdCounter = 0;
		
		for (int i=0;i<max;i++)
		{
			Book book1 = new Book();
			book1.setId(bookIdCounter ++);
			book1.setTitle("Book"+bookIdCounter);

			Book book2 = new Book();
			book2.setId(bookIdCounter ++);
			book2.setTitle("Book"+bookIdCounter);
			
			Author author  = new Author();
			author.setId(i);
			author.setLastName("Author" + i);
			
			List<Integer> booksIds = new ArrayList<Integer>();
			booksIds.add(book1.getId());
			booksIds.add(book2.getId());
			
			author.setBookIds(booksIds);
			book1.setAuthorId(i);
			book2.setAuthorId(i);
			
			space.write(book1);
			space.write(book2);
			space.write(author);
			
			if (i%10000==0)
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
			ArrayList<Book> booksFound= new ArrayList<Book>() ;
			
			// read all the Author Books via their IDs
			for (int j=0;j<authors.length;j++)
			{
				Integer ids [] = new Integer[authors[j].getBookIds().size()];
				ids  = authors[j].getBookIds().toArray(ids);
				Iterator<Book> bookIter = space.readByIds(Book.class ,ids).iterator();
				while (bookIter.hasNext()) {
					booksFound.add((Book) bookIter.next());
				}
			}
			
			long end = System.nanoTime();
			double durationMS = (double )(end-start)/(double)1000000;
			
			System.out.println("Query : lastName=" + "Author" + i + 
					" - Found " + authors.length + " authors with "+booksFound.size() + " Books - Query Time[microsecond]:" + durationMS + " books:" + booksFound);
		}
		
		System.out.println("Query: select * from Author where lastName=? and books[*].title=?");
		for (int i=0;i<10;i++)
		{
			long start = System.nanoTime();
			String authoridsForTitle = "";
			SQLQuery<Book> bookQuery = new SQLQuery <Book>(Book.class , "title=?");
			bookQuery.setParameter(1, "Book" + ((i*2)+1));
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
