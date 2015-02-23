package org.springframework.data.xap.examples.repository;

import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, Integer> {
    List<Person> findByPositionAndActiveTrue(String position);
}
