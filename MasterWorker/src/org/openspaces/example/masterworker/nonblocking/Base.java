package org.openspaces.example.masterworker.nonblocking;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.annotation.pojo.SpaceProperty.IndexType;

@SpaceClass
public class Base {
	
	public Base (){}
	
	Integer jobID;
	String taskID;	
	Object payload;

	@SpaceProperty(index=IndexType.BASIC)
	@SpaceRouting
	public Integer getJobID() {
		return jobID;
	}
	public void setJobID(Integer jobID) {
		this.jobID = jobID;
	}
	
	@SpaceId(autoGenerate=false)
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
