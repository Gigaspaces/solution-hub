package com.gigaspaces.examples.parallelqueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.core.context.GigaSpaceContext;
import org.openspaces.events.polling.SimplePollingContainerConfigurer;
import org.openspaces.events.polling.SimplePollingEventListenerContainer;
import org.springframework.beans.factory.InitializingBean;

public class ProcessorFactory implements InitializingBean{

	@GigaSpaceContext
	GigaSpace space;
	
	@ClusterInfoContext
	ClusterInfo clusterInfo;
	
	List<SimplePollingEventListenerContainer> pcList= new ArrayList<SimplePollingEventListenerContainer> ();

	void createPorcessor(String symbol)
	{
		int partitionID  = 1;
		if (clusterInfo != null)
			partitionID = clusterInfo.getInstanceId();

		Order template = new Order ();
		template.setSymbol(symbol);
		
		Processor pc = new Processor(symbol , partitionID);
		SimplePollingEventListenerContainer pollingEventListenerContainer = new SimplePollingContainerConfigurer(space)
	    .template(template)
	    .eventListener(pc)
	    .pollingContainer();

		System.out.println("Processor for symbol " +symbol+" started on partition "+partitionID );
		pcList.add(pollingEventListenerContainer);
	}   

	@Override
	public void afterPropertiesSet() throws Exception {
		for (int i=0;i<Order.symbols.length ;i++)
		{
			createPorcessor(Order.symbols[i]);	
		}
	}
}