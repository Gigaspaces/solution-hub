package org.openspaces.example.masterworker.blocking;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.j_spaces.core.client.SpaceURL;

public class Master {

	static GigaSpace space;
	static int partitions;
	
	public static void main(String[] args) {
		space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace")).gigaSpace(); 
		String total_members = space.getSpace().getURL().getProperty(SpaceURL.CLUSTER_TOTAL_MEMBERS);
		if (total_members != null)
			partitions = Integer.valueOf(total_members .substring(0,total_members.indexOf(","))).intValue();
		else
			partitions =1;
		
		for (int i=0;i<10;i++)
		{
			submitJob(i, 100);
		}
		System.exit(0);
	}
	
	static public void submitJob(int jobId , int tasks)
	{
		System.out.println(new Date(System.currentTimeMillis())+ " - Executing Job " +jobId);
		Request requests [] = new Request [tasks]; 
		AtomicInteger index = new AtomicInteger(0);
		for (int i=0;i<tasks; i++)
		{
			requests [i] = new  Request ();
			requests [i].setJobID(jobId);
			requests [i].setTaskID(jobId + "_"+i);
			requests [i].setRouting(index.getAndIncrement() %partitions );
		}
		
		space.writeMultiple(requests);
		int count = 0;
		Result reponseTemplate = new Result();
		reponseTemplate.setJobID(jobId);
		while (true)
		{
			Result _reponses[] = space.takeMultiple(reponseTemplate,Integer.MAX_VALUE);
			if (_reponses.length > 0)
			{
				count = count +_reponses.length;
			}
			if (count == tasks)
			{
				System.out.println(new Date(System.currentTimeMillis())+ " - Done executing Job " +jobId);
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
