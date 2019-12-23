package org.springframework.data.gigaspaces.examples.simple;

import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends GigaspacesRepository<Person, Integer> {
}
