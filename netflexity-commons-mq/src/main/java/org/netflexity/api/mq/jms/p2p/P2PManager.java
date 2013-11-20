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
package org.netflexity.api.mq.jms.p2p;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.netflexity.api.mq.jms.JMSMessageListener;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 * 
 * This class is the high-level abstraction layer to help 
 * manage all of JMS Point-to-Point related operations.
 */
public class P2PManager implements P2P {

    // Singleton instance.
    private static P2P instance;
    // Containers for JMS P2P objects.
    private Map queueConnectionFactories = Collections.synchronizedMap(new HashMap(5));
    private Map queues = Collections.synchronizedMap(new HashMap(15));
    private Map queueReceivers = Collections.synchronizedMap(new HashMap(15));

    P2PManager() {
    }

    /**
     * Obtain singleton instance.
     * @return
     */
    public static synchronized P2P getInstance() {
        if (instance == null) {
            instance = new P2PManager();
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.p2p.P2P#receive(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public JMSQueueReceiver receive(JMSConsumerObject consumerObject, JMSMessageListener listener) throws JMSException {
        synchronized (queueReceivers) {
            JMSQueueReceiver receiver = (JMSQueueReceiver) queueReceivers.get(consumerObject.getIdentifier());
            if (receiver == null) {
                // Get connection factory.
                JMSQueueConnectionFactory factory = getQueueConnectionFactory(
			consumerObject.getQmanagerName(), 
			consumerObject.getChannelName(),
			consumerObject.getHost(),
			consumerObject.getPort(),
			consumerObject.getSslCipherSpec()
		);
                // Get connection.
                JMSQueueConnection connection = factory.getQueueConnection();
                // Get session.
                JMSQueueSession session = connection.createQueueSession();
                // Get queue.
                JMSQueue queue = getQueue(factory.getQmanagerName(), consumerObject.getDestinationName());
                // Create receiver.
                receiver = session.createReceiver(queue, listener);

                // Preserve successfully created queue.
                queueReceivers.put(consumerObject.getIdentifier(), receiver);
            }
            return receiver;
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.p2p.P2P#send(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, java.lang.String)
     */
    public JMSQueueSender send(JMSConsumerObject consumerObject, String msg) throws JMSException {
        // Get connection factory.
        JMSQueueConnectionFactory factory = getQueueConnectionFactory(
		consumerObject.getQmanagerName(),
		consumerObject.getChannelName(),
		consumerObject.getHost(),
		consumerObject.getPort(),
		consumerObject.getSslCipherSpec()
	);
        // Get connection.
        JMSQueueConnection connection = factory.getQueueConnection();
        // Get session.
        JMSQueueSession session = connection.createQueueSession();
        // Get queue.
        JMSQueue queue = getQueue(factory.getQmanagerName(), consumerObject.getDestinationName());
        // Create sender.
        JMSQueueSender sender = session.createSender(queue);
        // Send.
        sender.send(msg);
        return sender;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.p2p.P2P#stop(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject)
     */
    public void stop(JMSConsumerObject consumerObject) throws JMSException {
        synchronized (queueReceivers) {
            JMSQueueReceiver receiver = (JMSQueueReceiver) queueReceivers.get(consumerObject.getIdentifier());
            if (receiver != null) {
                // Get session, unsubscribe from queue and close session.
                JMSQueueSession session = (JMSQueueSession) receiver.getSession();
                if (session != null) {
                    // Stop receiver.
                    session.stop(receiver.getConsumerId());

                    // Remove receiver from a local list.
                    queueReceivers.remove(receiver.getConsumerId());

                    // Close session if nobody is using it.
                    if (session.getConsumers().isEmpty()) {
                        // Close session.
                        session.close();
                    }
                }
            }
        }
    }

    /**
     * Find/create queue.
     * Supposedly, all queues should be retrieved from JNDI.
     *
     * @param qmanagerName
     * @param queueName
     * @return QflexQueue
     * @throws JMSException
     */
    protected JMSQueue getQueue(String qmanagerName, String queueName) throws JMSException {
        String key = qmanagerName + StringConstants.FORWARD_SLASH + queueName;
        synchronized (queues) {
            JMSQueue qflexQueue = (JMSQueue) queues.get(key);
            if (qflexQueue == null) {
                // Create qflex queue.
                qflexQueue = new JMSQueue(qmanagerName, queueName);

                // Preserve successfully created qflex queue.
                queues.put(key, qflexQueue);
            }
            return qflexQueue;
        }
    }

    /**
     * Find/create and return connection factory for a certain qmanager.
     * Supposedly, all ConnectionFactory instances should be retrieved from JNDI.
     *
     * @param qmanagerName
     * @param channelName
     * @param host
     * @param port
     * @return
     * @throws JMSException
     */
    protected JMSQueueConnectionFactory getQueueConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec) throws JMSException {
        String key = qmanagerName + StringConstants.AT + host + StringConstants.COLON + port;
        synchronized (queueConnectionFactories) {
            JMSQueueConnectionFactory factory = (JMSQueueConnectionFactory) queueConnectionFactories.get(key);
            if (factory == null) {
                // Create qflex connection factory.
                factory = new JMSQueueConnectionFactory(qmanagerName, channelName, host, port, sslCipherSpec);

                // Preserve successfully created connection factory.
                queueConnectionFactories.put(key, factory);
            }
            return factory;
        }
    }
}
