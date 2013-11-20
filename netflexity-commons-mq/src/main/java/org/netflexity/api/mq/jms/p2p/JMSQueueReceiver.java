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

import javax.jms.QueueReceiver;

import org.netflexity.api.mq.jms.AbstractJMSMessageConsumer;
import org.netflexity.api.mq.jms.AbstractJMSSession;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Queue Receiver.
 */
public class JMSQueueReceiver extends AbstractJMSMessageConsumer{
    
    /**
     * @param receiver
     * @param session
     */
    public JMSQueueReceiver(QueueReceiver receiver, AbstractJMSSession session){
        super(null, receiver, session);
    }
}
