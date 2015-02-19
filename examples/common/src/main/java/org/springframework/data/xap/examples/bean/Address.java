package org.springframework.data.xap.examples.bean;

/**
 * @author Anna_Babich.
 */
public class Address {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (localAddress != null ? !localAddress.equals(address.localAddress) : address.localAddress != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (localAddress != null ? localAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", localAddress='" + localAddress + '\'' +
                '}';
    }
}
