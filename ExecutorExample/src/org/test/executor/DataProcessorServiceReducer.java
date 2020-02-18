package org.test.executor;

import org.openspaces.remoting.RemoteResultReducer;
import org.openspaces.remoting.SpaceRemotingInvocation;
import org.openspaces.remoting.SpaceRemotingResult;

public class DataProcessorServiceReducer implements RemoteResultReducer<String , String>{

	public String reduce(SpaceRemotingResult<String>[] results, SpaceRemotingInvocation sri) throws Exception {
		String result ="";
		for (int i =0 ;i<results.length ; i++)
		{
			result += "\n"+ results[i].getResult();
		}
		return result ;
	}
}
