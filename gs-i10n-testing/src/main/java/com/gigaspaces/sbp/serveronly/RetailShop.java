package com.gigaspaces.sbp.serveronly;

import com.gigaspaces.sbp.Gear;
import com.gigaspaces.sbp.Watch;
import com.gigaspaces.sbp.services.WatchRepair;
import org.openspaces.core.GigaSpace;
import org.openspaces.remoting.RemotingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 4/28/14
 * Time: 1:22 PM
 */
@RemotingService
public class RetailShop implements WatchRepair {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private GigaSpace gigaSpace;

    @Override
    public Watch switchGears(Watch toRepair, List<Gear> newGears) {

        logger.trace("Watch: {} .", toRepair);
        for (Gear gear : newGears) logger.trace("Gears: {}", gear);
        logger.trace("GigaSpace {} .", gigaSpace);

        Watch original = gigaSpace.readById(Watch.class, toRepair.getSpaceId());
        if (original != null) {
            original.setGears(newGears);
            gigaSpace.write(original);
        } else {
            logger.error("Retrieved a null Watch, which is not expected for this demo. Watch toRepair was: {} .", toRepair);
        }

        return original;

    }

}