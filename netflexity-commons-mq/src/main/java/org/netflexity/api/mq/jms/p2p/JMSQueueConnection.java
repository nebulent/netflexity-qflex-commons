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
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.netflexity.api.mq.jms.AbstractJMSConnection;

/**
 * @author Max Fedorov
 *
 * Abstraction of real JMS Queue Connection.
 */
public class JMSQueueConnection extends AbstractJMSConnection{
	
    /**
     * @param clientId
     * @param connection
     * @param connectionFactory
     * @throws JMSException
     */
    public JMSQueueConnection(String clientId, QueueConnection connection, JMSQueueConnectionFactory connectionFactory) throws JMSException {
        super(clientId, connection, connectionFactory);
    }
    
    /**
     * @param connection
     * @param connectionFactory
     */
    public JMSQueueConnection(QueueConnection connection, JMSQueueConnectionFactory connectionFactory) throws JMSException{
        super(connection, connectionFactory);
    }
    
    /**
     * @return
     * @throws JMSException
     */
    public JMSQueueSession createQueueSession() throws JMSException{
        return createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    /**
     * @param transacted
     * @param acknowledgeType
     * @return
     * @throws JMSException
     */
    public JMSQueueSession createQueueSession(boolean transacted, int acknowledgeType) throws JMSException{
        synchronized(sessions){
			// Create JMS session.
			QueueSession session = getQueueConnection().createQueueSession(transacted, acknowledgeType);
			// Create qflex session.
			JMSQueueSession qflexQueueSession = new JMSQueueSession(session, this);
			// Preserve successfully created session.
			sessions.put(qflexQueueSession.getSessionId(), qflexQueueSession);
			// Return newly created qflex session.
			return qflexQueueSession;
		}
    }
    
    /**
     * @return
     */
    public QueueConnection getQueueConnection(){
        return (QueueConnection)connection;
    }
}