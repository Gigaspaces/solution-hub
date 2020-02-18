. setExampleEnv.sh
echo $GS_HOME

# PLEASE replace localhost with relevant HOSTNAME in production
export LOOKUPLOCATORS=localhost:4366
$GS_HOME/bin/gs.sh deploy -zones DE $DEPLOY_DIR/wan-space-DE
$GS_HOME/bin/gs.sh deploy -zones DE $DEPLOY_DIR/wan-gateway-DE

export LOOKUPLOCATORS=localhost:4166
$GS_HOME/bin/gs.sh deploy -zones RU $DEPLOY_DIR/wan-space-RU
$GS_HOME/bin/gs.sh deploy -zones RU $DEPLOY_DIR/wan-gateway-RU

export LOOKUPLOCATORS=localhost:4266
$GS_HOME/bin/gs.sh deploy -zones US $DEPLOY_DIR/wan-space-US
$GS_HOME/bin/gs.sh deploy -zones US $DEPLOY_DIR/wan-gateway-US

