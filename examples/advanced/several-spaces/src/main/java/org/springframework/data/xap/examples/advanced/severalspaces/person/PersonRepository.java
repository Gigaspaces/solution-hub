package org.springframework.data.xap.examples.advanced.severalspaces.person;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.data.xap.repository.XapRepository;

import java.util.List;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, Integer> {
}
