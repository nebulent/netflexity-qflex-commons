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
package org.netflexity.api.mq.jms.p2p;

import javax.jms.JMSException;

import org.netflexity.api.mq.jms.JMSMessageListener;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;

public interface P2P {

    /**
     * Start receiving messages from queue, finding an appropriate listener by the name of the
     * implementing IConsumerObject interface.
     * 
     * @param consumerObject
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSQueueReceiver receive(JMSConsumerObject consumerObject, JMSMessageListener listener) throws JMSException;

    /**
     * @param consumerObject
     * @param msg
     * @return
     * @throws JMSException
     */
    public JMSQueueSender send(JMSConsumerObject consumerObject, String msg) throws JMSException;

    /**
     * Stop receiving messages from a queue.
     * 
     * @param consumerObject
     * @throws JMSException
     */
    public void stop(JMSConsumerObject consumerObject) throws JMSException;

}