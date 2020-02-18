call setExampleEnv.bat


rem PLEASE replace localhost with relevant HOSTNAME in production
set LOOKUPLOCATORS=localhost:4366
call %GS_HOME%/bin/gs deploy -zones GB wan-space-GB 
call %GS_HOME%/bin/gs deploy -zones GB wan-gateway-GB

set LOOKUPLOCATORS=localhost:4166
call %GS_HOME%/bin/gs deploy -zones HK wan-space-HK
call %GS_HOME%/bin/gs deploy -zones HK wan-gateway-HK

set LOOKUPLOCATORS=localhost:4266
call %GS_HOME%/bin/gs deploy -zones US wan-space-US
call %GS_HOME%/bin/gs deploy -zones US wan-gateway-US


