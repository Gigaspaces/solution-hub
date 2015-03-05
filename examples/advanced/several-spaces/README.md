Spring Data XAP - Using several spaces
======================================

Sometimes it is required to have different groups of repositories to store and exchange data in different spaces. Configuration for such case will have several space declarations and several repository groups. For each group the space to use will be assigned using `gigaspace` attribute referring to `GigaSpace` bean id. Usually, groups of repositories will be placed in subpackages - it makes system structure clearer and eases configuration as well.

This example shows how one can configure and use two repositories working with different spaces. To run it, refer to `SeveralSpacesMain` class. The output of the example should be like this:

```
...
SEVERAL SPACES USAGE EXAMPLE
...
Space name of the personRepository space1
Space name of the roomRepository space2
Save persons and rooms to repositories..
Get persons from repository:
Person{id=3, name='John', active=true, position='accountant', age=31}
Person{id=5, name='Jim', active=true, position='security', age=24}
Get rooms from repository:
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 1'}, name='green'}
MeetingRoom{address=Address{city='New York', localAddress='Main Street, 5'}, name='orange'}
Try to get rooms from personRepository space
[]
Try to get persons from meetingRoomRepository space
[]
Clean up the first space:
All data has been deleted from space
Clean up the second space:
All data has been deleted from space
```

The same configuration may be achieved in Java-based style:
```java
@Configuration
@Import({Space1Repositories.class, Space2Repositories.class})
@ComponentScan("org.springframework.data.xap.examples.advanced.severalspaces")
public class ContextConfiguration {
}

@Configuration
@EnableXapRepositories(value = "org.springframework.data.xap.examples.advanced.severalspaces.person", gigaspace = "gigaSpace1")
class Space1Repositories {
    @Bean
    public GigaSpace gigaSpace1() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./space1");
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }
}

@Configuration
@EnableXapRepositories(value = "org.springframework.data.xap.examples.advanced.severalspaces.room", gigaspace = "gigaSpace2")
class Space2Repositories {
    @Bean
    public GigaSpace gigaSpace2() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./space2");
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }
}
```
