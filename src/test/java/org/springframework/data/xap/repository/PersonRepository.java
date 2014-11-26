package org.springframework.data.xap.test.repository;

import org.springframework.data.xap.repository.XapRepository;
import org.springframework.data.xap.test.model.Person;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends XapRepository<Person, String> {
}
