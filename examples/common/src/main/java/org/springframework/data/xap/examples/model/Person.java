package org.springframework.data.xap.examples.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import java.io.Serializable;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class Person implements Serializable {
    private Integer id;
    private String name;
    private Boolean active;
    private String position;
    private Integer age;

    public Person() {
    }

    public Person(Integer id, String name, Boolean active, String position, Integer age) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (position != null ? !position.equals(person.position) : person.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", position='" + position + '\'' +
                ", age=" + age +
                '}';
    }
}
