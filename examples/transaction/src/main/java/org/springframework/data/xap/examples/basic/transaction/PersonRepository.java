package org.springframework.data.xap.examples.basic.transaction;

import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, Integer>{
}
