package org.springframework.data.gigaspaces.querydsl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Path;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Utility class to perform conversions between Querydsl and Gigaspaces paths for pojo fields.</p>
 *
 * @author Oleksiy_Dyagilev
 */
public interface GigaspacesQueryDslUtils {


    static boolean isPaged(Pageable pageable){
        if (pageable == null) {
            return false;
        }
        return pageable.isPaged();
    }

    static boolean isSorted(Sort sort){
        if (sort == null) {
            return false;
        }
        return sort.isSorted();
    }

    /**
     * Convert QueryDSL Path to Gigaspaces path (used in projections and change API)
     *
     * @param path Querydsl Path
     * @return Gigaspaces string representation
     */
    static String convertPathToGigaspacesFieldString(Path<?> path) {
        return convertPathToGigaspacesFieldString(path, new LinkedList<String>());
    }

    static String convertPathToGigaspacesFieldString(Path<?> path, List<String> fieldsPath) {
        // recursively traverse and accumulate path into a list of fields
        if (path.getMetadata().isRoot()) {
            return Joiner.on(".").join(Lists.reverse(fieldsPath));
        } else {
            fieldsPath.add(path.getMetadata().getName());
            return convertPathToGigaspacesFieldString(path.getMetadata().getParent(), fieldsPath);
        }
    }

}