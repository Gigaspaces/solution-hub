GENERAL 
-------

The project consists of four modules: common, space, client and web-services. The common
module includes all the shared resources and classes between the other modules.

For detailed information on each module please see - http://docs.gigaspaces.com/solution-hub/xap-drools-integration.html

BUILDING, PACKAGING, RUNNING, DEPLOYING
---------------------------------------

Quick list:

* `mvn compile`: Compiles the project.
* `mvn os:run`: Runs the project.
* `mvn test`: Runs the tests in the project.
* `mvn package`: Compiles and packages the project.
* `mvn os:run-standalone`: Runs a packaged application (from the jars).
* `mvn os:deploy`: Deploys the project onto the Service Grid.
* `mvn os:undeploy`: Removes the project from the Service Grid.


In order to build the example, a simple "mvn compile" executed from the root of the 
project will compile all the different modules.

Packaging the application can be done using `mvn package` (note, by default, it also
runs the tests, in order to disable it, use `-DskipTests`). The packaging process jars up 
the common module. The feeder and processor modules packaging process creates a 
"processing unit structure" directory within the target directory called `[app-name]-[module]`.
It also creates a jar from the mentioned directory called `[app-name]-[module].jar`.

In order to simply run both the processor and the feeder (after compiling), `mvn os:run` can be used.
This will run a single instance of the processor and a single instance of the feeder within
the same JVM using the compilation level classpath (no need for packaging). 

A specific module can also be executed by itself, which in this case, executing more than 
one instance of the processing unit can be done. For example, running the processor module with 
a cluster topology of 2 partitions, each with one backup, the following command can be used:
```
mvn os:run -Dmodule=processor -Dcluster="total_members=2,1"
```

In order to run a packaged processing unit, `mvn package os:run-standalone` can be used (if
`mvn package` was already executed, it can be omitted). This operation will run the processing units
using the packaged jar files. Running a specific module with a cluster topology can be executed using:
```
mvn package os:run-standalone -Dmodule=processor -Dcluster="total_members=2,1"
```

Deploying the application requires starting up a GSM and at least 2 GSCs (scripts located under
the bin directory within the GigaSpaces installation). Once started, running "mvn package os:deploy"
will deploy the two processing units. 

When deploying, the SLA elements within each processing unit descriptor (pu.xml) are taken into 
account. This means that by default when deploying the application, 2 partitions, each with 
one backup will be created for the processor, and a single instance of the feeder will be created.

A special note regarding groups and deployment: If the GSM and GSCs were started under a specific 
group, the `-Dgroups=[group-name]` will need to be used in the deploy command.

WORKING WITH ECLIPSE
--------------------

 In order to generate eclipse project the following command need to be executed from the root of
the application: 
```
mvn eclipse:eclipse
``` 

Pointing the Eclipse import existing project wizard to the application root directory will result in 
importing the three modules. 
If this is a fresh Eclipse installation, the `M2_REPO` environment variable needs be defined and pointed 
to the local maven repository (which usually resides under `$USER_HOME/.m2/repository`).

After generating the projects, configure them to work with maven:
for each project, right click on it and select Configure > Convert to Maven Project.

The application itself comes with built in launch targets allowing to run the processor and the 
feeder using Eclipse run (or debug) targets.

