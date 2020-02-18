package com.gigaspaces.examples.parallelqueue;

import com.gigaspaces.annotation.pojo.FifoSupport;
import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass(fifoSupport=FifoSupport.ALL)
public class Order {

	String requestType;
	String orderId;
	Integer id;	
	String symbol;
	Long sendTime;
	Long ackTime;
	
	public final static String requestTypes[] = new String []{"requestType1" , "requestType2" , "requestType3", "requestType4","requestType5"}; 
	public final static String symbols[] = new String []{"A", "B", "C","D","E","F"}; 

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Long getSendTime() {
		return sendTime;
	}
	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public Long getAckTime() {
		return ackTime;
	}
	public void setAckTime(Long ackTime) {
		this.ackTime = ackTime;
	}
	@Override
	public String toString() {
		return "Order ID:"+ orderId+ " symbol " + symbol + " sendTime " +sendTime + " requestType "+requestType;  
	}
	
	@SpaceRouting
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@SpaceId(autoGenerate=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer requestOrderId) {
		this.id = requestOrderId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
