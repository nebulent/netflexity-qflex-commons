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
package org.netflexity.api.mb.ibm.util;

import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.MbSubscription;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;

/**
 * @author MAX
 *
 */
public class IBMMbFlowStatsQueueConsumer implements JMSConsumerObject{
    
    private final MbSubscription subscription;
    private final MbMessageBroker broker;
    
    /**
     * @param subscription
     */
    public IBMMbFlowStatsQueueConsumer(MbSubscription subscription, MbMessageBroker broker) {
        this.subscription = subscription;
        this.broker = broker;
    }

    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.jms.pubsub.IConsumerObject#getDestinationName()
     */
    public String getDestinationName() {
        return getQueueName();
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.jms.pubsub.IConsumerObject#getIdentifier()
     */
    public String getIdentifier() {
        return subscription.getSubscriptionId();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getChannelName()
     */
    public String getChannelName() {
        return broker.getChannelName();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getHost()
     */
    public String getHost() {
        return broker.getHost();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getPort()
     */
    public int getPort() {
        return broker.getPort();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getQmanagerName()
     */
    public String getQmanagerName() {
        return broker.getQmanagerName();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getQueueName()
     */
    public String getQueueName() {
        return broker.getSubQueueName();
    }

    public String getSslCipherSpec() {
	return broker.getSslCipherSpec();
    }
    
    
}