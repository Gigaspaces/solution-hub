package org.springframework.data.gigaspaces.repository.query;

/**
 * This is an extension to Spring Data that allows to use Gigaspaces' Projections API.
 * <p/>
 * Projection specifies that a result for an operation using query should contain data only for the specified projection
 * properties, all other properties are not populated.
 * <p/>
 * Projections mechanism allows to reduce network overhead, garbage memory generation and CPU overhead due to serialization
 *
 * @author Oleksiy_Dyagilev
 */
public class Projection {

    private final String[] properties;

    public Projection(String... properties) {
        this.properties = properties;
    }

    /**
     * convenient for static imports
     */
    public static Projection projections(String... properties) {
        return new Projection(properties);
    }

    public String[] getProperties() {
        return properties;
    }
}
