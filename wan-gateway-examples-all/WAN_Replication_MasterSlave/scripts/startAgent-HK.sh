. setExampleEnv.sh

export XAP_LOOKUP_GROUPS="HK"

# PLEASE replace localhost with relevant HOSTNAME in production
export XAP_LOOKUP_LOCATORS="localhost:4166"

export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4166"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.system.registryPort=10098"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gigaspaces.start.httpPort=9813"
export EXT_JAVA_OPTIONS="$EXT_JAVA_OPTIONS -Dcom.gs.zones=HK"

# Modify this as needed
export XAP_GSC_OPTIONS="-Xmx128m"

$GS_HOME/bin/gs-agent.sh gsa.gsm 1 gsa.gsc 2
