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

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.netflexity.api.mq.jms.AbstractJMSConnectionFactory;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQTopicConnectionFactory;

/**
 * @author Max Fedorov
 *
 * Abstraction of real JMS Topic Connection Factory.
 */
public class JMSTopicConnectionFactory extends AbstractJMSConnectionFactory{
    
    /**
     * @param qmanagerName
     * @param channelName
     * @param host
     * @param port
     * @throws JMSException
     */
    public JMSTopicConnectionFactory(String qmanagerName, String channelName, String host, int port, String sslCipherSpec) throws JMSException {
        super(qmanagerName, channelName, host, port, sslCipherSpec);
    }
        
    /* (non-Javadoc)
     * @see com.netflexity.qflex.mq.jms.AbstractQflexConnectionFactory#createConnectionFactory()
     */
    protected ConnectionFactory createConnectionFactory() throws JMSException{
	    // Programatically construct Websphere QM connection connectionfactory.
        MQTopicConnectionFactory topicConnectionfactory = new MQTopicConnectionFactory();
		// This transport type is used when the MQSeries server is on a different host from the client. 
		topicConnectionfactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		// Set the name of the queue manager to connect to.
		topicConnectionfactory.setQueueManager(qmanagerName);
		// Set the name of the host (*required for client transport type).
		topicConnectionfactory.setHostName(host);
		// Set the port (*required for client transport type).
		topicConnectionfactory.setPort(port);
		// Set the channel to use (*required for client transport type).
		topicConnectionfactory.setChannel(channelName);
		if(sslCipherSuite != null && sslCipherSuite.trim().length() > 0){
//		    System.out.println("*****************************************");
//		    System.out.println("** createConnectionFactory");
//		    System.out.println("** sslCipherSuite: [" + sslCipherSuite + "]");
		    topicConnectionfactory.setSSLCipherSuite(sslCipherSuite);
		}
        // Set broker version.
        topicConnectionfactory.setBrokerVersion(JMSC.MQJMS_BROKER_V2);
		return topicConnectionfactory;
	}
	
    /**
     * @return
     * @throws JMSException
     */
    public JMSTopicConnection getTopicConnection(String clientID) throws JMSException {
        synchronized(_lock){
            // Create and start connection if it does not exist.
    		if(connection == null){
    		    // Create JMS connection.
    			TopicConnection topicConnection = createTopicConnection();
    			// Construct qflex connection.
    			connection = new JMSTopicConnection(clientID, topicConnection, this);
    			// Start connection.
    			topicConnection.start();
    		}
    		return (JMSTopicConnection)connection;
        }
    }
    
    /**
     * Create new topic connection.
     * 
     * @return
     * @throws JMSException
     */
    protected TopicConnection createTopicConnection() throws JMSException {
        return ((TopicConnectionFactory)connectionfactory).createTopicConnection();
    }
}
