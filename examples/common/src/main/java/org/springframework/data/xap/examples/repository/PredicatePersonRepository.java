package org.springframework.data.xap.examples.repository;

import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.querydsl.XapQueryDslPredicateExecutor;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface PredicatePersonRepository extends XapRepository<Person, String>, XapQueryDslPredicateExecutor<Person> {
}
