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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author MAX
 *
 */
public abstract class AbstractJMSSession {

    protected AbstractJMSConnection connection;
    protected Session session;
    protected String sessionId;
    protected Map consumers = Collections.synchronizedMap(new HashMap(10));
    protected Map producers = Collections.synchronizedMap(new HashMap(5));
    
    /**
     * @param session
     * @param connection
     */
    public AbstractJMSSession(Session session, AbstractJMSConnection connection) {
        this.connection = connection;
        this.session = session;
        this.sessionId = RandomStringUtils.randomAlphanumeric(10);
    }
    
    /**
     * @return Returns the connection.
     */
    public AbstractJMSConnection getConnection() {
        return connection;
    }

    /**
     * Return a copy of all the consumers created by this session.
     * 
     * @return List
     */
    public List getConsumers() {
        synchronized(consumers){
            return new ArrayList(consumers.values());
        }
    }
    
    /**
     * Remove a certain consumer from the list.
     * @param consumerId
     */
    public void removeConsumer(String consumerId) {
        synchronized(consumers){
            consumers.remove(consumerId);
        }
    }
    
    /**
     * Return a copy of all the consumers created by this session.
     * 
     * @return List
     */
    public List getProducers() {
        synchronized(producers){
            return new ArrayList(producers.values());
        }
    }
    
    /**
     * Remove a certain producer from the list.
     * @param producerId
     */
    public void removeProducer(String producerId) {
        synchronized(producers){
            producers.remove(producerId);
        }
    }
    
    /**
     * @return Returns the sessionId.
     */
    public String getSessionId() {
        return sessionId;
    }
    
    /** 
     * Overriden to close all objects created by this session.
     * @see javax.jms.Session#close()
     */
    public void close() throws JMSException {
        // Close all associated consumers.
        synchronized(consumers){
	        for (ListIterator iter = getConsumers().listIterator(); iter.hasNext();) {
	            AbstractJMSMessageConsumer consumer = (AbstractJMSMessageConsumer) iter.next();
	            consumer.close();
	            iter.remove();
	        }
        }
        
        // Close all associated producers.
        synchronized(producers){
	        for (Iterator iter = getProducers().listIterator(); iter.hasNext();) {
	            AbstractJMSMessageProducer producer = (AbstractJMSMessageProducer) iter.next();
	            producer.close();
	            iter.remove();
	        }
        }
        
        // Remove session from connection list and close session itself.
        connection.removeSession(sessionId);
        session.close();
    }
    
    /**
     * @return Returns the session.
     */
    public Session getSession() {
        return session;
    }
    
    /**
     * @param msg
     * @return
     * @throws JMSException
     */
    public TextMessage createTextMessage(String msg) throws JMSException{
    	return session.createTextMessage(msg);
    }
    
    /**
     * @param msg
     * @return
     * @throws JMSException
     */
    public MapMessage createMapMessage(Map map) throws JMSException{
    	MapMessage msg = session.createMapMessage();
    	Iterator iter = map.keySet().iterator();
    	while (iter.hasNext()) {
			String key = (String) iter.next();
			msg.setObjectProperty(key, map.get(key));
		}
    	return msg;
    }
    
    /**
     * @param msg
     * @return
     * @throws JMSException
     */
    public BytesMessage createBytesMessage(byte barr[]) throws JMSException{
    	BytesMessage msg = session.createBytesMessage();
    	msg.writeBytes(barr);
    	return msg;
    }
}
