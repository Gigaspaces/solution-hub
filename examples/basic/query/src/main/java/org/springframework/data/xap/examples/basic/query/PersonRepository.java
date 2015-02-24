package org.springframework.data.xap.examples.basic.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, Integer> {
    List<Person> findByPositionAndActiveTrue(String position);

    List<Person> findByAgeGreaterThan(Integer age, Sort sort);

    List<Person> findByActiveTrue(Pageable pageable);
}
