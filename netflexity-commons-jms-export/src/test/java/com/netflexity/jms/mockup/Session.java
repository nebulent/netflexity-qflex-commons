package com.netflexity.jms.mockup;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

public class Session implements javax.jms.Session {

	public void close() throws JMSException {
		// TODO Auto-generated method stub

	}

	public void commit() throws JMSException {
		// TODO Auto-generated method stub

	}

	public QueueBrowser createBrowser(Queue arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public QueueBrowser createBrowser(Queue arg0, String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public BytesMessage createBytesMessage() throws JMSException {
		// TODO Auto-generated method stub
		return new com.netflexity.jms.mockup.BytesMessage();
	}

	public MessageConsumer createConsumer(Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageConsumer createConsumer(Destination arg0, String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageConsumer createConsumer(Destination arg0, String arg1,
			boolean arg2) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public TopicSubscriber createDurableSubscriber(Topic arg0, String arg1)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public TopicSubscriber createDurableSubscriber(Topic arg0, String arg1,
			String arg2, boolean arg3) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public MapMessage createMapMessage() throws JMSException {
		// TODO Auto-generated method stub
		return new com.netflexity.jms.mockup.MapMessage();
	}

	public Message createMessage() throws JMSException {
		// TODO Auto-generated method stub
		return new com.netflexity.jms.mockup.Message();
	}

	public ObjectMessage createObjectMessage() throws JMSException {
		// TODO Auto-generated method stub
		return new com.netflexity.jms.mockup.ObjectMessage();
	}

	public ObjectMessage createObjectMessage(Serializable arg0)
			throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageProducer createProducer(Destination arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public Queue createQueue(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public StreamMessage createStreamMessage() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public TemporaryQueue createTemporaryQueue() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public TemporaryTopic createTemporaryTopic() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public TextMessage createTextMessage() throws JMSException {
		return new com.netflexity.jms.mockup.TextMessage();
	}

	public TextMessage createTextMessage(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public Topic createTopic(String arg0) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getAcknowledgeMode() throws JMSException {
		// TODO Auto-generated method stub
		return 0;
	}

	public MessageListener getMessageListener() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getTransacted() throws JMSException {
		// TODO Auto-generated method stub
		return false;
	}

	public void recover() throws JMSException {
		// TODO Auto-generated method stub

	}

	public void rollback() throws JMSException {
		// TODO Auto-generated method stub

	}

	public void run() {
		// TODO Auto-generated method stub

	}

	public void setMessageListener(MessageListener arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

	public void unsubscribe(String arg0) throws JMSException {
		// TODO Auto-generated method stub

	}

}
