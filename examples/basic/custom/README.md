Spring Data XAP - Custom Repository Methods
===========================================

It is often needed to add custom methods with your implementation to repository interfaces. Spring Data allows you to provide custom repository code and still utilize basic CRUD features and query method functionality. In this example `PersonRepository` is extended with methods declared in `CustomMethods` interface. Pay attention to the next steps that were performed to supply `XapRepository` with custom method code:

1. `CustomMethods` interface was declared

2. `PersonRepositoryImpl` class implementing `CustomMethods` was added with custom code

3. `PersonRepository` was declared extending the `CustomMethods` interface along with `XapRepository`

You can run the example with `CustomMain` class. It will produce the next output:

```
CUSTOM METHOD EXAMPLE
...
Set up message
Call custom method
Hello World
...
```

To read more on this topic, please, refer to [Custom methods](/wiki/Reference-Documentation#custom).