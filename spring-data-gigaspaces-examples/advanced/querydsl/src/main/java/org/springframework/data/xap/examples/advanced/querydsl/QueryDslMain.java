package org.springframework.data.xap.examples.advanced.querydsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class QueryDslMain {
    private static Logger log = LoggerFactory.getLogger(QueryDslMain.class);

    public static void main(String[] args) {
        log.info("QUERY DSL EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        QueryDslExample queryDslExample = context.getBean(QueryDslExample.class);
        queryDslExample.run();
        System.exit(0);
    }
}
