. setExampleEnv.sh
echo $GS_HOME


# PLEASE replace localhost with relevant HOSTNAME in production
export XAP_LOOKUP_LOCATORS=localhost:4366
$GS_HOME/bin/gs.sh deploy -zones GB $DEPLOY_DIR/wan-space-GB
$GS_HOME/bin/gs.sh deploy -zones GB $DEPLOY_DIR/wan-gateway-GB

export XAP_LOOKUP_LOCATORS=localhost:4166
$GS_HOME/bin/gs.sh deploy -zones HK $DEPLOY_DIR/wan-space-HK
$GS_HOME/bin/gs.sh deploy -zones HK $DEPLOY_DIR/wan-gateway-HK

export XAP_LOOKUP_LOCATORS=localhost:4266
$GS_HOME/bin/gs.sh deploy -zones US $DEPLOY_DIR/wan-space-US
$GS_HOME/bin/gs.sh deploy -zones US $DEPLOY_DIR/wan-gateway-US


