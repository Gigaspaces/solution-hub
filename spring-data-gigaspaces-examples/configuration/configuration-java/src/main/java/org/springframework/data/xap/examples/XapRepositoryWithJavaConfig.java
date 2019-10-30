package org.springframework.data.xap.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.xap.examples.simple.PersonRepository;
import org.springframework.data.xap.examples.simple.WriteReadExample;

/**
 * Runs an example of Java-based configuration.
 *
 * @author Leonid_Poliakov
 */
public class XapRepositoryWithJavaConfig {

    private static Logger log = LoggerFactory.getLogger(XapRepositoryWithJavaConfig.class);

    public static void main(String args[]) {
        log.info("Java configuration repository");
        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        WriteReadExample.launch(personRepository);
        System.exit(0);
    }
}