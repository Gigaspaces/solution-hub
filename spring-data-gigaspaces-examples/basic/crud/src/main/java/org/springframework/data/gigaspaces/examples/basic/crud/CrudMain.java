package org.springframework.data.gigaspaces.examples.basic.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class CrudMain {
    private static Logger log = LoggerFactory.getLogger(CrudMain.class);

    public static void main(String[] args) {
        log.info("CRUD REPOSITORY EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CrudExample crudExample = context.getBean(CrudExample.class);
        crudExample.run();
        System.exit(0);
    }
}
