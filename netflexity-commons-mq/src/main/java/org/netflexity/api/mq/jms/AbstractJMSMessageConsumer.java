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
package org.netflexity.api.mq.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author MAX
 *
 * MessageConsumer wrapper.
 */
public abstract class AbstractJMSMessageConsumer {

    protected MessageConsumer consumer;
    protected AbstractJMSSession session;
    protected String consumerId;
    protected Object _lock = new Object();
    
    /**
     * @param consumerId
     * @param consumer
     * @param session
     */
    public AbstractJMSMessageConsumer(String consumerId, MessageConsumer consumer, AbstractJMSSession session){
        this.consumer = consumer;
        this.session = session;
        this.consumerId = consumerId;
        
        // Set unique consumer id.
        if(consumerId == null){
            this.consumerId = RandomStringUtils.randomAlphanumeric(10);
        }
    }
    
    /**
     * @return Returns the session.
     */
    public AbstractJMSSession getSession() {
        return session;
    }
    
    /**
     * @return Returns the consumerId.
     */
    public String getConsumerId() {
        return consumerId;
    }
    
    /**
     * Remove consumer from session list and close it.
     * @throws JMSException
     */
    public void close() throws JMSException {
        synchronized(_lock){
            session.removeConsumer(consumerId);
            consumer.close();
        }
    }
}
