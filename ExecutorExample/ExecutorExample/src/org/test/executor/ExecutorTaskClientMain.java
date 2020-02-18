package org.test.executor;

import java.sql.Time;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import com.gigaspaces.async.AsyncFuture;
import com.j_spaces.core.IJSpace;

public class ExecutorTaskClientMain {

	static IJSpace space = null;
	static GigaSpace gigaSpace = null;

	public static void main(String[] args) throws Exception{
		boolean sync = false;
		sync = args[0].equals("sync");

		if (sync)
			System.out.println("Sync Executor benchmark started");
		else
			System.out.println("A-Sync Executor benchmark started");

		space = new UrlSpaceConfigurer("jini://*/*/space").space();
		gigaSpace = new GigaSpaceConfigurer(space).gigaSpace(); 
		int count = 0;
		while(true)
		{
			count++;
			if (sync)
			{
				System.out.println(new Time(System.currentTimeMillis()) + " - Client calling MyTask execute" );
				AsyncFuture<String> future =gigaSpace.execute(new MyTask());
				String result = future.get();
				System.out.println(new Time(System.currentTimeMillis()) + " - Client called MyTask execute - Result:" + result );
				Thread.sleep(1000);
			}
			else
			{
				System.out.println(new Time(System.currentTimeMillis()) + " - Client calling async dataProcessor " );
//				dataProcessor.asyncProcessData("A" + count);
				System.out.println(new Time(System.currentTimeMillis()) + " - Client called async dataProcessor " );
				Thread.sleep(1000);
			}
		}
	}
}