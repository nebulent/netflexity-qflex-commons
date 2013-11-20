/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq.jms.pubsub;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.netflexity.api.mq.jms.AbstractJMSSession;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Topic Session.
 */
public class JMSTopicSession extends AbstractJMSSession{

    /**
     * @param session
     * @param connection
     */
    public JMSTopicSession(TopicSession session, JMSTopicConnection connection) {
        super(session, connection);
    }
    
    /**
     * @param topic
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSTopicSubscriber createSubscriber(Topic topic, MessageListener listener) throws JMSException{
		synchronized(consumers){
            // Create subscriber.
		    TopicSubscriber subscriber = getTopicSession().createSubscriber(topic);
		    
		    // Create qflex subscriber.
		    JMSTopicSubscriber qflexTopicSubscriber = new JMSTopicSubscriber(subscriber, this);

		    // Add listener to pickup messages.
		    if(listener != null){
		        subscriber.setMessageListener(listener);
		    }
		    
		    // Preserve successfully created subscriber.
		    consumers.put(qflexTopicSubscriber.getConsumerId(), qflexTopicSubscriber);
		    
		    // Return topic subscriber.
			return qflexTopicSubscriber;
		}
	}
    
    /**
     * @param subscriberId
     * @param topic
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSTopicSubscriber createDurableSubscriber(Topic topic, String subscriberId, MessageListener listener) throws JMSException{
		synchronized(consumers){
            // Create subscriber.
		    TopicSubscriber subscriber = getTopicSession().createDurableSubscriber(topic, subscriberId);
		    
		    // Create qflex subscriber.
		    JMSTopicSubscriber qflexTopicSubscriber = new JMSTopicSubscriber(subscriberId, subscriber, this);
		    
		    // Add listener to pickup messages.
		    if(listener != null){
		        subscriber.setMessageListener(listener);
		    }

		    // Preserve successfully created subscriber.
		    consumers.put(qflexTopicSubscriber.getConsumerId(), qflexTopicSubscriber);
		    
		    // Return durable topic subscriner.
			return qflexTopicSubscriber;
		}
	}
    
    /**
     * @param topic
     * @return
     * @throws JMSException
     */
    public JMSTopicPublisher createPublisher(Topic topic) throws JMSException{
		// Create subscriber.
	    TopicPublisher publisher = getTopicSession().createPublisher(topic);
	    
	    // Create qflex publisher.
	    JMSTopicPublisher qflexTopicPublisher = new JMSTopicPublisher(publisher, this);
	    
	    // Preserve successfully created publisher.
	    producers.put(qflexTopicPublisher.getProducerId(), qflexTopicPublisher);
	    
		// Return topic publisher.
	    return qflexTopicPublisher;
	}
    
    /**
     * @return Returns topic session.
     */
    public TopicSession getTopicSession() {
        return (TopicSession)session;
    }
}
