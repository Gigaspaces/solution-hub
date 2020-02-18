. setExampleEnv.sh


# PLEASE replace localhost with relevant HOSTNAME in production
export LOOKUPLOCATORS=localhost:4366
$GS_HOME/bin/gs.sh deploy -zones GB wan-space-GB
$GS_HOME/bin/gs.sh deploy -zones GB wan-gateway-GB

