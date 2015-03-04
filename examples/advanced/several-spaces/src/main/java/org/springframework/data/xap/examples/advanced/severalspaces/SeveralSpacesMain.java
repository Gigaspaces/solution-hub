package org.springframework.data.xap.examples.advanced.severalspaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class SeveralSpacesMain {
    private static Logger log = LoggerFactory.getLogger(SeveralSpacesMain.class);

    public static void main(String[] args) {
        log.info("SEVERAL SPACES USING EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        SeveralSpacesExample severalSpacesExample = context.getBean(SeveralSpacesExample.class);
        severalSpacesExample.run();
        System.exit(0);
    }
}
