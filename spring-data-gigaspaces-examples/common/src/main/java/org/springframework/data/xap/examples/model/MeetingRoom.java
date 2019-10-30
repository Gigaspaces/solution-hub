package org.springframework.data.xap.examples.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class MeetingRoom {
    private Address address;
    private String name;

    public MeetingRoom() {
    }

    public MeetingRoom(Address address, String name) {
        this.address = address;
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @SpaceId(autoGenerate = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MeetingRoom{" +
                "address=" + address +
                ", name='" + name + '\'' +
                '}';
    }
}
