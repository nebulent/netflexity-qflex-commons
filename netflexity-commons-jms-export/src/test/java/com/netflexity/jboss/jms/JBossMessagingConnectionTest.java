package com.netflexity.jboss.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class JBossMessagingConnectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		InitialContext ic = null;
		ConnectionFactory cf = null;
		Connection connection =  null;
		ic = new InitialContext();

		cf = (ConnectionFactory)ic.lookup("/ConnectionFactory");
		Queue queue = (Queue)ic.lookup("/queue/DLQ");
		//log("Queue /queue/DLQ exists");

		connection = cf.createConnection();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer sender = session.createProducer(queue);

		TextMessage message = session.createTextMessage("Hello!");
		sender.send(message);
		//log("The message was successfully sent to the " + queue.getQueueName() + " queue");

		MessageConsumer consumer =  session.createConsumer(queue);

		connection.start();

		message = (TextMessage)consumer.receive(2000);

		//log("Received message: " + message.getText());
		//assertEquals("Hello!", message.getText());

		//displayProviderInfo(connection.getMetaData());

	}

}
