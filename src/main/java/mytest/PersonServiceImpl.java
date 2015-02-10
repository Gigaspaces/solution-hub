package mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Person> findPersons(int page, int size) {
        PageRequest pageable = new PageRequest(page, size, new Sort(new Sort.Order(Sort.Direction.ASC, "id")));

        Page<Person> all = personRepository.findAll(pageable);
        List<Person> result = new ArrayList<>();
        for (Person person : all){
            result.add(person);
        }
        return result;
    }

}
