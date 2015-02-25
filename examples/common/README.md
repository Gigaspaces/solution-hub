Xap Spring Data Examples - Common
=================================
This module contains domain objects for examples and `DataSet` utility class that sets up the initial the data in the space and cleans it up afterwards.

You might notice that `Person` POJO contains `@SpaceIndex` and `@SpaceStorageType` annotations.
`@SpaceIndex` allows you to declare an index on one or more POJO fields for performance improvement. For more details, please, refer to [Indexing] (http://docs.gigaspaces.com/xap101/indexing-overview.html).
`@SpaceStorageType` determines how non-primitive property of Space class is serialized and stored in the space. To find out more on this, please, refer to [storage types] (http://docs.gigaspaces.com/xap101/storage-types---controlling-serialization.html).
   
To learn about other XAP features, refer to [Java Developers Guide] (http://docs.gigaspaces.com/xap101/).