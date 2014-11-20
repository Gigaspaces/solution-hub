package mytest;

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


        FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext("D:\\xap\\SPRING\\xap-spring-data\\src\\main\\java\\mytest\\test-context.xml");


//        ISpaceProxy iSpaceProxy;
//        iSpaceProxy.

        System.out.println(appContext);

        PersonService personService = appContext.getBeansOfType(PersonService.class).values().iterator().next();
        personService.addPerson(new Person("1", "aaaaa"));

        System.exit(0);
    }
}
