package org.springframework.data.xap.examples.basic.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class TransactionMain {
    private static Logger log = LoggerFactory.getLogger(TransactionMain.class);

    public static void main(String[] args) {
        log.info("USE TRANSACTIONS");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        TransactionExample transactionExample = context.getBean(TransactionExample.class);
        transactionExample.run();
        System.exit(0);
    }
}
