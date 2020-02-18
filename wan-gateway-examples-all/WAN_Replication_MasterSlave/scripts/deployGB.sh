. setExampleEnv.sh


# PLEASE replace localhost with relevant HOSTNAME in production
export XAP_LOOKUP_LOCATORS=localhost:4366
$GS_HOME/bin/gs.sh deploy -zones GB $DEPLOY_DIR/wan-space-GB
$GS_HOME/bin/gs.sh deploy -zones GB $DEPLOY_DIR/wan-gateway-GB

