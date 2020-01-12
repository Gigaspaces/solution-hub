@set SCRIPTDIR=%~dp0

@call setDevEnv-8.0.1.bat

cd %JSHOMEDIR%\bin

@call gs-agent.bat
