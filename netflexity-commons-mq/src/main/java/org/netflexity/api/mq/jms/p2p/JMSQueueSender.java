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

import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.netflexity.api.mq.jms.AbstractJMSMessageProducer;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Queue Sender.
 */
public class JMSQueueSender extends AbstractJMSMessageProducer{
    
    /**
     * @param sender
     * @param session
     */
    public JMSQueueSender(QueueSender sender, JMSQueueSession session) {
        super(sender, session);
    }
    
    /**
     * @param msg
     * @throws JMSException
     */
    public void send(String msg) throws JMSException{
        TextMessage txtMsg = session.createTextMessage(msg);
    	getQueueSender().send(txtMsg);
    }
    
    /**
     * @param map
     * @throws JMSException
     */
    public void send(Map map) throws JMSException{
        MapMessage mapMsg = session.createMapMessage(map);
        getQueueSender().send(mapMsg);
    }
    
    /**
     * @param barr[]
     * @throws JMSException
     */
    public void send(byte barr[]) throws JMSException{
        BytesMessage bMsg = session.createBytesMessage(barr);
        getQueueSender().send(bMsg);
    }
    
    /**
     * @return
     */
    public QueueSender getQueueSender(){
        return (QueueSender)producer;
    }
    
    /**
     * @return
     */
    public JMSQueueSession getQflexQueueSession(){
        return (JMSQueueSession)session;
    }
    
    /**
     * @return
     */
    public QueueSession getQueueSession(){
        return getQflexQueueSession().getQueueSession();
    }
}
