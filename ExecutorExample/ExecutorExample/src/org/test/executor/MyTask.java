package org.test.executor;

import java.sql.Time;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.executor.TaskGigaSpace;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.async.AsyncResult;

public class MyTask implements DistributedTask<String, String>{

	@TaskGigaSpace
	transient GigaSpace space;
	
	public String execute() throws Exception {
		Time t = new Time(System.currentTimeMillis());
		System.out.println(t + " MyTask execute called at "+space.getSpace().getURL().getContainerName());
		Thread.sleep(2000);
		return "Partition "+ space.getSpace().getURL().getContainerName()
				+ " invoked execute at "+ t;
	}

	int routing;

	@SpaceRouting
	public Integer routing() {
		return routing;
	}

	public String reduce(List<AsyncResult<String>> results) throws Exception {
		String out = "";

		for (AsyncResult<String> result : results) {
			if (result.getException() != null) {
				throw result.getException();
			}
			out += "\n" +result.getResult();
		}
		return out;
	}
}
	