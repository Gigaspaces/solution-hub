package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class ChangeApiMain {

    private static Logger log = LoggerFactory.getLogger(ChangeApiMain.class);

    public static void main(String[] args) {
        log.info("CHANGE API EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ChangeApiExample changeApiExample = context.getBean(ChangeApiExample.class);
        changeApiExample.run();
        System.exit(0);
    }
}
