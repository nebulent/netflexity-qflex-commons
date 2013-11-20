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

import java.util.HashMap;

/**
 * @author MAX
 *
 */
public class JMSMessageListenerContext extends HashMap {
    
    public static final String SESSION_KEY = "_session";
    public static final String CONSUMER_ID = "_consumerid";
    
    /**
     * @param session
     */
    public JMSMessageListenerContext(AbstractJMSSession session) {
        setSession(session);
    }
    
    /**
     * Default constructor.
     */
    public JMSMessageListenerContext() {
    }

    /**
     * @return
     */
    public String getConsumerId() {
        return (String)get(CONSUMER_ID);
    }
    
    /**
     * @param consumerId
     */
    public void setConsumerId(String consumerId) {
        put(CONSUMER_ID, consumerId);
    }
    
    /**
     * @return
     */
    public AbstractJMSSession getSession() {
        return (AbstractJMSSession)get(SESSION_KEY);
    }
    
    /**
     * @param session
     */
    public void setSession(AbstractJMSSession session) {
        put(SESSION_KEY, session);
    }
}
