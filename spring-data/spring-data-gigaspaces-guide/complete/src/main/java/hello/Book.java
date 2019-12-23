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
