package org.springframework.data.xap.examples.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceStorageType;
import com.gigaspaces.metadata.StorageType;
import com.gigaspaces.metadata.index.SpaceIndexType;

/**
 * @author Anna_Babich.
 */
@SpaceClass
public class Person {
    private Integer id;
    private String name;
    private Boolean active;
    private String position;
    private Integer age;
    private Byte[] photo;

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
    
    @SpaceIndex(type = SpaceIndexType.EXTENDED)
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

    @SpaceStorageType(storageType = StorageType.BINARY)
    public Byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(Byte[] photo) {
        this.photo = photo;
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
