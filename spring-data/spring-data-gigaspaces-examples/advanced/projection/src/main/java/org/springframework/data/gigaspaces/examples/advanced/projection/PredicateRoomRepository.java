package org.springframework.data.gigaspaces.examples.advanced.projection;

import org.springframework.data.gigaspaces.examples.model.MeetingRoom;
import org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslPredicateExecutor;
import org.springframework.data.gigaspaces.repository.GigaspacesRepository;

/**
 * @author Anna_Babich.
 */
public interface PredicateRoomRepository  extends GigaspacesRepository<MeetingRoom, String>, GigaspacesQueryDslPredicateExecutor<MeetingRoom> {
}
