package org.springframework.data.xap.utils;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.data.xap.utils.TestUtils.*;

public class SpaceStarter {

    public static void main(String[] args) {
        GigaSpace gigaspace = new GigaSpaceConfigurer(
                new UrlSpaceConfigurer("/./space?groups=" + getGroupName())).gigaSpace();
    }

}
