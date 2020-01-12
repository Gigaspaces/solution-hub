package org.openspaces.example.helloworld.processor;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.example.helloworld.common.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * The processor is passed interesting Objects from its associated
 * PollingContainer
 * <p>
 * The PollingContainer removes objects from the GigaSpace that match the
 * criteria specified for it.
 * <p>
 * Once the Processor receives each Object, it modifies its state and returns it
 * to the PollingContainer which writes them back to the GigaSpace
 * <p/>
 * <p>
 * The PollingContainer is configured in the pu.xml file of this project
 */
public class Processor implements InitializingBean {
	Logger logger = Logger.getLogger(this.getClass().getName());
	private JmsTemplate jmsTemplate;
	private Queue queue;

	private int msgCtr;
	private int rollbacks;

	/**
	 * Process the given Message and return it to the caller. This method is
	 * invoked using OpenSpaces Events when a matching event occurs.
	 * 
	 * @throws JMSException
	 * @throws Exception
	 */
	@SpaceDataEvent
	public Message processMessage(Message msg) throws Exception {
		logger.info("Processor PROCESSING: " + msg + " MessageCtr: " + ++msgCtr);
		myBusinessLogic(msg);

		try {
			sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		if (msg.getId() % 100 == 0) {
			logger.info("Rolling back: " + msg.getId() + " Rollback Counter: "
					+ ++rollbacks);
			throw new Exception("Rollback");
		}
		return msg;
	}

	private void myBusinessLogic(Message msg) {
		msg.setInfo(msg.getInfo() + "World !! Message id: " + msg.getId());
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Processor() {
		logger.info("Processor instantiated, waiting for messages feed...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("In the Processor Bean");
	}

	/**
	 * Send to the queue
	 */
	private void sendMessage(final Message msg) throws IOException,
			JMSException {

		this.jmsTemplate.send(this.queue, new MessageCreator() {
			public javax.jms.Message createMessage(Session session)
					throws JMSException {
				return session.createTextMessage(msg.getInfo());
			}
		});

	}
}
