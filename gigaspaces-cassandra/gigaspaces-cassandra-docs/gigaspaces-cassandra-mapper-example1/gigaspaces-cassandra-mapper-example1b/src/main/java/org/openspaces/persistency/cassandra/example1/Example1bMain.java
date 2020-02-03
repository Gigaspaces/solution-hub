package org.openspaces.persistency.cassandra.example1;

import com.gigaspaces.logger.GSLogConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Example1bMain {
    private static Logger logger = LoggerFactory.getLogger(Example1bMain.class);

    public static void main(String[] args){
        logger.info("Starting Example1bMain");
        GSLogConfigLoader.getLoader();
        ApplicationContext context = new ClassPathXmlApplicationContext("/gigaspaces-with-cassandra-ds-beans-test.xml");
    }

}
