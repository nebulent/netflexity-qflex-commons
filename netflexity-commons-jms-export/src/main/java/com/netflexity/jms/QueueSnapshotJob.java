package com.netflexity.jms;

import java.util.Enumeration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

public class QueueSnapshotJob {

	public enum State {
		NOT_STARTED, IN_PROGRESS, FINISHED, ABORTED;
	}
	
	private static final Log logger = LogFactory.getLog(QueueSnapshotJob.class);

	protected MessageStorage storage;

	protected JmsTemplate jmsTemplate;

	protected Queue queue;
	
	protected SnapshotStats stats;
	
	protected State state;

	public QueueSnapshotJob(ConnectionFactory conFactory, Queue queue, MessageStorage storage) {
		this.jmsTemplate = new JmsTemplate(conFactory);
		this.storage = storage;
		this.queue = queue;
		this.stats =  new SnapshotStats();
		this.state = State.NOT_STARTED;

	}

	public void run() {
		if (state != state.NOT_STARTED) {
			throw new RuntimeException("Illegal state to call this function: " + state);
		}
		state = State.IN_PROGRESS;

		jmsTemplate.browse(queue, new BrowserCallback() {

			public Object doInJms(Session session, QueueBrowser browser) throws JMSException {
				Enumeration<Message> messages = browser.getEnumeration();
				storage.openStorage();
				while (messages.hasMoreElements()) {
					Message message = messages.nextElement();
					stats.addMessageStats(storage.saveMessage(message));
					logger.debug("message saved: " + message);
				}	
				storage.closeStorage();
				return null;
			}

		});
		state = State.FINISHED;
	}

	public SnapshotStats getSnapshotStats() {
		return stats;
	}

	public State getSnapshotState() {
		return state;
	}

}
