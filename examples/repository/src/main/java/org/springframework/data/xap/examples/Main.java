package org.springframework.data.xap.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.xap.examples.repository.MeetingRepository;
import org.springframework.data.xap.examples.repository.MeetingRoomRepository;
import org.springframework.data.xap.examples.repository.PersonRepository;

/**
 * @author Anna_Babich.
 */
public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Init context... ");
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("context.xml");
        context.refresh();
        Sample sample = (Sample)context.getBean("sample");
        sample.run();
    }
}
