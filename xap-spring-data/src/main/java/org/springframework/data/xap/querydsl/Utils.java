package org.springframework.data.xap.querydsl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mysema.query.types.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksiy_Dyagilev
 */
public class Utils {

    /**
     * Convert QueryDSL Path to XAP path (used in projections and change API)
     *
     * @param path QueryDSL Path
     * @return XAP string representation
     */
    public static String convertPathToXapFieldString(Path<?> path) {
        return convertPathToXapFieldString(path, new ArrayList<String>());
    }

    // recursively traverse and accumulate path into a list of fields
    private static String convertPathToXapFieldString(Path<?> path, List<String> fieldsPath) {
        if (path.getMetadata().isRoot()) {
            return Joiner.on(".").join(Lists.reverse(fieldsPath));
        } else {
            fieldsPath.add(path.getMetadata().getName());
            return convertPathToXapFieldString(path.getMetadata().getParent(), fieldsPath);
        }
    }
}
