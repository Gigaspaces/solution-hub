package org.springframework.data.xap.utils;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

public class SpaceStarter {

    public static void main(String[] args) {
        GigaSpace gigaspace = new GigaSpaceConfigurer(
                new UrlSpaceConfigurer("/./testSpace")).gigaSpace();
    }

}
