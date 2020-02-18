package org.openspaces.example.masterworker.blocking;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoAware;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;

import com.j_spaces.core.client.SpaceURL;

@EventDriven @Polling (concurrentConsumers=1)
public class Worker implements ClusterInfoAware{

    public void setClusterInfo(ClusterInfo clusterInfo) {
    	System.out.println("--------------- > setClusterInfo called");
    	if (clusterInfo != null)
    	{
            this.clusterInfo = clusterInfo;
			System.out.println("--------------- > Worker " + clusterInfo.getInstanceId() + " started");
			
			String total_members = gigaspace.getSpace().getURL().getProperty(SpaceURL.CLUSTER_TOTAL_MEMBERS);
			
			int partitions ; 
			if (total_members != null)
				partitions = Integer.valueOf(total_members .substring(0,total_members.indexOf(","))).intValue();
			else
				partitions =1;

			System.out.println("--------------- > "+ gigaspace.getSpace().getName() +" Space got " + partitions + " partitions ");
	        routingValue = (clusterInfo.getInstanceId() - 1) % partitions ;

			System.out.println("--------------- > Worker "+  clusterInfo.getInstanceId() + " attached to Partition:"+ routingValue );
    	}
		else
		{
			System.out.println("--------------- > Worker started in non clustered mode");
	        routingValue = 0;
		}
    }

    private ClusterInfo clusterInfo;
	
	public Worker (){}
	
    private Integer routingValue;

    @GigaSpaceContext
    GigaSpace gigaspace;
   
    public void afterPropertiesSet() throws Exception {
		
    }
	
    @EventTemplate
    Request template() {
    	Request template = new Request();
    	template.setRouting(routingValue);
        return template;
    }
    
    @ReceiveHandler
    ReceiveOperationHandler receiveHandler() {
        SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
        return receiveHandler;
    }

    @SpaceDataEvent
    public Result execute(Request request) {
        //process Data here
    	try {Thread.sleep(1000);} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Result reponse = new Result ();
    	reponse.setJobID(request.getJobID());
    	reponse.setTaskID(request.getTaskID());
    	reponse.setRouting(request.getRouting());
    	return reponse;
    }
}
