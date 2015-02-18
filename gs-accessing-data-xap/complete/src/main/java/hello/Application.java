package hello;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.xap.repository.config.EnableXapRepositories;
import org.springframework.data.xap.repository.support.XapRepositoryFactory;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.IOException;

@Configuration
@EnableXapRepositories
public class Application implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Bean
    SpaceClient spaceClient(){
        ISpaceProxy space = null;
        try {
            space = (ISpaceProxy) SpaceFinder.find("jini://*/*/mySpace");
        } catch (FinderException e) {
            throw new RuntimeException("Unable to find space instance for testing", e);
        }
        SpaceClient spaceClient = new SpaceClient();
        spaceClient.setSpace(space);
        return spaceClient;
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
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
