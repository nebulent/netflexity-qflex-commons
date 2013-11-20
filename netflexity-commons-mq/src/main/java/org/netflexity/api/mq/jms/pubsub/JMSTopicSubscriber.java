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
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.mq.jms.AbstractJMSMessageConsumer;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Topic Subscriber.
 */
public class JMSTopicSubscriber extends AbstractJMSMessageConsumer {

    private boolean durable;

    /**
     * @param subscriberId
     * @param consumer
     * @param session
     */
    public JMSTopicSubscriber(String subscriberId, TopicSubscriber consumer, JMSTopicSession session) throws JMSException {
	super(subscriberId, consumer, session);
	durable = StringUtils.isNotBlank(subscriberId);
    }

    /**
     * @param consumer
     * @param session
     */
    public JMSTopicSubscriber(TopicSubscriber consumer, JMSTopicSession session) {
	super(null, consumer, session);
    }

    /**
     * @throws JMSException
     */
    public void unsubscribe() throws JMSException {
	// First, close subscriber.
	close();

	// Second, unsubscribe.
	if (durable) {
	    getTopicSession().unsubscribe(consumerId);
	}
		
    }

    /**
     * @return
     */
    public boolean isDurable() {
	return durable;
    }

    /**
     * @return
     */
    public JMSTopicSession getQflexTopicSession() {
	return (JMSTopicSession) session;
    }

    /**
     * @return
     */
    public TopicSubscriber getTopicSubscriber() {
	synchronized (_lock) {
	    return (TopicSubscriber) consumer;
	}
    }

    /**
     * @return
     */
    public TopicSession getTopicSession() {
	return getQflexTopicSession().getTopicSession();
    }
}
