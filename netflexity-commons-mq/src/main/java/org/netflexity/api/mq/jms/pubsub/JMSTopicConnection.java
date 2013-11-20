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

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.netflexity.api.mq.jms.AbstractJMSConnection;

/**
 * @author Max Fedorov
 *
 * Abstraction of real JMS Topic Connection.
 */
public class JMSTopicConnection extends AbstractJMSConnection{
	
    /**
     * @param clientId
     * @param connection
     * @param connectionFactory
     * @throws JMSException
     */
    public JMSTopicConnection(String clientId, TopicConnection connection, JMSTopicConnectionFactory connectionFactory) throws JMSException {
        super(clientId, connection, connectionFactory);
    }
    
	/**
	 * @param connection
	 * @param connectionFactory
	 */
	public JMSTopicConnection(TopicConnection connection, JMSTopicConnectionFactory connectionFactory) throws JMSException{
		super(connection, connectionFactory);
	}
	
    /**
     * Create non-transacted, auto-acknowledged session.
     * @return
     * @throws JMSException
     */
    public JMSTopicSession createTopicSession() throws JMSException{
        return createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
    }

    /**
     * @param transacted
     * @param acknowledgeType
     * @return
     * @throws JMSException
     */
    public JMSTopicSession createTopicSession(boolean transacted, int acknowledgeType) throws JMSException{
        synchronized(sessions){
			// Create JMS session.
			TopicSession session = getTopicConnection().createTopicSession(transacted, acknowledgeType);
			// Create qflex session.
			JMSTopicSession qflexTopicSession = new JMSTopicSession(session, this);
			// Preserve successfully created session.
			sessions.put(qflexTopicSession.getSessionId(), qflexTopicSession);
			// Return newly created qflex session.
			return qflexTopicSession;
		}
    }
    
    /**
     * @return
     */
    public TopicConnection getTopicConnection(){
        return (TopicConnection)connection;
    }
}