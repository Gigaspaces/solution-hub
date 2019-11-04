package org.springframework.data.gigaspaces.examples.model;

import java.io.Serializable;

/**
 * @author Anna_Babich.
 */
public class Address implements Serializable {
    private String city;
    private String localAddress;

    public Address() {
    }

    public Address(String city, String localAddress) {
        this.city = city;
        this.localAddress = localAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", localAddress='" + localAddress + '\'' +
                '}';
    }
}
