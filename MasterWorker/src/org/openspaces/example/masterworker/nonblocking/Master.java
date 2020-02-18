package org.openspaces.example.masterworker.nonblocking;

import java.sql.Time;
import java.util.Date;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

public class Master {

	static GigaSpace space;
	
	public static void main(String[] args) {
		space= new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace")).gigaSpace(); 

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
		for (int i=0;i<tasks; i++)
		{
			requests [i] = new  Request ();
			requests [i].setJobID(jobId);
			requests [i].setTaskID(jobId + "_"+i);
		}
		
		space.writeMultiple(requests);
		int count = 0;
		Result reponseTemplate = new Result();
		reponseTemplate.setJobID(jobId);
		Result reponses[] = new Result [tasks]; 
		while (true)
		{
			Result reponse = space.take(reponseTemplate,1000);
			if (reponse!=null)
			{
				reponses[count]= reponse ;
				count ++;
			}
			if (count == tasks)
			{
				System.out.println(new Date(System.currentTimeMillis())+ " - Done executing Job " +jobId);
				break;
			}
		}
	}
}
