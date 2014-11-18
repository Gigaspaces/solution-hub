import org.springframework.data.xap.repository.support.XapRepositoryFactory;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

/**
 * @author Oleksiy_Dyagilev
 */
public class MyTest {
    public static void main(String[] args) {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("jini://*/*/space");
        GigaSpace space = new GigaSpaceConfigurer(urlSpaceConfigurer).create();
////
//        space.write(new Person("1", "aaa"));
//        space.write(new Person("2", "bbb"));
//        space.write(new Person("3", "ccc"));
//        space.write(new Person("4", "1111111111"));
//
//
//        Class<Person> aClass = Person.class;
////        SQLQuery<Person> query = new SQLQuery<>(aClass, "");
////        Person[] persons = space.readMultiple(query);
////        System.out.println("persons = " + Arrays.toString(persons));
//
////        space.a
//
////        SQLQuery<Person> query = new SQLQuery<>(aClass, "");
////        long count = QueryExtension.count(space, query, "");
////        System.out.println(count);
//
//        IdQuery<Person> personIdQuery = new IdQuery<Person>(aClass, "4");
//        personIdQuery.setProjections("");
//
//        Person person = space.takeById(personIdQuery);
//        System.out.println(person);

        Person p1 = new Person("1", "aaa");

        XapRepositoryFactory f = new XapRepositoryFactory(space, null);
        PersonRepository repository = f.getRepository(PersonRepository.class);

        repository.save(p1);
        repository.delete("1");

        System.out.println(repository);


    }
}
