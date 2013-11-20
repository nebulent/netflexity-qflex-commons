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
package org.netflexity.api.mb;

import java.io.Serializable;

import javax.jms.JMSException;

import org.netflexity.api.mq.MqException;
import org.netflexity.api.mq.MqQmanagerManager;
import org.netflexity.api.mq.jms.JMSMessageListener;

public interface MbMessageBrokerManager extends Serializable{

    /**
     * Get broker's topology by requesting broker's report response message.
     * 
     * @param mqManager
     * @return
     * @throws JMSException
     * @throws MqException
     */
    public MbMessageBroker getBrokerTopology(MqQmanagerManager mqManager) throws JMSException, MqException;

    /**
     * @param subscription
     * @param listener
     * @param mqManager
     * @throws JMSException
     * @throws MqException
     */
    public void enableFlowStats(MbSubscription subscription, JMSMessageListener listener, MqQmanagerManager mqManager)
            throws JMSException, MqException;

    /**
     * @param subscription
     * @param mqManager
     * @throws JMSException
     * @throws MqException
     */
    public void disableFlowStats(MbSubscription subscription, MqQmanagerManager mqManager)
            throws JMSException, MqException;

    /**
     * @return true if broker's connection information is valid.
     */
    public boolean isConnected();

	/**
	 * @param subscription
	 * @param listener
	 * @param mqManager
	 * @throws JMSException
	 * @throws MqException
	 */
	public void stopFlowStatsConsumption(MbSubscription subscription, JMSMessageListener listener,
			MqQmanagerManager mqManager) throws JMSException, MqException;

	/**
	 * @param subscription
	 * @param listener
	 * @param mqManager
	 * @throws JMSException
	 * @throws MqException
	 */
	public void startFlowStatsConsumption(MbSubscription subscription, JMSMessageListener listener,
			MqQmanagerManager mqManager) throws JMSException, MqException;

	/**
	 * @param subscription
	 * @param mqManager
	 * @throws JMSException
	 * @throws MqException
	 */
	public void destroyFlowStatsSubscription(MbSubscription subscription,
			MqQmanagerManager mqManager) throws JMSException, MqException;

	/**
	 * @param subscription
	 * @param mqManager
	 * @throws JMSException
	 * @throws MqException
	 */
	public void createFlowStatsSubscription(MbSubscription subscription, MqQmanagerManager mqManager)
			throws JMSException, MqException;

}