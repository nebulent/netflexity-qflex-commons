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

import java.io.Serializable;

import org.netflexity.api.mb.MbMessageBrokerManagerContext;

/**
 * @author MAX
 *
 */
public class IBMMbMessageBrokerManagerContext implements Serializable, MbMessageBrokerManagerContext{

    private String subQueueName;
    private String qmanagerName;
    private String host;
    private int port;
    private String channelName;
    private String brokerName;
    private String brokerUuid;
    private String brokerVersion;
    private String sslCipherSpec;
    
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
    public IBMMbMessageBrokerManagerContext(String brokerName, String brokerUuid, String brokerVersion, String subQueueName, String qmanagerName, String host, int port, String channelName, String sslCipherSpec){
        this.brokerName = brokerName;
        this.brokerUuid = brokerUuid;
        this.brokerVersion = brokerVersion;
        this.subQueueName = subQueueName;
        this.qmanagerName = qmanagerName;
        this.host = host;
        this.port = port;
        this.channelName = channelName;
	this.sslCipherSpec = sslCipherSpec;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getBrokerName()
     */
    public String getBrokerName() {
        return brokerName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setBrokerName(java.lang.String)
     */
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getBrokerUuid()
     */
    public String getBrokerUuid() {
        return brokerUuid;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setBrokerUuid(java.lang.String)
     */
    public void setBrokerUuid(String brokerUuid) {
        this.brokerUuid = brokerUuid;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getChannelName()
     */
    public String getChannelName() {
        return channelName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setChannelName(java.lang.String)
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getHost()
     */
    public String getHost() {
        return host;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setHost(java.lang.String)
     */
    public void setHost(String host) {
        this.host = host;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getPort()
     */
    public int getPort() {
        return port;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setPort(int)
     */
    public void setPort(int port) {
        this.port = port;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getQmanagerName()
     */
    public String getQmanagerName() {
        return qmanagerName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setQmanagerName(java.lang.String)
     */
    public void setQmanagerName(String qmanagerName) {
        this.qmanagerName = qmanagerName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getSubQueueName()
     */
    public String getSubQueueName() {
        return subQueueName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setSubQueueName(java.lang.String)
     */
    public void setSubQueueName(String subQueueName) {
        this.subQueueName = subQueueName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#getBrokerVersion()
     */
    public String getBrokerVersion() {
        return brokerVersion;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.MbMessageBrokerManagerContext#setBrokerVersion(java.lang.String)
     */
    public void setBrokerVersion(String brokerVersion) {
        this.brokerVersion = brokerVersion;
    }

    public void setSslCipherSpec(String sslCipherSpec) {
	this.sslCipherSpec = sslCipherSpec;
    }

    public String getSslCipherSpec() {
	return sslCipherSpec;
    }
    
}