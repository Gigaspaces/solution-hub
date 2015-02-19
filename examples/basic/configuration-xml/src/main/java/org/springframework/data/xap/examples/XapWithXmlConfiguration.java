package org.springframework.data.xap.examples;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.xap.examples.repository.PersonRepository;

import java.util.UUID;

import static org.springframework.data.xap.examples.util.Output.dateTime;

/**
 * Runs an example of XML configuration. Please, see <code>context.xml</code> file from this example for details.
 *
 * @author Leonid_Poliakov
 */
public class XapWithXmlConfiguration {
    private PersonRepository repository;

    public static void main(String args[]) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("context.xml");
        context.refresh();
        new XapWithXmlConfiguration(context.getBean(PersonRepository.class)).launch();
    }

    public XapWithXmlConfiguration(PersonRepository repository) {
        this.repository = repository;
    }

    private void launch() {
//        Data data = new Data();
//        data.setId(UUID.randomUUID().toString());
//        data.setMessage("XML configuration " + dateTime());
//
//        System.out.println("writing data: " + data + "\n");
//        Data savedData = repository.save(data);
//        System.out.println("saved data: " + savedData + "\n");
//        System.out.println("current space: ");
//        for (Data foundData : repository.findAll()) {
//            System.out.println("\t" + foundData);
//        }
//        System.out.println();
    }
}