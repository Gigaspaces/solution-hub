package jms.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JMSSendClient {

	public static void main(String[] args)throws Exception{
		ClassPathXmlApplicationContext ctx=null;
		
		ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		JmsQueueSender sender = (JmsQueueSender)ctx.getBean("sender");
		int i=0;
		while (true)  {
			sender.simpleSend(++i);
			System.out.println("Sent" + i);
			Thread.sleep(1000);
		}
	}

}
