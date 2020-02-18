call setExampleEnv.bat


rem PLEASE replace localhost with relevant HOSTNAME in production
set XAP_LOOKUP_LOCATORS=localhost:4366
call %GS_HOME%\bin\gs deploy -zones GB %DEPLOY_DIR%\wan-space-GB 
call %GS_HOME%\bin\gs deploy -zones GB %DEPLOY_DIR%\wan-gateway-GB

set XAP_LOOKUP_LOCATORS=localhost:4166
call %GS_HOME%\bin\gs deploy -zones HK %DEPLOY_DIR%\wan-space-HK
call %GS_HOME%\bin\gs deploy -zones HK %DEPLOY_DIR%\wan-gateway-HK

set XAP_LOOKUP_LOCATORS=localhost:4266
call %GS_HOME%\bin\gs deploy -zones US %DEPLOY_DIR%\wan-space-US
call %GS_HOME%\bin\gs deploy -zones US %DEPLOY_DIR%\wan-gateway-US


