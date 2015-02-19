package org.springframework.data.xap.utils;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.io.IOException;
import java.util.Properties;

import static org.springframework.data.xap.utils.TestUtils.*;

public class SpaceStarter {

    public static void main(String[] args) throws InterruptedException {
        GigaSpace gigaspace = new GigaSpaceConfigurer(
                new UrlSpaceConfigurer("/./space?groups=" + getGroupName())).gigaSpace();
        // if started from maven wit some time for tests execution and exit
        if (mavenTest(args)){
            Thread.sleep(60000);
            System.exit(0);
        }
    }

    /**
     * SpaceStarter can be run as main class for debug purposes or as a part of maven build
     * @param args
     * @return
     */
    private static boolean mavenTest(String[] args) {
        return args.length  > 0 && "test".equals(args[0]);
    }

}
