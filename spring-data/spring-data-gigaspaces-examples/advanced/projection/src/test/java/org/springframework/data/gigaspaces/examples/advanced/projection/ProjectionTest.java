package org.springframework.data.gigaspaces.examples.advanced.projection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath*:spring-context.xml")
public class ProjectionTest {

    @Autowired
    ProjectionExample projectionExample;

    @Test
    public void projectionExampleTest(){
        projectionExample.run();
    }
}
