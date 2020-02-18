package com.gigaspaces.examples.parallelqueue;

import org.openspaces.core.GigaSpace;
import org.openspaces.events.SpaceDataEventListener;
import org.springframework.transaction.TransactionStatus;

public class Processor implements SpaceDataEventListener<Order>{
	
	Processor(String name,int partitionID )
	{
		this.name = name;
		this.partitionID=partitionID;
	}
	
	String name;
	int partitionID ;
	public void onEvent(Order order, GigaSpace space, TransactionStatus tx,
			Object arg3) {
		long time = System.currentTimeMillis() ;
		long latency = time - order.getSendTime(); 
		
		System.out.println("Time " +time + " partitionID " + partitionID  +" Processor " + name + " Got order: "+ order + " Processing Time "+latency + " ms");
		
	}
}
