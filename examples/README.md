Spring Data XAP Examples
============================

This project provides a set of examples to get you started using Spring Data XAP. These examples are designed to work with [Spring Data XAP integration] (http://projects.spring.io/spring-data-xap) and are organized into the following levels:

# Configuration

These examples cover basic configuration ways of Spring Data XAP. This currently includes

* [configuration-xml](/examples/configuration/configuration-xml) - Demonstrates configuring repository with XML Schema-based configuration
* [configuration-java](/examples/configuration-java) - Demonstrates configuring repository with Java configuration

# Basic

These examples cover basic usages of the Spring Data XAP. This currently includes

* [crud](/examples/basic/crud) - Covers all basic methods provided by XapRepository
* [query](/examples/basic/query) - Demonstrates the use of query methods
* [xap-native](/examples/basic/xap-native) - Shows access and use of native GigaSpaces API
* [custom](/examples/basic/custom) - Demonstrate how to declare custom method for repository implementation

# Advanced

These examples demonstrate the usage of additional XAP features in Spring Data XAP. This currently includes

* [query-dsl](/examples/advanced/query-dsl) - Shows how to use Query Dsl predicates
* [projection](/examples/advanced/projection) - Demonstrates the usage of Projections in XAP
* [change-api](/examples/advanced/change-api) - Demonstrates the usage of Change API in XAP

# Running The Examples

Before executing any of the given examples, you have to download GigaSpaces XAP and install maven plugin.
To install maven plugin run the next script:

```
*Windows*
/tools/maven/installmavenrep.bat

*Unix*
/tools/maven/installmavenrep.sh
```

This project is built with [Maven] (http://maven.apache.org/) and each example may be built and run with Maven (3.0 or higher) or within your Java IDE.
Detailed instructions for each example may be found in its own README file.

Some of the examples run using Querydsl code, to compile them you will require `Q...` classes for POJOs. To generate them, simply execute next maven command in project root:
```
mvn clean install
```