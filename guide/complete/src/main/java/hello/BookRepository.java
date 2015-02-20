package hello;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findByAuthor(String author);

    List<Book> findByCopiesLessThan(Integer copies);

    List<Book> findByAuthorOrCopiesGreaterThan(String author, Integer copies);

}
