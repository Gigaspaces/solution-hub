This is example project that can be used to create the jar files needed for WAN Gateway. This project has 2 child modules: space and gateway. Each module will create a processing unit jar for the space and gateway respectively.

The project also has 2 profiles: central and west. These profiles represent the artifacts needed for the 2 clusters or data centers.

To build for the central cluster use:

`mvn package -P central`

To build for the west cluster use:

`mvn package -P west`

For more details about creating environment specific property files for Maven, see: https://maven.apache.org/guides/mini/guide-building-for-different-environments.html

This is intended for deployment on Kubernetes. However, with a few changes, this can be made to run on premises as well.
