xap-spring-data
===============
Below the desired scope of the project. 
1.       Implement the latest CrudRepository , PagingAndSortingRepository in a similar way GemFire implemented it.
2.       All Query lookup strategies should be supported including Property expressions , Special parameter handling , Selectively exposing CRUD methods
3.       Creating repository instances via XML configuration , filters and JavaConfig , Standalone and Manual wiring should be supported.
4.       XAP paging support should be used with the pagination functionality.
5.       Examples similar to https://github.com/spring-projects/spring-gemfire-examples should be provided.
6.       Implement the QueryDslPredicateExecutor to support advances query execution.
7.       Documentation similar to https://spring.io/guides/gs/accessing-data-gemfire/ , https://spring.io/guides/gs/caching-gemfire/  should be provided. 
8.       An example based on http://spring.io/guides/tutorials/data/ that is using XAP instead of GemFire should be provided.
9.       XAP projection and Delta Update should be supported. These obviously are extension to the Spring Data specification. These should be supported using the XAP spirit. Exact interface TBD.
10.    Spring Data JpaRepository should be tested using XAP JPA implementation. List of supported and limitations should be provided as a deliverable.
 
References:
http://docs.spring.io/spring-data/data-commons/docs/current/reference/html/repositories.html
https://blog.42.nl/articles/spring-data-jpa-with-querydsl-repositories-made-easy/
http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-five-querydsl/  
http://docs.spring.io/spring-data-gemfire/docs/1.4.0.RELEASE/api/org/springframework/data/gemfire/repository/support/SimpleGemfireRepository.html
http://projects.spring.io/spring-data-gemfire/
