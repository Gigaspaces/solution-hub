package org.springframework.data.xap.examples.bean;

import java.util.Date;

/**
 * @author Anna_Babich.
 */
public class Person {
    private int id;
    private String name;
    private Address address;
    private boolean active;
    private Date birthday;

    public Person() {
    }

    public Person(int id, String name, Address address, boolean active, Date birthday) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.active = active;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (active != person.active) return false;
        if (id != person.id) return false;
        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (birthday != null ? !birthday.equals(person.birthday) : person.birthday != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", active=" + active +
                ", birthday=" + birthday +
                '}';
    }
}
