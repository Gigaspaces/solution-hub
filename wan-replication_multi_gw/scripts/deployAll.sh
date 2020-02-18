. setExampleEnv.sh
echo $GS_HOME

# PLEASE replace localhost with relevant HOSTNAME in production

export XAP_LOOKUP_LOCATORS=localhost:4266
$GS_HOME/bin/gs.sh deploy -zones US wan-space-US
$GS_HOME/bin/gs.sh deploy -cluster total_members=2 -zones US_GW wan-gateway-US


export XAP_LOOKUP_LOCATORS=localhost:4366
$GS_HOME/bin/gs.sh deploy -zones GB wan-space-GB
$GS_HOME/bin/gs.sh deploy -cluster total_members=3 -zones GB_GW wan-gateway-GB

