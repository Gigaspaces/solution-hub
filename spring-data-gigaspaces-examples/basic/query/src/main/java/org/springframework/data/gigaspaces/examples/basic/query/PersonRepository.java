package org.springframework.data.gigaspaces.examples.basic.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.examples.model.Person;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends GigaspacesRepository<Person, Integer> {
    List<Person> findByPositionAndActiveTrue(String position);

    List<Person> findByAgeGreaterThan(Integer age, Sort sort);

    List<Person> findByActiveTrue(Pageable pageable);

    List<Person> findByPositionOrdered(String name);
}
