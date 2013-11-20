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

import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.netflexity.api.mq.jms.AbstractJMSMessageProducer;

/**
 * @author FEDORMAX
 *
 * Abstraction of real JMS Topic Publisher.
 */
public class JMSTopicPublisher extends AbstractJMSMessageProducer{
    
    /**
     * @param publisher
     * @param session
     */
    public JMSTopicPublisher(TopicPublisher publisher, JMSTopicSession session) {
        super(publisher, session);
    }
    
    /**
     * @param msg
     * @throws JMSException
     */
    public void publish(String msg) throws JMSException{
        TextMessage txtMsg = session.createTextMessage(msg);
    	getTopicPublisher().publish(txtMsg);
    }
    
    /**
     * @param map
     * @throws JMSException
     */
    public void publish(Map map) throws JMSException{
        MapMessage mapMsg = session.createMapMessage(map);
        getTopicPublisher().publish(mapMsg);
    }
    
    /**
     * @param barr[]
     * @throws JMSException
     */
    public void publish(byte barr[]) throws JMSException{
        BytesMessage bMsg = session.createBytesMessage(barr);
        getTopicPublisher().publish(bMsg);
    }
    
    /**
     * @return
     */
    public TopicPublisher getTopicPublisher(){
        return (TopicPublisher)producer;
    }
    
    /**
     * @return
     */
    public JMSTopicSession getQflexTopicSession(){
        return (JMSTopicSession)session;
    }
    
    /**
     * @return
     */
    public TopicSession getTopicSession(){
        return getQflexTopicSession().getTopicSession();
    }
}
