package org.springframework.data.xap.examples.basic.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class QueryCreationMain {
    private static Logger log = LoggerFactory.getLogger(QueryCreationMain.class);
    public static void main(String[] args) {

        log.info("QUERY CREATION EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QueryCreationExample queryCreationExample = context.getBean(QueryCreationExample.class);
        queryCreationExample.run();
        System.exit(0);

    }
}
