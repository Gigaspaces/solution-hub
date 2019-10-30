package org.springframework.data.xap.examples.simple;

import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, Integer>{
}
