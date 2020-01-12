set JAVA_HOME=C:\DevTools\Java\jdk1.6.0_23
set JSHOMEDIR=C:\DevTools\GigaSpaces\gigaspaces-xap-premium-8.0.1-ga
set GS_HOME=%JSHOMEDIR%
set ANT_HOME=C:\DevTools\apache-ant-1.8.1\
set M2_HOME=%JSHOMEDIR%\tools\maven\apache-maven-2.0.9

set LOGDIR=C:\Dev\GigaSpaces\logs
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%M2_HOME%\bin;%JSHOMEDIR%\bin;%PATH%

set LOOKUPGROUPS=gs
set NIC_ADDR=192.168.1.114
rem set NIC_ADDR=70.4.35.165
set LOOKUPLOCATORS=%NIC_ADDR%

rem set GS_LOGGING_CONFIG_FILE=%LOGDIR%\gs_logging.properties

set COMMON_JAVA_OPTIONS=-Dcom.gs.multicast.enabled=false ^
-XX:MaxPermSize=128m ^
-Dcom.gigaspaces.logger.RollingFileHandler.filename-pattern=%LOGDIR%\{date,yyyy-MM-dd~HH.mm}-{service}-{pid}.log

set JAVA_OPTIONS=-Xmx128M ^
  %COMMON_JAVA_OPTIONS%

@call %JSHOMEDIR%/bin/setenv.bat