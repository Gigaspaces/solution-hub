. setExampleEnv.sh

export XAP_LOOKUP_GROUPS=US

# PLEASE replace localhost with relevant HOSTNAME in production
export XAP_LOOKUP_LOCATORS=localhost:4266

export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4266"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.system.registryPort=10198"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.start.httpPort=9913"

# Modify this as needed
export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.zones=US"

$GS_HOME/bin/gs-agent.sh gsa.gsm 1 gsa.global.gsm 0 gsa.lus 1 gsa.global.lus 0 gsa.gsc 4 &

export EXT_JAVA_OPTIONS=

export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=7000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10767 -Dcom.gs.zones=US_GW"

$GS_HOME/bin/gs-agent.sh gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1 &

export XAP_GSC_OPTIONS="-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=9000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10769 -Dcom.gs.zones=US_GW"

$GS_HOME/bin/gs-agent.sh gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1 &
