package org.springframework.data.xap.examples.repository;

import org.springframework.data.xap.examples.bean.Data;
import org.springframework.data.xap.repository.XapRepository;

/**
 * @author Leonid_Poliakov
 */
public interface DataRepository extends XapRepository<Data, String> {
}