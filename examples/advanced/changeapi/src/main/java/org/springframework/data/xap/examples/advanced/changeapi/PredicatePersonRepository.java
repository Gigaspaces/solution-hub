package org.springframework.data.xap.examples.advanced.changeapi;

import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.querydsl.XapQueryDslPredicateExecutor;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Anna_Babich.
 */
public interface PredicatePersonRepository extends XapRepository<Person, Integer>, XapQueryDslPredicateExecutor<Person> {
}
