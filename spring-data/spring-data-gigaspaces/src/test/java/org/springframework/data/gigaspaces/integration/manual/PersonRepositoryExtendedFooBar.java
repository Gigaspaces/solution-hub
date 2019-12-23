package org.springframework.data.gigaspaces.integration.manual;

/**
 * Prefix "FooBar" is used instead of "Impl" to show that custom prefixes can be used for classes that implement custom methods in repository.
 *
 * @author Leonid_Poliakov
 */
public class PersonRepositoryExtendedFooBar implements PersonRepositoryCustom {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String customMethod() {
        return message;
    }
}