package org.springframework.data.xap.examples.basic.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class QueryMethodsMain {
    private static Logger log = LoggerFactory.getLogger(QueryMethodsMain.class);
    public static void main(String[] args) {

        log.info("QUERY CREATION EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QueryMethodsExample queryMethodsExample = context.getBean(QueryMethodsExample.class);
        queryMethodsExample.run();
        System.exit(0);

    }
}
