package org.springframework.data.gigaspaces.examples.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import org.springframework.data.gigaspaces.examples.util.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class Meeting {

    private Integer id;
    private MeetingRoom meetingRoom;
    private List<Person> personList;
    private Date startTime;

    public Meeting() {
    }

    public Meeting(Integer id, MeetingRoom meetingRoom, List<Person> personList, Date startTime) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.personList = personList;
        this.startTime = startTime;
    }

    @SpaceId(autoGenerate = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", meetingRoom=" + meetingRoom +
                ", personList=" + personList +
                ", startTime=" + DateUtils.getFormatDate(startTime) +
                '}';
    }
}
