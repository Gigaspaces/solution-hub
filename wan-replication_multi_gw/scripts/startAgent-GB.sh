. setExampleEnv.sh

export XAP_LOOKUP_GROUPS=GB

# PLEASE replace localhost with relevant HOSTNAME in production
export XAP_LOOKUP_LOCATORS=localhost:4366

export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4366"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.system.registryPort=10298"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.start.httpPort=9713"

# Modify this as needed
export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.zones=GB"

$GS_HOME/bin/gs-agent.sh gsa.gsm 1 gsa.global.gsm 0 gsa.lus 1 gsa.global.lus 0 gsa.gsc 6 &

export EXT_JAVA_OPTIONS=

export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=8000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10768 -Dcom.gs.zones=GB_GW"

$GS_HOME/bin/gs-agent.sh gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1 &

export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=6000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10766 -Dcom.gs.zones=GB_GW"

$GS_HOME/bin/gs-agent.sh gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1 &
