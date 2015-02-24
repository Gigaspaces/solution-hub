package org.springframework.data.xap.examples.basic.custom;

/**
 * @author Anna_Babich.
 */
public class PersonRepositoryExtendedImpl implements CustomInterface {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String customMethod() {
        return message;
    }
}
