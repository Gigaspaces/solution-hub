package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Leonid_Poliakov.
 */
public class DocumentApiMain {
    private static Logger log = LoggerFactory.getLogger(DocumentApiMain.class);

    public static void main(String[] args) {
        log.info("DOCUMENT STORAGE SUPPORT EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.getBean(DocumentApiExample.class).run();
        System.exit(0);
    }
}