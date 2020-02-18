. setExampleEnv.sh

# PLEASE replace localhost with relevant HOSTNAME in production

export XAP_LOOKUP_LOCATORS=localhost:4266
$GS_HOME/bin/gs.sh deploy -zones US wan-space-US
$GS_HOME/bin/gs.sh deploy -zones US wan-gateway-US

