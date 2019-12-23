package org.springframework.data.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Leonid_Poliakov
 */
@SpaceClass
public class Team {
    @Id
    private String id;
    private String name;
    private Person leader;
    private Integer membersCount;
    private Person sponsor;
    private TeamStatus status;
    private Date creationDate;

    public Team() {
    }

    public Team(String id, String name, Person leader, Integer membersCount, Person sponsor, TeamStatus status, Date creationDate) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.membersCount = membersCount;
        this.sponsor = sponsor;
        this.status = status;
        this.creationDate = creationDate;
    }

    @SpaceId(autoGenerate = false)
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

    public Person getLeader() {
        return leader;
    }

    public void setLeader(Person leader) {
        this.leader = leader;
    }

    public Integer getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(Integer membersCount) {
        this.membersCount = membersCount;
    }

    public Person getSponsor() {
        return sponsor;
    }

    public void setSponsor(Person sponsor) {
        this.sponsor = sponsor;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        if (creationDate != null ? !creationDate.equals(team.creationDate) : team.creationDate != null) return false;
        if (id != null ? !id.equals(team.id) : team.id != null) return false;
        if (leader != null ? !leader.equals(team.leader) : team.leader != null) return false;
        if (membersCount != null ? !membersCount.equals(team.membersCount) : team.membersCount != null) return false;
        if (name != null ? !name.equals(team.name) : team.name != null) return false;
        if (sponsor != null ? !sponsor.equals(team.sponsor) : team.sponsor != null) return false;
        if (status != team.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (leader != null ? leader.hashCode() : 0);
        result = 31 * result + (membersCount != null ? membersCount.hashCode() : 0);
        result = 31 * result + (sponsor != null ? sponsor.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", leader=" + leader +
                ", membersCount=" + membersCount +
                ", sponsor=" + sponsor +
                ", status=" + status +
                ", creationDate=" + creationDate +
                '}';
    }
}