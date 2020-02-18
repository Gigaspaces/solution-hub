package com.gigaspaces.examples.parallelqueue;

import java.util.concurrent.atomic.AtomicInteger;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class Feeder implements Runnable{
	static GigaSpace space = null;
	static final int ORDERS_COUNT =100;
	static AtomicInteger counter = new AtomicInteger(0);
	public static void main(String[] args) {
		space = new GigaSpaceConfigurer (new UrlSpaceConfigurer("jini://*/*/space")).gigaSpace();
		int threadCount = 5;
				
		ThreadPoolTaskExecutor tpExecutor = new ThreadPoolTaskExecutor();
		tpExecutor.setCorePoolSize(threadCount);
		tpExecutor.setMaxPoolSize(threadCount);
		tpExecutor.setQueueCapacity(threadCount);
		tpExecutor.afterPropertiesSet();		
		for (int i=0;i<threadCount;i++)
		{
			tpExecutor.execute(new Feeder());
		}
		tpExecutor.destroy();
	}
	
	public void run() {
		
		System.out.println(Thread.currentThread() + " started");
		for (int i=0;i<ORDERS_COUNT;i++)
		{
			for (int j=0;j<Order.requestTypes.length;j++)
			{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Order o = new Order();
				
				o.setOrderId(Thread.currentThread().getId() + "_" + i);				
				// If we use the Order ID both for the @SpaceID and @SpaceRouting fields we might end up updating the same object
				o.setId(counter.incrementAndGet());
				
				o.setSymbol(Order.symbols[i%Order.symbols.length]);
				o.setSendTime(System.currentTimeMillis());
				String requestType = Order.requestTypes[j] ;
				o.setRequestType(requestType);
				space.write(o);
				System.out.println(Thread.currentThread() +  " send Order " + o);
			}
		}
	}
}
