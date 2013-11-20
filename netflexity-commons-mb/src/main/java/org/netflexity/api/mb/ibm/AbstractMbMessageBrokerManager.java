/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mb.ibm;

import org.apache.log4j.Logger;
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mq.jms.p2p.P2P;
import org.netflexity.api.mq.jms.p2p.P2PManager;
import org.netflexity.api.mq.jms.pubsub.PubSub;
import org.netflexity.api.mq.jms.pubsub.PubSubManager;


/**
 * @author MAX
 *
 */
public abstract class AbstractMbMessageBrokerManager {
    
    protected static Logger logger = Logger.getLogger(AbstractMbMessageBrokerManager.class.getPackage().getName());
    public static final String QFLEX_JMS_CLIENT_ID = "QFLEX";
    
    protected MbMessageBroker broker;
    protected PubSub pubSubManager;
    protected P2P p2pManager;
    
    /**
     * @param broker
     */
    public AbstractMbMessageBrokerManager(MbMessageBroker broker) {
        this.broker = broker;
        this.pubSubManager = PubSubManager.getInstance(QFLEX_JMS_CLIENT_ID);
        this.p2pManager = P2PManager.getInstance();
    }

    /**
     * @param brokerName
     * @param brokerUuid
     * @param brokerVersion
     * @param subQueueName
     * @param qmanagerName
     * @param host
     * @param port
     * @param channelName
     */
    public AbstractMbMessageBrokerManager(String brokerName, String brokerUuid, String brokerVersion, String subQueueName, String qmanagerName, String host, int port, String channelName, String sslCipherSpec){
        this.broker = new IBMMbMessageBroker(brokerName, brokerUuid, brokerVersion, subQueueName, qmanagerName, host, port, channelName, sslCipherSpec);
        this.pubSubManager = PubSubManager.getInstance(QFLEX_JMS_CLIENT_ID);
        this.p2pManager = P2PManager.getInstance();
    }
    
    /**
     * @return Returns the broker.
     */
    public MbMessageBroker getBroker() {
        return broker;
    }

    /**
     * @param broker The broker to set.
     */
    public void setBroker(MbMessageBroker broker) {
        this.broker = broker;
    }
}
