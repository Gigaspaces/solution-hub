package org.springframework.data.gigaspaces.examples.advanced.projection;

import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslPredicateExecutor;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * @author Anna_Babich.
 */
public interface PredicatePersonRepository extends GigaspacesRepository<Person, Integer>, GigaspacesQueryDslPredicateExecutor<Person> {
}
