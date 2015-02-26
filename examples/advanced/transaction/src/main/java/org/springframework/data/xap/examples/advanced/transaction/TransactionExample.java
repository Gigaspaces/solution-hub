package org.springframework.data.xap.examples.advanced.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.DataSet;
import org.springframework.stereotype.Component;

/**
 * @author Anna_Babich.
 */
@Component
public class TransactionExample {
    private static Logger log = LoggerFactory.getLogger(TransactionExample.class);

    @Autowired
    private ServiceWithTransaction service;

    @Autowired
    PersonRepository personRepository;

    public void run() {
        log.info("Run service method with rollback.. ");
        service.serviceMethod(true, DataSet.nick);
        log.info("Try to get person, that has been saved in transaction: " + personRepository.findOne(DataSet.nick.getId()));
        log.info("Run service method without rollback..");
        service.serviceMethod(false, DataSet.mary);
        log.info("Try to get person, that has been saved in transaction: " + personRepository.findOne(DataSet.mary.getId()));
    }
}
