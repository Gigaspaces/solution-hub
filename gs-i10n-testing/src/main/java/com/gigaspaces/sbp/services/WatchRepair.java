package com.gigaspaces.sbp.services;

import com.gigaspaces.sbp.Gear;
import com.gigaspaces.sbp.Watch;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 4/28/14
 * Time: 1:20 PM
 */
public interface WatchRepair {

    /**
     * Executes a set on the watch to be repaired, in a mechanism that is collocated with
     * the data.
     * @param toRepair on which to perform the gear update
     * @param newGears gears to use
     * @return updated {@link Watch}
     */
    Watch switchGears(Watch toRepair, List<Gear> newGears);

}
