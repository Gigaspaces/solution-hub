package org.springframework.data.xap.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Init context... ");
        ApplicationContext context = new ClassPathXmlApplicationContext("org.springframework.data.xap.examples/context.xml");
        Sample sample = (Sample) context.getBean("sample");
        sample.run();
        System.exit(0);
    }
}
