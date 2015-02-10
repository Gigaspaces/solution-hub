package mytest;

import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonService {

    void addPerson(Person p);

    List<Person> findPersons(int page, int size);
}
