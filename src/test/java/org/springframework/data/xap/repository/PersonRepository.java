package org.springframework.data.xap.repository;

import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.XapRepository;


/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends XapRepository<Person, String> {
}
