package org.openspaces.example.masterworker.nonblocking;

import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.openspaces.events.polling.ReceiveHandler;
import org.openspaces.events.polling.receive.ReceiveOperationHandler;
import org.openspaces.events.polling.receive.SingleTakeReceiveOperationHandler;

@EventDriven @Polling (concurrentConsumers=2)
public class Worker {
	
	public Worker ()
	{
		System.out.println("Worker started!");
	}
	
    @EventTemplate
    Request template() {
    	Request template = new Request();
        return template;
    }
    
    @ReceiveHandler
    ReceiveOperationHandler receiveHandler() {
        SingleTakeReceiveOperationHandler receiveHandler = new SingleTakeReceiveOperationHandler();
        receiveHandler.setNonBlocking(true);
        receiveHandler.setNonBlockingFactor(10);
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
    	return reponse;
    }
}
