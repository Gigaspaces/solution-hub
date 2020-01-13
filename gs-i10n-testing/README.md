gs-executor-remoting
====================

This codebase serves as an example of how to do [Executor Based Remoting](http://docs.gigaspaces.com/xap97/executor-based-remoting.html) 
and the [Projection API] (http://docs.gigaspaces.com/xap97/query-partial-results.html). In combination, these techniques
help us to avoid network, memory and CPU overhead.

+ Once you've checked this repository out, put a valid [License Key](http://docs.gigaspaces.com/xap97/license-key.html) 
in the [src/test/resources](https://github.com/jasonnerothin/gs-executor-remoting/tree/master/src/test/resources) 
directory. 
(This demo uses 2 partitions, which is incompatible with the freeware licensing terms.)

+ To build a [Processing Unit](http://docs.gigaspaces.com/xap97/java-tutorial-part5.html), do:

`
	$ gradle -C rebuild clean ear -x test
`

This will produce a [Processing Unit](http://docs.gigaspaces.com/xap97/java-tutorial-part5.html), named **executor-remoting-pu.jar**
in **build/libs**.

+ Start a grid:

`
    $ gs-agent.(sh|bat)  gsa.global.lus 1 gsa.global.gsm 1 gsa.gsc 2
`    

+ Deploy the Processing Unit with settings `cluster_schema=partitioned-sync2backup total_members=2,1` 

+ Run the WatchRepairSuite of tests from the command line: 

`
    $ gradle clean test -i
`    

Two tests are run. [One](https://github.com/jasonnerothin/gs-executor-remoting/blob/master/src/test/scala/com/gigaspaces/sbp/WatchRepairSuite.scala#L88) 
[updates the Gears in a Watch](https://github.com/jasonnerothin/gs-executor-remoting/blob/master/src/main/java/com/gigaspaces/sbp/services/WatchRepair.java#L23) 
using [Executor Based Remoting](http://docs.gigaspaces.com/xap97/executor-based-remoting.html).
The [second](https://github.com/jasonnerothin/gs-executor-remoting/blob/master/src/test/scala/com/gigaspaces/sbp/WatchRepairSuite.scala#L102) 
cuts down on the remoting overhead by [using projection](https://github.com/jasonnerothin/gs-executor-remoting/blob/master/src/test/scala/com/gigaspaces/sbp/WatchRepairSuite.scala#L124).
Using [Projection API](http://docs.gigaspaces.com/xap97/query-partial-results.html) is considered a best practice.