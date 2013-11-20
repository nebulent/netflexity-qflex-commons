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

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import org.netflexity.api.mq.jms.AbstractJMSSession;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Queue Session.
 */
public class JMSQueueSession extends AbstractJMSSession{
    
    /**
     * @param session
     * @param connection
     */
    public JMSQueueSession(QueueSession session, JMSQueueConnection connection) {
        super(session, connection);
    }
    
    /**
     * @param queue
     * @param listener
     * @return
     * @throws JMSException
     */
    public JMSQueueReceiver createReceiver(JMSQueue queue, MessageListener listener) throws JMSException{
		synchronized(consumers){
            // Create subscriber.
		    QueueReceiver receiver = getQueueSession().createReceiver((Queue)queue.getQueue());
		    
		    // Create qflex subscriber.
		    JMSQueueReceiver qflexQueueReceiver = new JMSQueueReceiver(receiver, this);

		    // Add listener to pickup messages.
		    if(listener != null){
		        receiver.setMessageListener(listener);
		    }
		    
		    // Preserve successfully created subscriber.
		    consumers.put(qflexQueueReceiver.getConsumerId(), qflexQueueReceiver);
		    
		    // Return receiver.
			return qflexQueueReceiver;
		}
	}
    
    /**
     * @param receiverId
     * @throws JMSException
     */
    public void stop(String receiverId) throws JMSException{
        synchronized(consumers){
            // Find receiver.
		    JMSQueueReceiver receiver = (JMSQueueReceiver)consumers.get(receiverId);
		    if(receiver != null){
			    // Close receiver.
		        receiver.close();
		    }
		}
    }
    
    /**
     * @param topic
     * @return
     * @throws JMSException
     */
    public JMSQueueSender createSender(JMSQueue queue) throws JMSException{
		// Create sender.
	    QueueSender sender = getQueueSession().createSender(queue.getQueue());
	    
	    // Create qflex sender.
	    JMSQueueSender qflexQueueSender = new JMSQueueSender(sender, this);
	    
	    // Preserve successfully created sender.
	    producers.put(qflexQueueSender.getProducerId(), qflexQueueSender);
	    
		// Return queue sender.
	    return qflexQueueSender;
	}
    
    /**
     * @return
     */
    public QueueSession getQueueSession(){
        return (QueueSession)session;
    }
}
