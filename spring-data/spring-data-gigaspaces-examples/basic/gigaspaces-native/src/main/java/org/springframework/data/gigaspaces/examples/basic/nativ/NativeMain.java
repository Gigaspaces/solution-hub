package org.springframework.data.gigaspaces.examples.basic.nativ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Anna_Babich.
 */
public class NativeMain {
    private static Logger log = LoggerFactory.getLogger(NativeMain.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("USE Gigaspaces NATIVE API EXAMPLE");
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        NativeExample nativeExample = context.getBean(NativeExample.class);
        nativeExample.run();
        System.exit(0);
    }
}
