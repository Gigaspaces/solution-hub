package com.gigaspaces.sbp;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 4/27/14
 * Time: 5:16 AM
 */
public class Spring implements WatchPart, Serializable{

    private Float weight;

    @Override
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Spring{");
        sb.append("weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}
