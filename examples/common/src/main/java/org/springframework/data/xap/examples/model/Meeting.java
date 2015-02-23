package org.springframework.data.xap.examples.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import org.springframework.data.xap.examples.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class Meeting implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (id != null ? !id.equals(meeting.id) : meeting.id != null) return false;
        if (meetingRoom != null ? !meetingRoom.equals(meeting.meetingRoom) : meeting.meetingRoom != null) return false;
        if (personList != null ? !personList.equals(meeting.personList) : meeting.personList != null) return false;
        if (startTime != null ? !startTime.equals(meeting.startTime) : meeting.startTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (meetingRoom != null ? meetingRoom.hashCode() : 0);
        result = 31 * result + (personList != null ? personList.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
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
