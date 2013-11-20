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

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

import org.netflexity.api.mq.jms.AbstractJMSConnectionFactory;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnectionFactory;

/**
 * @author Max Fedorov
 *
 * Abstraction of real JMS Queue Connection Factory.
 */
public class JMSQueueConnectionFactory extends AbstractJMSConnectionFactory{

    protected String username;
    protected String password;

    /**
     * @param qmanagerName
     * @param channelName
     * @param host
     * @param port
     * @throws JMSException
     */
    public JMSQueueConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec) throws JMSException {
        super(qmanagerName, channelName, host, port, sslCipherSpec);
    }

    public JMSQueueConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec, String username, String password) throws JMSException {
        super(qmanagerName, channelName, host, port, sslCipherSpec);
        this.username = username;
        this.password = password;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.jms.AbstractQflexConnectionFactory#createConnectionFactory()
     */
    protected ConnectionFactory createConnectionFactory() throws JMSException{
	    // Programatically construct Websphere QM connection connectionfactory.
        MQQueueConnectionFactory queueConnectionfactory = new MQQueueConnectionFactory();
		// This transport type is used when the MQSeries server is on a different host from the client. 
		queueConnectionfactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		// Set the name of the queue manager to connect to.
		queueConnectionfactory.setQueueManager(qmanagerName);
		// Set the name of the host (*required for client transport type).
		queueConnectionfactory.setHostName(host);
		// Set the port (*required for client transport type).
		queueConnectionfactory.setPort(port);
		// Set the channel to use (*required for client transport type).
		queueConnectionfactory.setChannel(channelName);
		
		queueConnectionfactory.setSSLCipherSuite(sslCipherSuite);
                
		return queueConnectionfactory;
	}
    
    /**
     * Create and start connection if it does not exist.
     * 
     * @return
     * @throws JMSException
     */
    public JMSQueueConnection getQueueConnection() throws JMSException {
        synchronized(_lock){
            if(connection == null){
    		    // Create JMS connection.
    			QueueConnection queueConnection = createQueueConnection();
    			// Construct qflex connection.
    			connection = new JMSQueueConnection(queueConnection, this);
    			// Start connection.
    			queueConnection.start();
    		}
    		return (JMSQueueConnection)connection;
        }
    }
    
    /**
     * @return
     * @throws JMSException
     */
    protected QueueConnection createQueueConnection() throws JMSException {
        if(username != null && password != null){
            return ((MQQueueConnectionFactory)connectionfactory).createQueueConnection(username, password);
        }
        return ((MQQueueConnectionFactory)connectionfactory).createQueueConnection();
    }
}
