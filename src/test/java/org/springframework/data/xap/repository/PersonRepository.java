package org.springframework.data.xap.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.xap.model.Person;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends XapRepository<Person, String>{

    @Query("name=?")
    Person findByName(String name);

}
