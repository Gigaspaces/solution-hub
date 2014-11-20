package mytest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


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



       // FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("D:\\xap\\SPRING\\xap-spring-data\\src\\main\\java\\mytest\\test-context.xml");

//        //FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("//home/pivot/Projects/xap-spring-data/src/main/java/mytest/test-context.xml");
//        System.out.println(appContext);
//        PersonService personService = appContext.getBeansOfType(PersonService.class).values().iterator().next();
//        personService.addPerson(new Person("2", "aaa2aa"));

//
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConf.class);
        PersonRepository repo = context.getBeansOfType(PersonRepository.class).values().iterator().next();
        System.out.println(repo);
        repo.save(new Person("3", "aaaaa"));

        System.exit(0);
    }
}
