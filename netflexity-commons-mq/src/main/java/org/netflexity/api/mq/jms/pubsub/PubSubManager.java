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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.netflexity.api.mq.jms.JMSMessageListener;
import org.netflexity.api.mq.jms.JMSMessageListenerContext;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 * 
 * This class is the high-level abstraction layer to help 
 * manage all of JMS Pub/Sub related operations.
 */
public class PubSubManager implements PubSub {

    // Singleton instance.
    private static PubSub instance;
    public static Logger logger = Logger.getLogger(PubSubManager.class);
    // Containers for JMS Pub/Sub objects.
    private Map topicConnectionFactories = Collections.synchronizedMap(new HashMap(5));
    private Map topics = Collections.synchronizedMap(new HashMap(15));
    private Map topicSubscribers = Collections.synchronizedMap(new HashMap(15));
    private String clientId;

    /**
     * @param clientId
     */
    PubSubManager(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Obtain singleton instance.
     * @return
     */
    public static synchronized PubSub getInstance(String clientId) {
        if (instance == null) {
            instance = new PubSubManager(clientId);
        }
        return instance;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribeDurable(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public JMSTopicSubscriber subscribeDurable(JMSConsumerObject consumerObject, JMSMessageListener listener) throws JMSException {
        return subscribe(consumerObject, listener, true);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribe(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public JMSTopicSubscriber subscribe(JMSConsumerObject consumerObject, JMSMessageListener listener) throws JMSException {
        return subscribe(consumerObject, listener, false);
    }

    /**
     * Subscribe to a topic, finding an appropriate listener by the name of the
     * implementing IConsumerObject interface. If a subscription with the same id
     * already exists, subscribe request will be ignored.
     *
     * @param consumerObject
     * @param listener
     * @param durable
     * @return
     * @throws JMSException
     */
    private JMSTopicSubscriber subscribe(JMSConsumerObject consumerObject, JMSMessageListener listener, boolean durable) throws JMSException {
        final JMSMessageListenerContext context = listener.getContext();
	
//	System.out.println("Creating subscriber id: " + consumerObject.getIdentifier());
        synchronized (topicSubscribers) {
            JMSTopicSubscriber subscriber = (JMSTopicSubscriber) topicSubscribers.get(consumerObject.getIdentifier());
            if (subscriber == null) {
                // Get connection factory.
                JMSTopicConnectionFactory factory = getTopicConnectionFactory(
			consumerObject.getQmanagerName(),
			consumerObject.getChannelName(),
			consumerObject.getHost(),
			consumerObject.getPort(),
			consumerObject.getSslCipherSpec()
		);
                // Get connection.
                JMSTopicConnection connection = factory.getTopicConnection(clientId);
                // Get session.
                JMSTopicSession session = connection.createTopicSession();
                // Get topic.
                JMSTopic topic = getTopic(factory.getQmanagerName(), consumerObject.getDestinationName(), consumerObject.getQueueName());
                // Set the session in the context.
                context.setSession(session);
                // Set consumer id, the one that will be present in db.
                context.setConsumerId(consumerObject.getIdentifier());

                // Create subscriber.
                if (durable) {
                    subscriber = session.createDurableSubscriber(topic.getTopic(), consumerObject.getIdentifier(), listener);
                    logger.debug("Established durable subscription to " + consumerObject.getDestinationName() + " @ " + factory.getQmanagerName());
                } else {
                    subscriber = session.createSubscriber(topic.getTopic(), listener);
                    logger.debug("Established non-durable subscription to " + consumerObject.getDestinationName() + " @ " + factory.getQmanagerName());
                }

                // Preserve successfully created topic.
                topicSubscribers.put(consumerObject.getIdentifier(), subscriber);
            }
            return subscriber;
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribeOnceNonBlocking(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public void subscribeOnceNonBlocking(final JMSConsumerObject consumerObject, final JMSMessageListener listener) throws JMSException {
        subscribeOnceNonBlocking(false, 1000L, consumerObject, listener);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribeOnceNonBlocking(long, org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public void subscribeOnceNonBlocking(final long sleep, final JMSConsumerObject consumerObject, final JMSMessageListener listener) throws JMSException {
        subscribeOnceNonBlocking(false, sleep, consumerObject, listener);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribeDurableOnceNonBlocking(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public void subscribeDurableOnceNonBlocking(final JMSConsumerObject consumerObject, final JMSMessageListener listener) throws JMSException {
        subscribeOnceNonBlocking(true, 1000L, consumerObject, listener);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#subscribeDurableOnceNonBlocking(long, org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, org.netflexity.api.mq.jms.JMSMessageListener)
     */
    public void subscribeDurableOnceNonBlocking(final long sleep, final JMSConsumerObject consumerObject, final JMSMessageListener listener) throws JMSException {
        subscribeOnceNonBlocking(true, sleep, consumerObject, listener);
    }

    /**
     * @param durable
     * @param sleep
     * @param consumerObject
     * @param listener
     * @throws JMSException
     */
    private void subscribeOnceNonBlocking(boolean durable, final long sleep, final JMSConsumerObject consumerObject, final JMSMessageListener listener) throws JMSException {
        // Subscribe with whichever method selected.
        final JMSTopicSubscriber subscriber;
        if (durable) {
            subscriber = subscribeDurable(consumerObject, listener);
        } else {
            subscriber = subscribe(consumerObject, listener);
        }

        // Wait till listener is executed in a non-blocking way.
        new Thread(new Runnable() {

            public void run() {
                // Wait till listener is done.
                if (listener != null) {
                    while (!listener.isCompleted()) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            logger.error("Failed to sleep and wait for listener because " + e.getMessage(), e);
                        }
                    }

                    // Listener's finished.
                    synchronized (topicSubscribers) {
                        // Remove subscriber.
                        topicSubscribers.remove(consumerObject.getIdentifier());
                    }

                    // Close subscription.
                    try {
                        if (subscriber != null) {
                            subscriber.unsubscribe();
                        }
                    } catch (JMSException e) {
                        logger.error("Failed to close/unsubscribe subscriber because " + e.getMessage(), e);
                    }
                }
            }
        }).start();
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#unsubscribe(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject)
     */
    public void unsubscribe(JMSConsumerObject consumerObject) throws JMSException {
        synchronized (topicSubscribers) {
            JMSTopicSubscriber subscriber = (JMSTopicSubscriber) topicSubscribers.get(consumerObject.getIdentifier());
            if (subscriber != null) {
		
		subscriber.unsubscribe();
		
                logger.debug("Trying to close sessions");
                // Close session if nobody is using it.
                JMSTopicSession session = subscriber.getQflexTopicSession();
                if (session != null) {
                    if (session.getConsumers().isEmpty()) {
                        logger.debug("session id: [" + session.getSessionId() + "], no consumers, closing.");
                        session.close();
                    }else{
                        logger.debug("session id: [" + session.getSessionId() + "], has consumers, skipping.");
                    }
                }

                // Stop subscription.
//                subscriber.unsubscribe();

                logger.debug("Unsubscribed from " + consumerObject.getDestinationName());

                // Remove subscriber from local list.
                topicSubscribers.remove(consumerObject.getIdentifier());

            }
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.jms.pubsub.PubSub#publish(org.netflexity.api.mq.jms.pubsub.JMSConsumerObject, java.lang.String)
     */
    public void publish(JMSConsumerObject consumerObject, String msg) throws JMSException {
        // Get connection factory.
        JMSTopicConnectionFactory factory = getTopicConnectionFactory(
		consumerObject.getQmanagerName(),
		consumerObject.getChannelName(),
		consumerObject.getHost(), 
		consumerObject.getPort(),
		consumerObject.getSslCipherSpec()
	);
        // Get connection.
        JMSTopicConnection connection = factory.getTopicConnection(clientId);
        // Create session.
        JMSTopicSession session = connection.createTopicSession();
        // Get topic.
        JMSTopic topic = getTopic(factory.getQmanagerName(), consumerObject.getDestinationName(), consumerObject.getQueueName());
        // Create subscriber.
        JMSTopicPublisher publisher = session.createPublisher(topic.getTopic());
        // Publish message.
        publisher.publish(msg);

        logger.debug("Published to " + consumerObject.getDestinationName() + " @ " + factory.getQmanagerName());

        // Close session.
        session.close();
    }

    /**
     * Find/create topic.
     * Supposedly, all topics should be retrieved from JNDI.
     *
     * @param qmanagerName
     * @param topicName
     * @param brokerQueueName
     *
     * @return QflexTopic
     * @throws JMSException
     */
    protected JMSTopic getTopic(String qmanagerName, String topicName, String brokerQueueName) throws JMSException {
        String key = qmanagerName + StringConstants.FORWARD_SLASH + topicName + StringConstants.FORWARD_SLASH + brokerQueueName;
        synchronized (topics) {
            JMSTopic qflexTopic = (JMSTopic) topics.get(key);
            if (qflexTopic == null) {
                // Create topic.
                qflexTopic = new JMSTopic(qmanagerName, topicName, brokerQueueName);
                // Preserve successfully created qflex topic.
                topics.put(key, qflexTopic);
            }
            return qflexTopic;
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
    protected JMSTopicConnectionFactory getTopicConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec) throws JMSException {
        String key = qmanagerName + StringConstants.AT + host + StringConstants.COLON + port;
        synchronized (topicConnectionFactories) {
            JMSTopicConnectionFactory factory = (JMSTopicConnectionFactory) topicConnectionFactories.get(key);
            if (factory == null) {
                // Create qflex connection factory.
                factory = new JMSTopicConnectionFactory(qmanagerName, channelName, host, port, sslCipherSpec);
                // Preserve successfully created connection factory.
                topicConnectionFactories.put(key, factory);
            }
            return factory;
        }
    }
}
