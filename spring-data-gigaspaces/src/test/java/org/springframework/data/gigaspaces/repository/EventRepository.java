package org.springframework.data.gigaspaces.repository;

import org.springframework.data.gigaspaces.model.Event;

/**
 * @author Oleksiy_Dyagilev
 */
public interface EventRepository extends GigaspacesRepository<Event, String> {
}
