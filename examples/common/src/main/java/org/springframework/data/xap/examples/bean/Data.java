package org.springframework.data.xap.examples.bean;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * @author Leonid_Poliakov
 */
@SpaceClass
public class Data {
    private String id;
    private String message;

    public Data() {
    }

    public String getMessage() {
        return message;
    }

    @SpaceId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Data{" + "message='" + message + '\'' + ", id='" + id + '\'' + '}';
    }

}