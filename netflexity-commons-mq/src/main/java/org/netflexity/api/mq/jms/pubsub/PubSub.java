/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
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

import org.netflexity.api.mq.jms.JMSMessageListener;

public interface PubSub {

    /**
     * Establish durable subscription that will last even if the client gets
     * disconnected.
     * 
     * @param consumerObject
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSTopicSubscriber subscribeDurable(JMSConsumerObject consumerObject, JMSMessageListener listener)
            throws JMSException;

    /**
     * Establish non-durable subscription.
     * 
     * @param consumerObject
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSTopicSubscriber subscribe(JMSConsumerObject consumerObject, JMSMessageListener listener)
            throws JMSException;

    /**
     * @param consumerObject
     * @param listener
     * @throws JMSException
     */
    public void subscribeOnceNonBlocking(final JMSConsumerObject consumerObject, final JMSMessageListener listener)
            throws JMSException;

    /**
     * @param sleep
     * @param consumerObject
     * @param listener
     * @throws JMSException
     */
    public void subscribeOnceNonBlocking(final long sleep, final JMSConsumerObject consumerObject,
            final JMSMessageListener listener) throws JMSException;

    /**
     * @param consumerObject
     * @param listener
     * @throws JMSException
     */
    public void subscribeDurableOnceNonBlocking(final JMSConsumerObject consumerObject,
            final JMSMessageListener listener) throws JMSException;

    /**
     * @param sleep
     * @param consumerObject
     * @param listener
     * @throws JMSException
     */
    public void subscribeDurableOnceNonBlocking(final long sleep, final JMSConsumerObject consumerObject,
            final JMSMessageListener listener) throws JMSException;

    /**
     * Unsubscribe from a topic.
     * 
     * @param consumerObject
     */
    public void unsubscribe(JMSConsumerObject consumerObject) throws JMSException;

    /**
     * Publish to a topic.
     * 
     * @param consumerObject
     * @param msg
     * @return
     */
    public void publish(JMSConsumerObject consumerObject, String msg) throws JMSException;

}