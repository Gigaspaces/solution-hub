package org.springframework.data.gigaspaces.querydsl.predicate;

import org.springframework.data.gigaspaces.model.Team;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslPredicateExecutor;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * @author Leonid_Poliakov
 */
public interface PredicateTeamRepository extends GigaspacesRepository<Team, String>, GigaspacesQueryDslPredicateExecutor<Team> {
}