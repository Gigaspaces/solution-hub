XAP-Native
=======
These example demonstrate how to get access to the native XAP API.

To get access to the XAP GigaSpace instance call `space()` method on the instance of the `XapRepository` extension.
With access to the space you have more possibilities for work with XAP advanced features. 

To run the example, refer to `NativeMain` class. It will delegate configuration to `NativeExample` which will show several advanced features. Running the example should produce the next output:

```
...
15:28:19.035 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - USE XAP NATIVE API EXAMPLE
...
15:28:22.425 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - You can use get GigaSpace instance from repository and use advanced XAP features
15:28:22.432 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - We are writing an object to the space with 4 seconds to live
15:28:22.485 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - Try to get this object immediately: Person{id=1, name='Nick', active=true, position='accounting', age=22}
15:28:22.485 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - Wait 5 seconds.. 
15:28:27.486 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - Try to get this object again: null
15:28:27.486 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - Write Mary to the space.. 
15:28:27.486 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - You can use write/read modifiers for change behavior of this operation. Next modifier don't allow write, if object already exists. 
15:28:27.487 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - Try to write Mary again.. 
15:28:27.490 [main] ERROR o.s.d.x.e.basic.nativ.NativeMain - Entry already in space!
15:28:27.490 [main] INFO  o.s.d.x.e.basic.nativ.NativeMain - For another features please read XAP documentation.
15:28:27.496 [main] INFO  o.s.data.xap.examples.DataSet - All data has been deleted from space
```
To read more on this topic, please, refer to [interacting with space] (http://docs.gigaspaces.com/xap100/java-tutorial-part1.html).
