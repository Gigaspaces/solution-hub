package org.springframework.data.xap.querydsl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mysema.query.types.Path;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Utility class to perform conversions between Querydsl and XAP paths for pojo fields.</p>
 *
 * @author Oleksiy_Dyagilev
 */
public class QueryDslUtils {

    /**
     * Convert QueryDSL Path to XAP path (used in projections and change API)
     *
     * @param path Querydsl Path
     * @return XAP string representation
     */
    public static String convertPathToXapFieldString(Path<?> path) {
        return convertPathToXapFieldString(path, new LinkedList<String>());
    }

    private static String convertPathToXapFieldString(Path<?> path, List<String> fieldsPath) {
        // recursively traverse and accumulate path into a list of fields
        if (path.getMetadata().isRoot()) {
            return Joiner.on(".").join(Lists.reverse(fieldsPath));
        } else {
            fieldsPath.add(path.getMetadata().getName());
            return convertPathToXapFieldString(path.getMetadata().getParent(), fieldsPath);
        }
    }

}