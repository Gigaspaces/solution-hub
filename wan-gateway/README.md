This is example project that can be used to create the jar files needed for WAN Gateway. This project has to child modules: space and gateway. Each module will create a processing unit jar for the space and gateway respectively.

The project also has 2 profiles: central and west. These profiles represent the artifacts needed for the 2 cluster or data centers.

To build for the central cluster use:

`mvn package -P central`

To build for the west cluster use:

`mvn package -P west`

