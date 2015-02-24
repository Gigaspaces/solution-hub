Xap Sprig Data Examples - Native API
==========
This example demonstrates how to get access to the native XAP API.

To retrieve the XAP GigaSpace instance call `space()` method on the instance of the `XapRepository` extension.
With an access to the space you will have more possibilities for work with XAP advanced features.

To run the example, refer to `NativeMain` class. It will delegate the call to `NativeExample` which will show several XAP specific features. Running the example should produce the next output:

```
...
USE XAP NATIVE API EXAMPLE
...
You can use get GigaSpace instance from repository and use advanced XAP features
We are writing an object to the space with 4 seconds to live
Try to get this object immediately: Person{id=1, name='Nick', active=true, position='accounting', age=22}
Wait 5 seconds..
Try to get this object again: null
Write Mary to the space..
You can use write/read modifiers for change behavior of this operation. Next modifier don't allow write, if object already exists.
Try to write Mary again..
Entry already in space!
For another features please read XAP documentation.
...
```

To read more on this topic, please, refer to [interacting with space] (http://docs.gigaspaces.com/xap100/java-tutorial-part1.html).
