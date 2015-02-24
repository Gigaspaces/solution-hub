XAP Spring Data - Configuration-java
=======
Simple example how to use Xap Spring Data repository via java based configuration.

For config in java based way you should use `@EnableXapRepositories` annotation for configuration class and create GigaSpace bean.

To run the example, to the `XapRepositoryWithJavaConfig` class. Running the example should produce the next output:

```
...
15:52:12.316 [main] INFO  o.s.d.x.e.XapRepositoryWithJavaConfig - Java configuration repository
...
15:52:14.223 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Save new person Person{id=1, name='Mike', active=true, position='accounting', age=20} ...
15:52:14.266 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Save new person Person{id=2, name='Nick', active=false, position='manager', age=27} ...
15:52:14.266 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Get persons from space: 
15:52:14.312 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Person{id=1, name='Mike', active=true, position='accounting', age=20}
15:52:14.312 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Person{id=2, name='Nick', active=false, position='manager', age=27}
15:52:14.312 [main] INFO  o.s.d.x.e.simple.WriteReadExample - Delete persons from space...
```
