package org.springframework.data.xap.examples.bean;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.util.Date;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class Person {
    private Integer id;
    private String name;
    private Boolean active;
    private String skill;
    private Integer age;

    public Person() {
    }

    public Person(Integer id, String name, Boolean active, String skill, Integer age) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.skill = skill;
        this.age = age;
    }

    @SpaceId(autoGenerate = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (active != null ? !active.equals(person.active) : person.active != null) return false;
        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (skill != null ? !skill.equals(person.skill) : person.skill != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", skill='" + skill + '\'' +
                ", age=" + age +
                '}';
    }
}
