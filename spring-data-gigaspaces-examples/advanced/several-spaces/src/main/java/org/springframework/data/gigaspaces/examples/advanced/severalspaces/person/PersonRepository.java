package org.springframework.data.gigaspaces.examples.advanced.severalspaces.person;

import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends GigaspacesRepository<Person, Integer> {
}
