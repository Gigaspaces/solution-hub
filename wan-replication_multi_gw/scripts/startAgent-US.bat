set XAP_LOOKUP_GROUPS=US

call setExampleEnv.bat

rem PLEASE replace localhost with relevant HOSTNAME in production
set XAP_LOOKUP_LOCATORS=localhost:4266

set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4266
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.system.registryPort=10198
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.start.httpPort=9913

rem Modify this as needed
set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.zones=US

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 1 gsa.global.gsm 0 gsa.lus 1 gsa.global.lus 0 gsa.gsc 4

set EXT_JAVA_OPTIONS=

set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=7000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10767 -Dcom.gs.zones=US_GW

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1

set XAP_GSC_OPTIONS=-Xmx128m -Dcom.gs.transport_protocol.lrmi.bind-port=9000 -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=10769 -Dcom.gs.zones=US_GW

start %GS_HOME%/bin/gs-agent.bat gsa.gsm 0 gsa.global.gsm 0 gsa.lus 0 gsa.global.lus 0 gsa.gsc 1
