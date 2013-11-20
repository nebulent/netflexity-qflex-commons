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
package org.netflexity.api.mb.ibm;

import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;

import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.MbMessageBrokerManager;
import org.netflexity.api.mb.MbSubscription;
import org.netflexity.api.mb.ibm.parsers.BrokerReportResponseDigester;
import org.netflexity.api.mb.ibm.util.IBMMbArtifactBuilderFactory;
import org.netflexity.api.mb.ibm.util.IBMMbFlowStatsConsumer;
import org.netflexity.api.mb.ibm.util.IBMMbFlowStatsQueueConsumer;
import org.netflexity.api.mb.ibm.util.IBMMbMqsiChangeFlowStats;
import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqMessage;
import org.netflexity.api.mq.MqMessageHeader;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.ibm.IBMMqMessage;
import org.netflexity.api.mq.ibm.IBMMqMessageHeader;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderMessageTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderPersistenceTypeEnum;
import org.netflexity.api.mq.jms.JMSMessageListener;
import org.netflexity.api.mq.jms.JMSMessageListenerContext;
import org.netflexity.api.mq.jms.pubsub.JMSConsumerObject;

/**
 * @author MAX
 *
 */
public class IBMMbMessageBrokerManagerImpl extends AbstractMbMessageBrokerManager implements MbMessageBrokerManager {

    public static final String SYSTEM_BROKER_MODEL_QUEUE = "SYSTEM.BROKER.MODEL.QUEUE";
    public static final String SYSTEM_BROKER_ADMIN_QUEUE = "SYSTEM.BROKER.ADMIN.QUEUE";

    /**
     * @param broker
     */
    public IBMMbMessageBrokerManagerImpl(MbMessageBroker broker) {
	super(broker);
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
    public IBMMbMessageBrokerManagerImpl(String brokerName, String brokerUuid, String brokerVersion, String subQueueName, String qmanagerName, String host, int port, String channelName, String sslCipherSpec) {
	super(brokerName, brokerUuid, brokerVersion, subQueueName, qmanagerName, host, port, channelName, sslCipherSpec);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManager#getBrokerTopology(org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public MbMessageBroker getBrokerTopology(MqQmanagerManager mqManager) throws JMSException, MqException {
	MqMessage message = new IBMMqMessage();
	MqMessageHeader header = new IBMMqMessageHeader();
	message.setMessageHeader(header);
	header.setMessageType(MqMessageHeaderMessageTypeEnum.REQUEST);
	header.setPersistence(MqMessageHeaderPersistenceTypeEnum.NOT_PERSISTENT);
	broker.setBrokerUuid("04cfef48-0a01-0000-0080-ad4c6b86410a"); // fake UUID.
	message.setData(new IBMMbMqsiChangeFlowStats(broker).toTopologyXml());
	logger.debug("Sending fake topology request: " + message.getData());

	// Construct topology tree.
	MbMessageBroker mbBroker = null;
	message = mqManager.consumeRequest(SYSTEM_BROKER_ADMIN_QUEUE, SYSTEM_BROKER_MODEL_QUEUE, 10000, message);
	if (message != null) {
	    String xml = message.getData();
	    mbBroker = new BrokerReportResponseDigester().digest(xml);

	    // Now send the same request but with real Broker UUID.
	    message = new IBMMqMessage();
	    header = new IBMMqMessageHeader();
	    message.setMessageHeader(header);
	    header.setMessageType(MqMessageHeaderMessageTypeEnum.REQUEST);
	    header.setPersistence(MqMessageHeaderPersistenceTypeEnum.NOT_PERSISTENT);
	    message.setData(new IBMMbMqsiChangeFlowStats(mbBroker).toTopologyXml());
	    logger.debug("Sending real broker topology request: " + message.getData());
	    //System.out.println("Sending real broker topology request: " + message.getData());

	    message = mqManager.consumeRequest(SYSTEM_BROKER_ADMIN_QUEUE, SYSTEM_BROKER_MODEL_QUEUE, 10000, message);
	    if (message != null) {
		xml = message.getData();
		MbMessageBroker mbBroker2 = new BrokerReportResponseDigester().digest(xml);

		// Send the same request for all execution groups to retrieve all flows with UUIDs.
		List mbExecGroups = mbBroker2.getExecGroups();
		for (Iterator iter = mbExecGroups.iterator(); iter.hasNext();) {
		    MbExecGroup mbExecGroup = (MbExecGroup) iter.next();
		    message = new IBMMqMessage();
		    header = new IBMMqMessageHeader();
		    message.setMessageHeader(header);
		    header.setMessageType(MqMessageHeaderMessageTypeEnum.REQUEST);
		    header.setPersistence(MqMessageHeaderPersistenceTypeEnum.NOT_PERSISTENT);
		    message.setData(new IBMMbMqsiChangeFlowStats(mbExecGroup).toTopologyXml());
		    logger.debug("Sending real exec group topology request: " + message.getData());
		    //System.out.println("Sending real exec group topology request: " + message.getData());

		    message = mqManager.consumeRequest(SYSTEM_BROKER_ADMIN_QUEUE, SYSTEM_BROKER_MODEL_QUEUE, 10000, message);
		    xml = message.getData();
		    logger.debug("Received flow topology: " + xml);

		    MbMessageBroker mbBroker3 = new BrokerReportResponseDigester().digest(xml);
		    List execGroups = mbBroker3.getExecGroups();
		    for (Iterator iterator = execGroups.iterator(); iterator.hasNext();) {
			MbExecGroup mbExecGroup2 = (MbExecGroup) iterator.next();
			mbExecGroup2.setExecGroupName(mbExecGroup.getExecGroupName());
			((IBMMbMessageBroker) mbBroker).addExecGroup(mbExecGroup2);
		    }
		}
	    }
	}
	return mbBroker;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageBrokerManager#createFlowStatsSubscription(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void createFlowStatsSubscription(MbSubscription subscription, MqQmanagerManager mqManager) throws JMSException, MqException {
	String xml = IBMMbArtifactBuilderFactory.createArtifactBuilder(subscription.getSubscriptionType())
		.getActivateFlowStatsMessage(subscription.getSubscriptionObject(), subscription.getNodeDataLevel());

	MqMessage message = new IBMMqMessage();
	MqMessageHeader header = new IBMMqMessageHeader();
	message.setMessageHeader(header);
	header.setMessageType(MqMessageHeaderMessageTypeEnum.REQUEST);
	header.setPersistence(MqMessageHeaderPersistenceTypeEnum.NOT_PERSISTENT);
	message.setData(xml);
	logger.debug("Sending activate flow statistics request: " + message.getData());

	// Start flowstats snapshot generation.
	message = mqManager.consumeRequest(SYSTEM_BROKER_ADMIN_QUEUE, SYSTEM_BROKER_MODEL_QUEUE, 10000, message);
	if (message != null) {
	    logger.debug("Received activate mqsichangeflowstats request: " + message.getData());
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageBrokerManager#destroyFlowStatsSubscription(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void destroyFlowStatsSubscription(MbSubscription subscription, MqQmanagerManager mqManager) throws JMSException, MqException {
	String xml = IBMMbArtifactBuilderFactory.createArtifactBuilder(subscription.getSubscriptionType())
		.getInactivateFlowStatsMessage(subscription.getSubscriptionObject(), subscription.getNodeDataLevel());

	MqMessage message = new IBMMqMessage();
	MqMessageHeader header = new IBMMqMessageHeader();
	message.setMessageHeader(header);
	header.setMessageType(MqMessageHeaderMessageTypeEnum.REQUEST);
	header.setPersistence(MqMessageHeaderPersistenceTypeEnum.NOT_PERSISTENT);
	message.setData(xml);
	logger.debug("Sending inactivate flow statistics request: " + message.getData());

	// Stop flowstats snapshot generation.
	message = mqManager.consumeRequest(SYSTEM_BROKER_ADMIN_QUEUE, SYSTEM_BROKER_MODEL_QUEUE, 10000, message);
	if (message != null) {
	    logger.debug("Received inactivate mqsichangeflowstats request: " + message.getData());
	}
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageBrokerManager#startFlowStatsConsumption(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.jms.JMSMessageListener, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void startFlowStatsConsumption(MbSubscription subscription, JMSMessageListener listener, MqQmanagerManager mqManager) throws JMSException, MqException {
	// Start collection of flowstats using point-to-point queue receiver.
	p2pManager.receive(new IBMMbFlowStatsQueueConsumer(subscription, broker), listener);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageBrokerManager#stopFlowStatsConsumption(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.jms.JMSMessageListener, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void stopFlowStatsConsumption(MbSubscription subscription, JMSMessageListener listener, MqQmanagerManager mqManager) throws JMSException, MqException {
	// Stop collection of flowstats using point-to-point queue receiver.
	p2pManager.stop(new IBMMbFlowStatsQueueConsumer(subscription, broker));
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManager#enableFlowStats(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.jms.JMSMessageListener, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void enableFlowStats(MbSubscription subscription, JMSMessageListener listener, MqQmanagerManager mqManager) throws JMSException, MqException {
	// Issue subscription call.
	createFlowStatsSubscription(subscription, mqManager);

	// Unsubscribe to close subscription on the broker's side.
	// Note: subscription might not even exist, but we need to make sure that previous one was closed.
	try {
	    pubSubManager.unsubscribe(new IBMMbFlowStatsConsumer(subscription, broker));
	} catch (JMSException e) {
	    logger.error("It is ok; we tried to close subscription that never existed." + e.getMessage(), e);
	} catch (Throwable e) {
	    logger.error("It is ok; we tried to close subscription that never existed." + e.getMessage(), e);
	}

	// Start collection of flowstats.
	pubSubManager.subscribeDurable(new IBMMbFlowStatsConsumer(subscription, broker), listener);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManager#disableFlowStats(org.netflexity.api.mb.MbSubscription, org.netflexity.api.mq.MqQmanagerManager)
     */
    @Override
    public void disableFlowStats(MbSubscription subscription, MqQmanagerManager mqManager) throws JMSException, MqException {
	// Delete subscription from broker.
	destroyFlowStatsSubscription(subscription, mqManager);

	// Stop collection of flowstats.
	pubSubManager.unsubscribe(new IBMMbFlowStatsConsumer(subscription, broker));
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManager#isConnected()
     */
    @Override
    public boolean isConnected() {
	boolean isConnected = true;
	final JMSMessageListenerContext context = new JMSMessageListenerContext();
	JMSConsumerObject consumer = new NonDurableConnectionTestConsumer(broker);
	try {
	    // Unsubscribe to close subscription on the broker's side.
	    pubSubManager.unsubscribe(consumer);

	    // Subscribe.
	    pubSubManager.subscribe(consumer,
		    new JMSMessageListener() {
			/* (non-Javadoc)
			 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
			 */
			@Override
			public void onMessage(Message arg0) {
			}

			/* (non-Javadoc)
			 * @see org.netflexity.api.mq.jms.JMSMessageListener#getContext()
			 */
			@Override
			public JMSMessageListenerContext getContext() {
			    return context;
			}

			/* (non-Javadoc)
			 * @see org.netflexity.api.mq.jms.JMSMessageListener#isCompleted()
			 */
			@Override
			public boolean isCompleted() {
			    return true;
			}
		    });
	} catch (JMSException e) {
	    logger.error(e.getMessage(), e);
	    isConnected = false;
	} finally {
	    if (isConnected) {
		if (pubSubManager != null) {
		    try {
			pubSubManager.unsubscribe(consumer);
		    } catch (JMSException e) {
			logger.error(e.getMessage(), e);
		    }
		}
	    }
	}
	return isConnected;
    }

    class NonDurableConnectionTestConsumer implements JMSConsumerObject {

	public static final String SYSTEM_JMS_ND_CC_SUBSCRIBER_QUEUE = "SYSTEM.JMS.ND.CC.SUBSCRIBER.QUEUE";
	private final MbMessageBroker broker;

	/**
	 * @param broker
	 */
	public NonDurableConnectionTestConsumer(MbMessageBroker broker) {
	    this.broker = broker;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getQueueName()
	 */
	@Override
	public String getQueueName() {
	    return SYSTEM_JMS_ND_CC_SUBSCRIBER_QUEUE;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getQmanagerName()
	 */
	@Override
	public String getQmanagerName() {
	    return broker.getQmanagerName();
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getPort()
	 */
	@Override
	public int getPort() {
	    return broker.getPort();
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
	    return String.valueOf(broker.getBrokerUuid());
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getHost()
	 */
	@Override
	public String getHost() {
	    return broker.getHost();
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getDestinationName()
	 */
	@Override
	public String getDestinationName() {
	    return "QFLEX/broker/ping";
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.mq.jms.pubsub.JMSConsumerObject#getChannelName()
	 */
	@Override
	public String getChannelName() {
	    return broker.getChannelName();
	}

	@Override
	public String getSslCipherSpec() {
	    return broker.getSslCipherSpec();
	}
	
	
    }
}