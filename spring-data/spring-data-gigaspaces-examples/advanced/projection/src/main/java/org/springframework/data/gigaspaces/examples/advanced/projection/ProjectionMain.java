package org.springframework.data.gigaspaces.examples.advanced.projection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class ProjectionMain {
    private static Logger log = LoggerFactory.getLogger(ProjectionMain.class);

    public static void main(String[] args) {
        log.info("PROJECTION EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ProjectionExample projectionExample = context.getBean(ProjectionExample.class);
        projectionExample.run();
        System.exit(0);
    }
}
