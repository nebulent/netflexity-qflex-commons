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
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;

/**
 * @author MAX
 *
 */
public abstract class AbstractJMSConnection {

    protected String clientId;
    protected Connection connection;
    protected AbstractJMSConnectionFactory connectionFactory;
    protected Map sessions = Collections.synchronizedMap(new HashMap(10));

    /**
     * @param clientId, necessary for durable subscriptions
     * @param connection
     * @param connectionFactory
     */
    public AbstractJMSConnection(String clientId, Connection connection, AbstractJMSConnectionFactory connectionFactory) throws JMSException {
        this.connection = connection;
        this.connectionFactory = connectionFactory;

        // Set client ID, necessary for durable subscriptions.
        if (StringUtils.isNotBlank(clientId)) {
            this.clientId = clientId;
            connection.setClientID(clientId);
        }
    }

    /**
     * @param connection
     * @param connectionFactory
     */
    public AbstractJMSConnection(Connection connection, AbstractJMSConnectionFactory connectionFactory) throws JMSException {
        this(null, connection, connectionFactory);
    }

    /**
     * @return Returns the connectionFactory.
     */
    public AbstractJMSConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * Return qflex session.
     * 
     * @param key
     * @return
     */
    public AbstractJMSSession getSession(String sessionId) {
        synchronized (sessions) {
            return (AbstractJMSSession) sessions.get(sessionId);
        }
    }

    /**
     * @return Returns the sessions.
     */
    public List getSessions() {
        synchronized (sessions) {
            return new ArrayList(sessions.values());
        }
    }

    /**
     * Remove a certain session from the list.
     * @param sessionId
     */
    public void removeSession(String sessionId) {
        synchronized (sessions) {
            sessions.remove(sessionId);
        }
    }

    /**
     * Ovveriden to close all sessions created with this connection.
     * @see javax.jms.Connection#close()
     */
    public void close() throws JMSException {
        // Close all sessions first.
        synchronized (sessions) {
            ListIterator iter = getSessions().listIterator();
            while (iter.hasNext()) {
                AbstractJMSSession session = (AbstractJMSSession) iter.next();
                session.close();
                iter.remove();
            }
        }

        // Remove connection from factory and close it.
        connectionFactory.removeConnection();
        connection.close();
    }

    /**
     * @return Returns the clientId, if exists.
     */
    public String getClientId() {
        return clientId;
    }
}
