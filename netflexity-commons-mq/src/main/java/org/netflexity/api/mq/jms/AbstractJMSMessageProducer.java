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
import javax.jms.MessageProducer;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author MAX
 *
 * Message Producer Wrapper.
 */
public abstract class AbstractJMSMessageProducer {

    protected MessageProducer producer;
    protected AbstractJMSSession session;
    private String producerId;
    
    /**
     * @param producer
     * @param session
     */
    public AbstractJMSMessageProducer(MessageProducer producer, AbstractJMSSession session) {
        this.producer = producer;
        this.session = session;
        this.producerId = RandomStringUtils.randomAlphanumeric(10);
    }
    
    /**
     * @return Returns the session.
     */
    public AbstractJMSSession getSession() {
        return session;
    }
    
    /**
     * @return Returns the producerId.
     */
    public String getProducerId() {
        return producerId;
    }
    
    /**
     * Remove producer from session list and close it.
     * @throws JMSException
     */
    public void close() throws JMSException {
        session.removeProducer(producerId);
        producer.close();
    }
}
