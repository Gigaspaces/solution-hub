package org.springframework.data.xap.examples.basic.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class CustomMain {
    private static Logger log = LoggerFactory.getLogger(CustomMain.class);
    public static void main(String[] args) {
        log.info("CUSTOM METHOD EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        log.info("Set up message");
        personRepository.setMessage("Hello World");
        log.info("Call custom method");
        log.info(personRepository.customMethod());
        System.exit(0);
    }
}
