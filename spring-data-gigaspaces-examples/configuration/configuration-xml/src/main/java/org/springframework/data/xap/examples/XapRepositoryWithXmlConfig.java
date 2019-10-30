package org.springframework.data.xap.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.xap.examples.simple.PersonRepository;
import org.springframework.data.xap.examples.simple.WriteReadExample;

/**
 * Runs an example of XML configuration. Please, see <code>context.xml</code> file from this example for details.
 *
 * @author Leonid_Poliakov
 */
public class XapRepositoryWithXmlConfig {

    private static Logger log = LoggerFactory.getLogger(XapRepositoryWithXmlConfig.class);

    public static void main(String args[]) {
        System.out.println();
        log.info("XML configuration repository");
        ApplicationContext context = new ClassPathXmlApplicationContext("org.springframework.data.xap.examples/context.xml");
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        WriteReadExample.launch(personRepository);
        System.exit(0);
    }
}