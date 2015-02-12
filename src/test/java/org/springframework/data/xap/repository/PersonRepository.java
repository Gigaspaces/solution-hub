package org.springframework.data.xap.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.xap.model.Person;

import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends XapRepository<Person, String>{

    @Query("name=?")
    List<Person> findByName(String name);

    List<Person> findByAge(Integer age);

    List<Person> findByNameAndAge(String name, Integer age);

    List<Person> findByNameOrAge(String name, Integer age);
}
