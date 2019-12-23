Spring Data Gigaspaces Examples - Native API
==========
This example demonstrates how to get access to the native Gigaspaces API.

To retrieve the GigaSpace instance call `space()` method on the instance of the `GigaspacesRepository` extension.
With an access to the space you will have more possibilities for work with Gigaspaces advanced features.

To run the example, refer to `NativeMain` class. It will delegate the call to `NativeExample` which will show several Gigaspaces specific features. Running the example should produce the next output:

```
...
USE Gigaspaces NATIVE API EXAMPLE
...
You can use get GigaSpace instance from repository and use advanced Gigaspaces features
We are writing an object to the space with 4 seconds to live
Try to get this object immediately: Person{id=1, name='Nick', active=true, position='accounting', age=22}
Wait 5 seconds..
Try to get this object again: null
Write Mary to the space..
You can use write/read modifiers for change behavior of this operation. Next modifier don't allow write, if object already exists.
Try to write Mary again..
Entry already in space!
For another features please read Gigaspaces Documentation.
...
```

To read more on this topic, please, refer to [interacting with space] (http://docs.gigaspaces.com/latest/dev-java/java-tutorial-part1.html).
