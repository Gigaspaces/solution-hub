package org.springframework.data.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Id;

/**
 * @author Oleksiy_Dyagilev
 */
@SpaceClass
public class Event {

    @Id
    private String id;

    private String name;

    public Event(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Event() {
    }

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
