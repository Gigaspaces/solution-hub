package org.springframework.data.xap.querydsl.predicate;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.repository.PersonRepository;

/**
 * @author Leonid_Poliakov
 */
public interface PredicatePersonRepository extends PersonRepository, QueryDslPredicateExecutor<Person> {
}