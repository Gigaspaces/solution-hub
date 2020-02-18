package org.test.executor;

import java.sql.Time;

import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.remoting.RemotingService;
import com.gigaspaces.async.AsyncFuture;


@RemotingService
public class DataProcessorService implements IDataProcessor {

	@ClusterInfoContext
	public ClusterInfo clusteinfo;

    public AsyncFuture<Object>   asyncProcessData(Object data)
    {
    	String out = new Time(System.currentTimeMillis()) + " Partition " +clusteinfo.getInstanceId() + " invoked DataProcessorService with data "+ data;
    	System.out.println(out);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public Object processData(Object data) {
    	
    	String out = new Time(System.currentTimeMillis()) + " Partition " +clusteinfo.getInstanceId() + " invoked DataProcessorService with data "+ data;
    	System.out.println(out);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return out ;
    }
}	
