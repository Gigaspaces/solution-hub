package hello;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.io.Serializable;

@SpaceClass
public class Book implements Serializable {
    String id;
    String author;
    Integer copies;

    public Book() {
    }

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
    public String toString() {
        return "Book{" + "id='" + id + '\'' + ", author='" + author + '\'' + ", copies=" + copies + '}';
    }
}