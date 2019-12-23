package hello;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={Application.class} )
public class QuickStartTest {

    static {
        System.setProperty("spring.main.allow-bean-definition-overriding","true");
    }
    @Autowired
    Application application;

    @Test
    public void quickStartTest(){
        application.quickstartRun();;
    }
}
