package org.springframework.data.xap.examples.repository;

import org.springframework.data.xap.examples.bean.Person;
import org.springframework.data.xap.repository.XapRepository;
import org.springframework.data.xap.repository.query.XapRepositoryQuery;

/**
 * @author Anna_Babich.
 */
public interface PersonRepository extends XapRepository<Person, String> {
}
