package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Leonid_Poliakov.
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class DocumentApiTests {
    private static Logger log = LoggerFactory.getLogger(DocumentApiMain.class);

    @Autowired
    DocumentApiExample documentApiExample;
    @Test
    public void validateThatTestRunWithoutIssue() {
        documentApiExample.run();
    }
}