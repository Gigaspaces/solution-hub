### <a name="structure"/>1. Document Structure

This part of the reference documentation explains the core functionality offered by Spring Data Gigaspaces.

* [Configuration](#configuration) section contains examples on how to configure space and repository in different ways.
  * [Gigaspaces Support](#support) - describes how to configure basic connection to space.
  * [Gigaspaces Repositories](#repositories) - introduces Spring Data repository configuration for Gigaspaces.
* [Basic Usage](#basic) section introduces Gigaspaces support of basic Spring Data features.
  * [Query Methods](#query) - shows how to declare CRUD operations in Gigaspaces Repositories.
  * [Custom Methods](#custom) - shows how to implement custom methods with Gigaspaces Repositories.
* [Advanced Usage](#advanced) section contains explanation and examples for querying support and specific Gigaspaces features.
  * [Gigaspaces Projection API](#projection) - describes how to use [Projection API](https://docs.gigaspaces.com/latest/dev-java/query-partial-results.html?Highlight=projection) integrated with Spring Repositories.
  * [Query DSL Support](#querydsl) - explains the configuration and usage of [Query DSL](http://www.querydsl.com/) with Gigaspaces data store.
  * [Gigaspaces Change API](#change) - describes how to use [Change API](http://docs.gigaspaces.com/latest/dev-java/change-api-overview.html) integrated with Spring Repositories.
  * [Gigaspaces Take Operations](#take) - shows take methods usage with simple amd Querydsl syntax.
  * [Gigaspaces Lease Time](#lease) - shows how to use [Lease Time](http://docs.gigaspaces.com/latest/dev-java/leases-automatic-expiration.html).
  * [Gigaspaces Transactions](#transaction) - gives an example of using the [Transactions](http://docs.gigaspaces.com/latest/dev-java/transaction-overview.html).
  * [Gigaspaces Document Storage Support](#document) - introduces support of [Document API](http://docs.gigaspaces.com/latest/dev-java/document-api.html).