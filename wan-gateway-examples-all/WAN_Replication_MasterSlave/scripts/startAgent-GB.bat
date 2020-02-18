call setExampleEnv.bat

set XAP_LOOKUP_GROUPS=GB

rem PLEASE replace localhost with relevant HOSTNAME in production
set XAP_LOOKUP_LOCATORS=localhost:4366

set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.sun.jini.reggie.initialUnicastDiscoveryPort=4366
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.system.registryPort=10298
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gigaspaces.start.httpPort=9713
set EXT_JAVA_OPTIONS=%EXT_JAVA_OPTIONS% -Dcom.gs.zones=GB

rem Modify this as needed
set XAP_GSC_OPTIONS=-Xmx128m

%GS_HOME%\bin\gs-agent.bat gsa.gsm 1 gsa.gsc 2
