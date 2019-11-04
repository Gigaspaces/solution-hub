package org.springframework.data.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Oleksiy_Dyagilev
 */
@Entity // required for JPA
@SpaceClass
public class Person implements Serializable {
    @Id
    private String id;

    private String name;

    private Integer age;

    private Person spouse;

    private Date birthday;

    private Boolean active;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String id, String name, Integer age, Date birthday, Boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.active = active;
    }

    public Person(String id, String name, Integer age, Person spouse, Date birthday, Boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.spouse = spouse;
        this.birthday = birthday;
        this.active = active;
    }

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SpaceId(autoGenerate = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (active != null ? !active.equals(person.active) : person.active != null) return false;
        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (spouse != null ? !spouse.equals(person.spouse) : person.spouse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (spouse != null ? spouse.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", spouse=" + spouse +
                ", birthday=" + birthday +
                ", active=" + active +
                '}';
    }
}
