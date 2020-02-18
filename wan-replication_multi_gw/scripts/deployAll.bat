call setExampleEnv.bat

rem PLEASE replace localhost with relevant HOSTNAME in production

set XAP_LOOKUP_LOCATORS=localhost:4266
call %GS_HOME%/bin/gs deploy -zones US wan-space-US
call %GS_HOME%/bin/gs deploy -cluster total_members=2 -zones US_GW wan-gateway-US

set XAP_LOOKUP_LOCATORS=localhost:4366
call %GS_HOME%/bin/gs deploy -zones GB wan-space-GB
call %GS_HOME%/bin/gs deploy -cluster total_members=3 -zones GB_GW wan-gateway-GB
