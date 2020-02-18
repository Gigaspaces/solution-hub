# WAN-GATEWAY-SAMPLES
The WAN Gateway provides a simple way of creating a cluster topology enabling data from one XAP site to be replicated to one or more remote sites. Any updates to this repository should be reflected in the documentation on the wiki

## Master-Slave Topology
This example of WAN Gateway provides a simple way of creating a master-slave topology enabling data from one XAP site to be replicated to one or more remote sites. In this example, we define three clusters. One in New York, London, and Hong Kong, with New York being the master and the remaining two acting as slaves, any updates to the New York space will propagate to both London and Hong Kong asynchronously

http://docs.gigaspaces.com/sbp/wan-replication-gateway.html

## Multi-Master
This example of WAN Gateway provides a simple way of creating a multi-master topology enabling data from all XAP site to be replicated to all other remote sites. In this example, we define three clusters. One in US, Germany, and Russia, with all instances acting as a master,  updates to any master will replicate to other remote locations

http://docs.gigaspaces.com/sbp/wan-gateway-master-slave-replication.html

## Pass-Through
The example of WAN Gateway allows for the implementation of a pass-through replication topology across clusters of space instances. In this architecture, a site may act as an intermediary for delegating replication requests across two or more other sites.

http://docs.gigaspaces.com/sbp/wan-gateway-pass-through-replication.html

Wan gateway discovery can be configured using network configuration file to translate ip addresses 

http://docs.gigaspaces.com/xap120adm/network-over-nat.html

#### License
Copyright 2015 GigaSpaces Technologies Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
