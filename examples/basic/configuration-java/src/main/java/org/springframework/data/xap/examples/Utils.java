package org.springframework.data.xap.examples;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Anna_Babich.
 */
public class Utils {


    public static String getGroupName(){
        Properties properties = new Properties();
        try {
            properties.load(Utils.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to find config.properties");
        }
        String groupName =  properties.getProperty("space.groups");
        groupName = groupName.replace("?groups=", "");
        return properties.getProperty("space.groups");
    }
}
