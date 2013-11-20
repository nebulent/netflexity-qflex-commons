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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageBroker;

/**
 * @author MAX
 *
 * Represents a definition of a generic message broker.
 */
public class IBMMbMessageBroker implements MbMessageBroker {
    
    private String subQueueName;
    private String qmanagerName;
    private String host;
    private int port;
    private String channelName;
    private String brokerName;
    private String brokerUuid;
    private String brokerVersion = "1";
    
    private List execGroups;
    
    private String sslCipherSpec;
    
    public IBMMbMessageBroker(){
        execGroups = new ArrayList();
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
    public IBMMbMessageBroker(String brokerName, String brokerUuid, String brokerVersion, String subQueueName, String qmanagerName, String host, int port, String channelName, String sslCipherSpec){
        this.brokerName = brokerName;
        this.brokerUuid = brokerUuid;
        if(StringUtils.isNotBlank(brokerVersion)){
            this.brokerVersion = brokerVersion;
        }
        this.subQueueName = subQueueName;
        this.qmanagerName = qmanagerName;
        this.host = host;
        this.port = port;
        this.channelName = channelName;
	this.sslCipherSpec = sslCipherSpec;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getBrokerName()
     */
    public String getBrokerName() {
        return brokerName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setBrokerName(java.lang.String)
     */
    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getBrokerUuid()
     */
    public String getBrokerUuid() {
        return brokerUuid;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setBrokerUuid(java.lang.String)
     */
    public void setBrokerUuid(String brokerUuid) {
        this.brokerUuid = brokerUuid;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getChannelName()
     */
    public String getChannelName() {
        return channelName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setChannelName(java.lang.String)
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getHost()
     */
    public String getHost() {
        return host;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setHost(java.lang.String)
     */
    public void setHost(String host) {
        this.host = host;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getPort()
     */
    public int getPort() {
        return port;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setPort(int)
     */
    public void setPort(int port) {
        this.port = port;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getQmanagerName()
     */
    public String getQmanagerName() {
        return qmanagerName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setQmanagerName(java.lang.String)
     */
    public void setQmanagerName(String qmanagerName) {
        this.qmanagerName = qmanagerName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getSubQueueName()
     */
    public String getSubQueueName() {
        return subQueueName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setSubQueueName(java.lang.String)
     */
    public void setSubQueueName(String subQueueName) {
        this.subQueueName = subQueueName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#getBrokerVersion()
     */
    public String getBrokerVersion() {
        return brokerVersion;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageBroker#setBrokerVersion(java.lang.String)
     */
    public void setBrokerVersion(String brokerVersion) {
        this.brokerVersion = brokerVersion;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageBroker#getExecGroups()
     */
    public List getExecGroups() {
        return execGroups;
    }

    /**
     * @param execGroups The execGroups to set.
     */
    public void addExecGroup(MbExecGroup execGroup) {
        this.execGroups.add(execGroup);
    }

    public String getSslCipherSpec() {
	return sslCipherSpec;
    }
    
    
}