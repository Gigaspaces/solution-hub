package org.springframework.data.xap.examples;

import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.xap.examples.model.Address;
import org.springframework.data.xap.examples.model.MeetingRoom;
import org.springframework.data.xap.examples.model.Person;

import java.util.Arrays;

/**
 * @author Anna_Babich.
 */
public class DataSet {
    private static Logger log = LoggerFactory.getLogger(DataSet.class);

    public static Person nick = new Person(1, "Nick", true, "accounting", 22);
    public static Person mary = new Person(2, "Mary", false, "teacher", 29);
    public static Person john = new Person(3, "John", true, "accounting", 31);
    public static Person paul = new Person(4, "Paul", false, "accounting", 43);
    public static Person jim = new Person(5, "Jim", true, "security", 24);

    public static MeetingRoom green = new MeetingRoom(new Address("New York", "Main Street, 1"), "green");
    public static MeetingRoom orange = new MeetingRoom(new Address("New York", "Main Street, 5"), "orange");
    public static MeetingRoom blue = new MeetingRoom(new Address("Kyiv", "Main Street, 12"), "blue");

    public static void setup(GigaSpace space){
        space.writeMultiple(new Person[]{nick, mary, john, paul, jim});
        space.writeMultiple(new MeetingRoom[]{green, orange, blue});
        log.info("Created persons: " + Arrays.asList(space.readMultiple(new Person())));
        log.info("Created rooms: " + Arrays.asList(space.readMultiple(new MeetingRoom())));
    }

    public static void cleanup(GigaSpace space){
        space.clear(null);
        log.info("All data has been deleted from space");
    }
}
