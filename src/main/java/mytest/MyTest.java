package mytest;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.GSIterator;
import org.openspaces.core.IteratorBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.xap.repository.support.XapRepositoryFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

/**
 * @author Oleksiy_Dyagilev
 */
public class MyTest {
    public static void main(String[] args) {
//        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/space");
//        GigaSpace space = new GigaSpaceConfigurer(urlSpaceConfigurer).create();
//
//        Person p1 = new Person("1", "aaa");
//
//        XapRepositoryFactory f = new XapRepositoryFactory(space, null);
//        PersonRepository repository = f.getRepository(PersonRepository.class);
//
//        repository.save(p1);
//        repository.delete("1");
//
//        System.out.println(repository);


        FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("//home/pivot/Projects/xap-spring-data/src/main/java/mytest/test-context.xml");


//        ISpaceProxy iSpaceProxy;
//        iSpaceProxy.

        System.out.println(appContext);

        PersonService personService = appContext.getBeansOfType(PersonService.class).values().iterator().next();
        personService.addPerson(new Person("1", "aaaaa"));

        System.exit(0);
    }
}
