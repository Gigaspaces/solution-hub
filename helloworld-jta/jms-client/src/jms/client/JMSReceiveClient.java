package jms.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JMSReceiveClient {

	public static void main(String[] args)throws Exception{
		ClassPathXmlApplicationContext ctx=null;
		
		ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		while (true)  {
			Thread.sleep(10);
		}
	}

}
