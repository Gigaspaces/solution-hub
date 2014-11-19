package mytest;

import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Oleksiy_Dyagilev
 */
public interface PersonRepository extends XapRepository<Person, String> {
}
