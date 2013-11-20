package com.netflexity.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.stream.XMLInputFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

import com.netflexity.jms.util.ByteMessageConvertor;
import com.netflexity.jms.util.MapMessageConvertor;
import com.netflexity.jms.util.MessageConvertor;
import com.netflexity.jms.util.ObjectMessageConvertor;
import com.netflexity.jms.util.StreamMessageConvertor;
import com.netflexity.jms.util.TextMessageConvertor;
import com.netflexity.jms.util.UnknownMessageConvertor;

public class QueueRestoreJob implements Runnable {

	public enum State {
		NOT_STARTED, IN_PROGRESS, FINISHED, ABORTED;
	}

	private static final Log logger = LogFactory.getLog(QueueSnapshotJob.class);

	protected JmsTemplate jmsTemplate;

	protected Queue queue;

	protected QueueRestoreStats stats;

	protected State state;

	protected MessageSource source;

	protected JmsTransactionManager transactionManager;

	private XMLInputFactory xmlFactory;

	private Map<MessageType, MessageConvertor> converters;

	private int messagesPerTransaction = 500;
	
	public QueueRestoreJob(ConnectionFactory conFactory, Queue queue, MessageSource source) {
		this.jmsTemplate = new JmsTemplate(conFactory);
		this.queue = queue;
		this.stats =  new QueueRestoreStats();
		this.state = State.NOT_STARTED;
		this.source = source;
		this.transactionManager = new JmsTransactionManager(conFactory);
		this.xmlFactory = XMLInputFactory.newInstance();

		converters = new HashMap<MessageType, MessageConvertor>();
		converters.put(MessageType.BYTES, new ByteMessageConvertor());
		converters.put(MessageType.MAP, new MapMessageConvertor());
		converters.put(MessageType.OBJECT, new ObjectMessageConvertor());
		converters.put(MessageType.STREAM, new StreamMessageConvertor());
		converters.put(MessageType.TEXT, new TextMessageConvertor());
		converters.put(MessageType.UNKNOWN, new UnknownMessageConvertor());
	}

	public void run() {
		if (state != state.NOT_STARTED) {
			throw new IllegalStateException("Illegal state to call this function: " + state);
		}
		source.open();
		state = State.IN_PROGRESS;

		jmsTemplate.execute(queue, new ProducerCallback() {

			public Object doInJms(Session session, MessageProducer producer)
					throws JMSException {
				try {
					int messageCount = 0;
					while (source.hasMoreMessages()) {
						Message message = source.nextMessage(session);
						producer.send(message);
						messageCount++;
						if (messageCount == messagesPerTransaction) {
							commit(session);
							messageCount = 0;
						}
					}
					//commit remaining messages
					commit(session);
					return null;
				} catch (Exception e) {
					if(session.getTransacted()) {
						session.rollback();
					}
					throw new RuntimeException(e);
				}
			}
		});
		source.close();
	}

	public int getMessagesPerTransaction() {
		return messagesPerTransaction;
	}

	public void setMessagesPerTransaction(int messagesPerTransaction) {
		this.messagesPerTransaction = messagesPerTransaction;
	}
	
	private void commit(Session session) throws JMSException {
		if(session.getTransacted()) {
			session.commit();
		}
	}

}
