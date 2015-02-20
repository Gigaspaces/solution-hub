package org.springframework.data.xap.utils;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * Utils to simplify testing.
 *
 * @author Oleksiy_Dyagilev
 */
public class TestUtils {

    public static GigaSpace initSpace() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./space").lookupGroups(getGroupName());
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }

    public static String getGroupName() {
        Properties properties = new Properties();
        try {
            properties.load(TestUtils.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to find config.properties");
        }
        return properties.getProperty("space.groups");
    }

}
