Xap Spring Data Examples - Common
=======
Contains domain objects for examples and `DataSet` util for set up initial data to space and clean up it.  

`Person` entity contains `@SpaceIndex` and `@SpaceStorageType` annotations.  
`@SpaceIndex` allows you to to index one or more Space class properties for performance improvement. For more details refer to [indexing] (http://docs.gigaspaces.com/xap100/indexing-overview.html)  
`@SpaceStorageType` determines how non-primitive property of Space class is serialized and stored in the space. For more details refer to [storage types] (http://docs.gigaspaces.com/xap100/storage-types---controlling-serialization.html)  
   
To learn about other XAP features refer to [documentation] (http://docs.gigaspaces.com/xap100/programmers-guide.html)  