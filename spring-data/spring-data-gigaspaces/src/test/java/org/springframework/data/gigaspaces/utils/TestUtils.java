package org.springframework.data.gigaspaces.utils;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

/**
 * Utils to simplify testing.
 *
 * @author Oleksiy_Dyagilev
 */
public class TestUtils {

    public static GigaSpace initSpace(String spaceName) {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./" + spaceName);
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }
}
