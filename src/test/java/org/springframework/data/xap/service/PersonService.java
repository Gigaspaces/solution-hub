package org.springframework.data.xap.service;



import org.springframework.data.xap.model.Person;

import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonService {
    public void addPerson(Person p);
    public void addPersons(List<Person> persons);
    public Person getById(String id);
    public boolean exists(String id);
    public List<Person> getAll();
    public List<Person> getAll(List<String> ids);
    public long count();
    public void delete(String id);
    public void deleteAll();
}
