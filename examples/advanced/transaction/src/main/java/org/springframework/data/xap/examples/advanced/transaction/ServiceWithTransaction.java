package org.springframework.data.xap.examples.advanced.transaction;

import net.jini.core.transaction.Transaction;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.examples.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author Anna_Babich.
 */
@Component
public class ServiceWithTransaction {
    private static Logger log = LoggerFactory.getLogger(TransactionExample.class);

    @Autowired
    private PersonRepository personRepository;


    @Transactional
    public void serviceMethod(boolean doRollback, Person person) {
        GigaSpace space = personRepository.space();
        Transaction transaction = space.getCurrentTransaction();
        log.info("Current transaction: " + transaction);
        personRepository.save(person);
        log.info("Saved person: " + personRepository.findOne(person.getId()));
        if (doRollback) {
            log.info("Set rollback status");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
}
