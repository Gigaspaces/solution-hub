package org.springframework.data.gigaspaces.integration.manual;

import org.springframework.data.gigaspaces.repository.PersonRepository;

/**
 * @author Leonid_Poliakov
 */
public interface PersonRepositoryExtended extends PersonRepository, PersonRepositoryCustom {
}