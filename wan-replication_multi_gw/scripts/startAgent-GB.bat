set XAP_LOOKUP_GROUPS=GB

call setExampleEnv.bat

rem PLEASE replace localhost with relevant HOSTNAME in production
set XAP_LOOKUP_LOCATORS=localhost:4366

set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4366
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.system.registryPort=10298
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.start.httpPort=9713

rem Modify this as needed
set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.zones=GB

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 1 gsa.global.gsm 0 gsa.lus 1 gsa.global.lus 0 gsa.gsc 6

set EXT_JAVA_OPTIONS=

set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=8000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10768 -Dcom.gs.zones=GB_GW

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1

set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=6000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10766 -Dcom.gs.zones=GB_GW

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1
