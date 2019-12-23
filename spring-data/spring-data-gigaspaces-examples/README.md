Spring Data Gigaspaces Examples
========================

This project provides a set of examples to get you started using Spring Data Gigaspaces. These examples are designed to work with [Spring Data Gigaspaces integration] (https://github.com/meirfarajGig/spring-data-gigaspaces/tree/spring-data-gigaspaces-14.5) and are organized into the following levels:

# Configuration

These examples cover basic configuration ways of Spring Data Gigaspaces. This currently includes

* [configuration-xml](/examples/configuration/configuration-xml) - Demonstrates configuring repository with XML Schema-based configuration
* [configuration-java](/examples/configuration/configuration-java) - Demonstrates configuring repository with Java configuration

# Basic

These examples cover basic usages of the Spring Data Gigaspaces. This currently includes

* [crud](/examples/basic/crud) - Covers all basic methods provided by GigaspacesRepository
* [query](/examples/basic/query) - Demonstrates the use of query methods
* [gigaspaces-native](/examples/basic/gigaspaces-native) - Shows access and use of native GigaSpaces API
* [custom](/examples/basic/custom) - Demonstrate how to declare custom method for repository implementation

# Advanced

These examples demonstrate the usage of additional features in Spring Data Gigaspaces. This currently includes

* [querydsl](/examples/advanced/querydsl) - Shows how to use Query Dsl predicates
* [projection](/examples/advanced/projection) - Demonstrates the usage of Projections in Gigaspaces
* [changeapi](/examples/advanced/changeapi) - Demonstrates the usage of Change API in Gigaspaces
* [transaction](/examples/advanced/transaction) - Shows how Gigaspaces Transactions can be used
* [document](/examples/advanced/document) - Covers using Space as a Document Store
* [several-spaces](/examples/advanced/several-spaces) - Demonstrates how to explicitly define space for the repository

# Running The Examples

Before executing any of the given examples, you have to download GigaSpaces and install maven plugin.
To install maven plugin run the next script:

```
*Windows*
/tools/maven/installmavenrep.bat

*Unix*
/tools/maven/installmavenrep.sh
```   

Also you have to download spring-data-gigaspaces project and build it with maven using `mvn clean install`.   

This project is built with [Maven] (http://maven.apache.org/) and each example may be built and run with Maven (3.0 or higher) or within your Java IDE.
Detailed instructions for each example may be found in its own README file.

Some of the examples run using Querydsl code, to compile them you will require `Q...` classes for POJOs. To generate them, simply execute next maven command in project root:
```
mvn clean install
```