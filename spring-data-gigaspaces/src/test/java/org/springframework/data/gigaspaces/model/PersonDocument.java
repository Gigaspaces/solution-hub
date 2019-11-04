package org.springframework.data.gigaspaces.model;

import com.gigaspaces.document.SpaceDocument;

import java.util.Date;

/**
 * @author Leonid_Poliakov
 */
public class PersonDocument extends SpaceDocument {
    public static final String TYPE_NAME = "Person";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_AGE = "age";
    public static final String PROPERTY_SPOUSE = "spouse";
    public static final String PROPERTY_BIRTHDAY = "birthday";
    public static final String PROPERTY_ACTIVE = "active";

    public PersonDocument() {
        super(TYPE_NAME);
    }

    public PersonDocument(String id, String name, Integer age) {
        this();
        setId(id);
        setName(name);
        setAge(age);
    }

    public String getId() {
        return super.getProperty(PROPERTY_ID);
    }

    public PersonDocument setId(String id) {
        super.setProperty(PROPERTY_ID, id);
        return this;
    }

    public String getName() {
        return super.getProperty(PROPERTY_NAME);
    }

    public PersonDocument setName(String name) {
        super.setProperty(PROPERTY_NAME, name);
        return this;
    }

    public Integer getAge() {
        return super.getProperty(PROPERTY_AGE);
    }

    public PersonDocument setAge(Integer age) {
        super.setProperty(PROPERTY_AGE, age);
        return this;
    }

    public Person getSpouse() {
        return super.getProperty(PROPERTY_SPOUSE);
    }

    public PersonDocument setSpouse(Integer spouse) {
        super.setProperty(PROPERTY_SPOUSE, spouse);
        return this;
    }

    public Date getBirthday() {
        return super.getProperty(PROPERTY_BIRTHDAY);
    }

    public PersonDocument setBirthday(Date birthday) {
        super.setProperty(PROPERTY_BIRTHDAY, birthday);
        return this;
    }

    public Boolean isActive() {
        return super.getProperty(PROPERTY_ACTIVE);
    }

    public PersonDocument setActive(Boolean active) {
        super.setProperty(PROPERTY_ACTIVE, active);
        return this;
    }

}