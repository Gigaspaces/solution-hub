package org.springframework.data.gigaspaces.examples.advanced.changeapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class ChangeApiTest {

    @Autowired
    ChangeApiExample changeApiExample;

    @Test
    public void changeApiTest(){
        changeApiExample.run();
    }
}
