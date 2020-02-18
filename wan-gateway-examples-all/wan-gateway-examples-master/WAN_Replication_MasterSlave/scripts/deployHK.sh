. setExampleEnv.sh

# PLEASE replace localhost with relevant HOSTNAME in production

export XAP_LOOKUP_LOCATORS=localhost:4166
$GS_HOME/bin/gs.sh deploy -zones HK wan-space-HK
$GS_HOME/bin/gs.sh deploy -zones HK wan-gateway-HK
