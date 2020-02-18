call setExampleEnv.bat


rem PLEASE replace localhost with relevant HOSTNAME in production
set LOOKUPLOCATORS=localhost:4366
call %GS_HOME%/bin/gs deploy -zones DE wan-space-DE 
call %GS_HOME%/bin/gs deploy -zones DE wan-gateway-DE

set LOOKUPLOCATORS=localhost:4166
call %GS_HOME%/bin/gs deploy -zones RU wan-space-RU
call %GS_HOME%/bin/gs deploy -zones RU wan-gateway-RU

set LOOKUPLOCATORS=localhost:4266
call %GS_HOME%/bin/gs deploy -zones US wan-space-US
call %GS_HOME%/bin/gs deploy -zones US wan-gateway-US

