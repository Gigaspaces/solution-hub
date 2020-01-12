package jms.client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsQueueSender {

	private JmsTemplate jmsTemplate;
	private Queue queue;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public void simpleSend(final int i) {

		if (queue != null) {

			this.jmsTemplate.send(this.queue, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage("hello queue world " + i);
				}
			});
		} else {
			this.jmsTemplate.send("weblogic.examples.jms.exampleQueue", new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage("hello queue world " + i);
				}
			});

		}
	}
}