package com.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.jdbc.driver.GConnection;

public class MainJDBC {

	public static void main(String[] args) throws Exception{
		GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./space")).gigaSpace();
		System.out.println("-------------  NON-EMBEDDED MODE JDBC Example -------------");
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
			
			book1.setAuthorId(i);
			book2.setAuthorId(i);
			
			space.write(book1);
			space.write(book2);
			space.write(author);
			
			if (i%100==0)
				System.out.print(".");
		}
		
		System.out.println();
		System.out.println("we have " + space.count(new Book()) + " Books in the space");
		System.out.println("we have " + space.count(new Author()) + " Authors in the space");

		Class.forName("com.j_spaces.jdbc.driver.GDriver").newInstance();
		String url = "jdbc:gigaspaces:url:jini://*/*/space";
		Connection conn = DriverManager.getConnection(url);
//		GConnection conn = GConnection.getInstance(space.getSpace());
		
		for (int i=0;i<10;i++)
		{
			Statement statement= conn.createStatement();
			long start = System.nanoTime();
			ResultSet result = statement.executeQuery("select com.test.jdbc.Book.id , " +
						"com.test.jdbc.Author.id,com.test.jdbc.Author.lastName "+ 
						" from com.test.jdbc.Book," +
						" com.test.jdbc.Author " +
						" WHERE com.test.jdbc.Author.lastName='Author" + i+
						"' AND com.test.jdbc.Book.authorId = com.test.jdbc.Author.id");
			long end = System.nanoTime();

			Set<Integer> booksSet = new HashSet<Integer>();
			Set<Integer> authorSet = new HashSet<Integer>();
			
			while (result.next()) {
				booksSet.add(result.getInt(1));
				authorSet.add(result.getInt(2));
			}
			statement.close();
			
			double durationMS = (double )(end-start)/(double)1000000;
			
			System.out.println("Query : lastName=" + "Author" + i + 
					" - Found " + authorSet.size() + " authors - with " +booksSet.size() + " Books - Query Time[microsecond]:" + durationMS + " books:" + booksSet);

		}
		conn.close();
	}
}
