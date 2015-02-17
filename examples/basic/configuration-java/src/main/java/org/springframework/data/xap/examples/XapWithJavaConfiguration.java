package org.springframework.data.xap.examples;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.xap.examples.bean.Data;
import org.springframework.data.xap.examples.repository.DataRepository;

import java.util.UUID;

import static org.springframework.data.xap.examples.util.Output.dateTime;

/**
 * Runs an example of Java-based configuration.
 *
 * @author Leonid_Poliakov
 */
public class XapWithJavaConfiguration {
    private DataRepository repository;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        new XapWithJavaConfiguration(context.getBean(DataRepository.class)).launch();
    }

    public XapWithJavaConfiguration(DataRepository repository) {
        this.repository = repository;
    }

    private void launch() {
        Data data = new Data();
        data.setId(UUID.randomUUID().toString());
        data.setMessage("Java based configuration " + dateTime());

        System.out.println("writing data: " + data + "\n");
        Data savedData = repository.save(data);
        System.out.println("saved data: " + savedData + "\n");
        System.out.println("current space: ");
        for (Data foundData : repository.findAll()) {
            System.out.println("\t" + foundData);
        }
        System.out.println();
    }

}