package mytest;

import com.gigaspaces.client.iterator.GSIteratorConfig;
import com.gigaspaces.client.iterator.IteratorScope;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.GSIterator;
import com.j_spaces.core.client.SQLQuery;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.domain.Sort;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author Oleksiy_Dyagilev
 */
public class MyTest {
    public static void main(String[] args) throws TransactionException, UnusableEntryException, RemoteException, InterruptedException {
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



       FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("//home/adminuser/projects/gigaspaces/xap-spring-data/src/main/java/mytest/test-context.xml");

        //FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("//home/pivot/Projects/xap-spring-data/src/main/java/mytest/test-context.xml");
        System.out.println(appContext);
        PersonService personService = appContext.getBeansOfType(PersonService.class).values().iterator().next();
        for (int i = 0; i < 100; i++){
            personService.addPerson(new Person(String.valueOf(i), "aaa2aa"));
        }
       // List<Person> persons = personService.findPersons(3, 10);


        IJSpace ijSpace = appContext.getBean("space", IJSpace.class);
        List<Object> templates = new ArrayList<Object>();
        SQLQuery<Person> sqlQuery = new SQLQuery<>(Person.class, "ORDER BY id ASC");

        // simple SQLQuery works successfully
        Object[] objects = ijSpace.readMultiple(sqlQuery, null, 100);

        // GSIterator creation fails
        templates.add(sqlQuery);
        GSIteratorConfig config = new GSIteratorConfig();
        config.setIteratorScope(IteratorScope.CURRENT);
        GSIterator gsIterator = new GSIterator(ijSpace, templates, config);
        gsIterator.hasNext();


//        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConf.class);
//        PersonRepository repo = context.getBeansOfType(PersonRepository.class).values().iterator().next();
//        System.out.println(repo);
//        repo.save(new Person("3", "aaaaa"));

        System.exit(0);
    }
}
