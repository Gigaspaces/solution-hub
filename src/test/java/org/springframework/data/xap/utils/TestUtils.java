package org.springframework.data.xap.utils;

import com.gigaspaces.internal.client.spaceproxy.ISpaceProxy;
import com.j_spaces.core.client.FinderException;
import com.j_spaces.core.client.SpaceFinder;
import org.springframework.data.xap.spaceclient.SpaceClient;

import java.io.IOException;
import java.util.Properties;

/**
 * Utils to simplify testing.
 *
 * @author Oleksiy_Dyagilev
 */
public class TestUtils {

    public static SpaceClient initSpaceClient() {
        Properties properties = new Properties();
        try {
            properties.load(TestUtils.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to find config.properties");
        }
        String group = properties.getProperty("space.groups");

        ISpaceProxy space = null;
        try {
            space = (ISpaceProxy) SpaceFinder.find("jini://*/*/space?groups=" + group);
        } catch (FinderException e) {
            throw new RuntimeException("Unable to find space instance for testing", e);
        }
        SpaceClient spaceClient = new SpaceClient();
        spaceClient.setSpace(space);
        return spaceClient;
    }

}
