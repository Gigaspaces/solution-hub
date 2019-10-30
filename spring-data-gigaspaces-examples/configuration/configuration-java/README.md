XAP Spring Data - Java Configuration
====================================

This is a simple example on how to use Xap Spring Data repository via Java-based configuration.

To configure an application Java-based style you should apply `@EnableXapRepositories` annotation to configuration class and create GigaSpace bean.

To run the example, refer to the `XapRepositoryWithJavaConfig` class. It will produce the next output:

```
...
Java configuration repository
...
Save new person Person{id=1, name='Mike', active=true, position='accounting', age=20} ...
Save new person Person{id=2, name='Nick', active=false, position='manager', age=27} ...
Get persons from space:
Person{id=1, name='Mike', active=true, position='accounting', age=20}
Person{id=2, name='Nick', active=false, position='manager', age=27}
Delete persons from space...
```

To read more on this topic, please, refer to [Configuration Reference](https://github.com/Gigaspaces/xap-spring-data/wiki/Reference-Documentation#configuration).