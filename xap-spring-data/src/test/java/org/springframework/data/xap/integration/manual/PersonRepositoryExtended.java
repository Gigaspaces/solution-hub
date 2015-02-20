package org.springframework.data.xap.integration.manual;

import org.springframework.data.xap.repository.PersonRepository;

/**
 * @author Leonid_Poliakov
 */
public interface PersonRepositoryExtended extends PersonRepository, PersonRepositoryCustom {
}