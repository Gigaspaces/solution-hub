Accessing data with Spring Data XAP
=====

This guide walks you through the process of building an application with GigaSpaces XAP.

## What you'll build

You will use the powerful Spring Data XAP library to store and retrieve POJOs.

## What you'll need

Before using this guide, you have to download GigaSpaces XAP and install maven plugin.   
To install maven plugin run the next script:   

```
*Windows*
/tools/maven/installmavenrep.bat

*Unix*
/tools/maven/installmavenrep.sh
```   

Also you have to download spring-data-xap project and build it with maven using `mvn clean install`.   

If you’re not familiar with Maven, refer to [Building Java Projects with Maven] (https://spring.io/guides/gs/maven/).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

`pom.xml`

```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.springframework</groupId>
    <artifactId>gs-accessing-data-xap-complete</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>

        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-xap</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>1.2.1.RELEASE</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.7</source>
                    <target>1.7</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>org.openspaces</id>
            <url>http://maven-repository.openspaces.org</url>
        </repository>
        <repository>
            <id>spring-releases</id>
            <url>https://repo.spring.io/libs-release</url>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>org.openspaces</id>
            <url>http://maven-repository.openspaces.org</url>
        </pluginRepository>
        <pluginRepository>
            <id>spring-releases</id>
            <url>https://repo.spring.io/libs-release</url>
        </pluginRepository>
    </pluginRepositories>

</project>
```


## Define a simple entity
GigaSpaces XAP is in-memory data grid. It stores data into spaces, and each space can be configured in different manner(replicated, partitioned etc).
For this guide you use an embedded space so you don't have to set up anything extra.

In this example, you store Book objects with a few annotations.

```java

package hello;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.io.Serializable;

@SpaceClass
public class Book implements Serializable {

    String id;

    String author;

    Integer copies;

    public Book(){}

    public Book(String id, String author, Integer copies) {
        this.id = id;
        this.author = author;
        this.copies = copies;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @SpaceId(autoGenerate = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (copies != null ? !copies.equals(book.copies) : book.copies != null) return false;
        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (copies != null ? copies.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", copies=" + copies +
                '}';
    }
}
```

Here you have a `Book` class with three attributes, the `id`, the `author` and the `copies`. You also have the constructor to populate the entities when creating a new instance and the default constructor.

Notice that this class is annotated `@SpaceClass`. This annotation is not mandatory, you can write netity to the space without it, but it's used to provide an additional metadata.
This class also has getter for `id` marked with `@SpaceId`. This property uniquely identifies the entity within the space, and is similar to a primary key in a database.

The next important piece is the number of book's copies. Later in this guide, you will use it to fashion some queries.

The convenient `toString()` method will print out the book's id, author and copies.

## Create simple queries
Spring Data XAP focuses on storing data in XAP. It also inherits powerful functionality from the Spring Data Commons project, such as the ability to derive queries. Essentially, you don't have to learn the query language of GigaSpaces XAP; you can simply write a handful of methods and the queries are written for you.

To see how this works, create an interface that queries `Book` space objects.
 
```java

package hello;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findByAuthor(String author);

    List<Book> findByCopiesLessThan(Integer copies);

    List<Book> findByAuthorOrCopiesGreaterThan(String author, Integer copies);

}
```
   
`BookRepository` extends the `CrudRepository` interface and plugs in the type of values and keys it works with: `Book` and `String`. Out-of-the-box, this interface comes with many operations, including standard CRUD (create-read-update-delete).

You can define other queries as needed by simply declaring their method signature. In this case, you add `findByAuthor`, which essentially seeks entities of type `Book` and find the ones that matches on `Author`.

You also have:

- `findByCopiesLessThan` to find books with a number of copies below a certain number
- `findByAuthorOrCopiesGreaterThan` to find books with a certain author or number of copies above a certain number

Let's wire this up and see what it looks like!

## Create an application class
Here you create an Application class with all the components.

``` java

package hello;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.repository.support.XapRepositoryFactory;

import java.io.IOException;

@Configuration
@EnableXapRepositories
public class Application implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Bean
    GigaSpace spaceClient(){
        UrlSpaceConfigurer urlConfigurer = new UrlSpaceConfigurer("/./testSpace");
        return new GigaSpaceConfigurer(urlConfigurer).gigaSpace();
    }

    @Bean
    BookRepository bookRepository(){
        RepositoryFactorySupport factory = new XapRepositoryFactory(spaceClient(), null);
        return factory.getRepository(BookRepository.class);
    }

    @Override
    public void run(String... strings) throws Exception {

        Book thinkingInJava = new Book("1234", "Eccel", 10_000);
        Book effectiveJava = new Book("2345", "Bloch", 20_000);
        Book springInAction = new Book("3456", "Walls", 50_000);

        System.out.println("Before writing objects to space...");
        for (Book book : new Book[] { thinkingInJava, effectiveJava, springInAction }) {
            System.out.println("\t" + book);
        }

        bookRepository.save(thinkingInJava);
        bookRepository.save(effectiveJava);
        bookRepository.save(springInAction);

        System.out.println("Lookup books by author...");
        for (String name : new String[] { thinkingInJava.author, effectiveJava.author, effectiveJava.author}) {
            System.out.println("\t" + bookRepository.findByAuthor(name));
        }

        System.out.println("Lookup for less popular books...");
        for (Book book : bookRepository.findByCopiesLessThan(15_000)) {
            System.out.println("\t" + book);
        }

        System.out.println("Lookup for popular books or books of specific author...");
        for (Book book : bookRepository.findByAuthorOrCopiesGreaterThan("Bloch", 30_000)) {
            System.out.println("\t" + book);
        }

        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
```


In the configuration, you need to add the `@EnableXapRepositories` annotation.

XAP space is required to store all data. For that, you have Spring Data XAP convenient `SpaceClient` bean.

NOTE: In this guide, the space is created locally using built-in components and an evaluation license. For a production solution, Spring recommends the production version of XAP, where you can create distributed spaces across multiple nodes.

NOTE: The types are `<String, Book>`, matching the key type (`String`) with the value type (`Book`).

The `public static void main` uses Spring Boot's `SpringApplication.run()` to launch the application and invoke the `CommandLineRunner` that builds the relationships.

The application autowires an instance of `BookRepository` that you just defined. Spring Data XAP will dynamically create a concrete class that implements that interface and will plug in the needed query code to meet the interface's obligations. This repository instance is the used by the `run()` method to demonstrate the functionality.

## Store and fetch data
In this guide, you are creating three local `Book` s, **SpringInAction**, **ThinkingInJava**, and **EffectiveJava**. Initially, they only exist in memory. After creating them, you have to save them to XAP.

Now you run several queries. The first looks up books by author. Then you execute query to find less popular books and another query to find popular books or books written by specific author.

You should see something like this:
   
    Before writing objects to space...
        Book{id='1234', author='Eccel', copies=10000}
        Book{id='2345', author='Bloch', copies=20000}
        Book{id='3456', author='Walls', copies=50000}
    Lookup books by author...
        [Book{id='1234', author='Eccel', copies=10000}]
        [Book{id='2345', author='Bloch', copies=20000}]
        [Book{id='2345', author='Bloch', copies=20000}]
    Lookup for less popular books...
        Book{id='1234', author='Eccel', copies=10000}
    Lookup for popular books or books of specific author...
        Book{id='2345', author='Bloch', copies=20000}
        Book{id='3456', author='Walls', copies=50000}
   

## Summary
Congratulations! You set up an embedded XAP space, stored simple entities, and developed simple queries.
