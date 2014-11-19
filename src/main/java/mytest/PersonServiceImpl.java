package mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Oleksiy_Dyagilev
 */
@Component
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void addPerson(Person p) {
        personRepository.save(p);
    }
}
