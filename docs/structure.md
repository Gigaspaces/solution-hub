### <a name="structure"/>1. Document Structure

This part of the reference documentation explains the core functionality offered by Spring Data XAP.

* [Configuration](#configuration) section contains examples on how to configure space and repository in different ways.
  * [XAP Support](#support) - describes how to configure basic connection to space.
  * [XAP Repositories](#repositories) - introduces Spring Data repository configuration for XAP.
* [Basic Usage](#basic) section introduces XAP support of basic Spring Data features.
  * [Query methods](#query) - shows how to declare CRUD operations in XAP Repositories.
  * [Custom methods](#custom) - shows how to implement custom methods with XAP Repositories.
* [Advanced Usage](#advanced) section contains explanation and examples for querying support and specific XAP features.
  * [XAP Projection API](#projection) - describes how to use [Projection API](http://docs.gigaspaces.com/xap101/query-partial-results.html) integrated with Spring Repositories.
  * [Query DSL Support](#querydsl) - explains the configuration and usage of [Query DSL](http://www.querydsl.com/) with XAP data store.
  * [XAP Change API](#change) - describes how to use [Change API](http://docs.gigaspaces.com/xap101/change-api-overview.html) integrated with Spring Repositories.
