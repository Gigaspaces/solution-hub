Spring Data XAP Examples
============================

This project provides a set of examples to get you started using Spring Data XAP. These examples are designed to work with [Spring Data XAP integration] (http://projects.spring.io/spring-data-xap) and are organized into the following levels:

# Basic

These examples cover basic configuration and usage of Spring Data XAP without any specific features that XAP provides. This currently includes

* configuration-xml - Demonstrates configuring repository with XML Schema-based configuration
* configuration-java - Demonstrates configuring repository with Java configuration
* interaction-with-space - Covers all basic methods provided by XapRepository
* transaction - Demonstrates the use of XAP transactions

# Advanced

These examples demonstrate the usage of additional XAP features in Spring Data XAP. This currently includes

* query-dsl - Shows how to use Query Dsl predicates
* projection - Demonstrates the usage of Projections in XAP
* change-api - Demonstrates the usage of Change API in XAP

# Running The Examples

Before executing any of the given examples, you have to download and run GigaSpaces XAP.
Please, refer to [Installation Guide] (http://docs.gigaspaces.com/xap100/installation.html) to install the platform.
To deploy the Data Grid on you local machine to host the Space for these examples, run next scripts from the products 'bin' folder

        *Windows*
        gs-agent.bat
        gs.bat deploy-space -cluster total_members=1,1 example-space
        
        *Unix*
        ./gs-agent.sh
        ./gs.sh deploy-space -cluster total_members=1,1 example-space

Please, refer to [XAP in 5 minutes] (http://docs.gigaspaces.com/xap100/your-first-data-grid-application.html) for the details on Data Grid basic configuration.

This project is built with [Maven] (http://maven.apache.org/) and each example may be built and run with Maven (3.0 or higher) or within your Java IDE.
Detailed instructions for each example may be found in its own README file.