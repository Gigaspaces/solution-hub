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
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
    }
}
