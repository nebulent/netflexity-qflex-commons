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
package org.netflexity.api.mq;

import java.io.Serializable;

public interface MqChannel extends Serializable{

    /**
     * @return Returns the alterationDate.
     */
    public String getAlterationDate();

    /**
     * @return Returns the alterationTime.
     */
    public String getAlterationTime();
    
    /**
     * @return
     */
    public Integer getBatchHeartbeat();

    /**
     * @return
     */
    public Integer getBatchInterval();

    /**
     * @return
     */
    public Integer getBatchSize();

    /**
     * @return
     */
    public String getChannelDesc();

    /**
     * @return
     */
    public String getChannelName();

    /**
     * @return
     */
    public Integer getChannelType();

    /**
     * @return
     */
    public String getClusterName();

    /**
     * @return
     */
    public String getClusterNamelist();

    /**
     * @return
     */
    public String getConnectionName();

    /**
     * @return
     */
    public Integer getDataConversion();

    /**
     * @return
     */
    public Integer getDiscInterval();

    /**
     * @return
     */
    public Integer getHeartbeatInterval();

    /**
     * @return
     */
    public String getLocalAddress();

    /**
     * @return
     */
    public Integer getLongRetryCount();

    /**
     * @return
     */
    public Integer getLongRetryInterval();

    /**
     * @return
     */
    public Integer getMaxMsgLength();

    /**
     * @return
     */
    public String getMcaName();

    /**
     * @return
     */
    public Integer getMcaType();

    /**
     * @return
     */
    public String getMcaUserIdentifier();

    /**
     * @return
     */
    public String getModeName();

    /**
     * @return
     */
    public String getMsgExit();

    /**
     * @return
     */
    public Integer getMsgRetryCount();

    /**
     * @return
     */
    public String getMsgRetryExit();

    /**
     * @return
     */
    public Integer getMsgRetryInterval();

    /**
     * @return
     */
    public String getMsgRetryUserData();

    /**
     * @return
     */
    public String getMsgUserData();

    /**
     * @return
     */
    public Integer getNetworkPriority();

    /**
     * @return
     */
    public Integer getNonPersistentMsgSpeed();

    /**
     * @return
     */
    public String getPassword();

    /**
     * @return
     */
    public String getTpName();

    /**
     * @return
     */
    public String getQmanagerName();

    /**
     * @return
     */
    public String getReceiveExit();

    /**
     * @return
     */
    public String getReceiveUserData();

    /**
     * @return
     */
    public String getSecurityExit();

    /**
     * @return
     */
    public String getSecurityUserData();

    /**
     * @return
     */
    public String getSendExit();

    /**
     * @return
     */
    public String getSendUserData();

    /**
     * @return
     */
    public Integer getSeqNumberWrap();

    /**
     * @return
     */
    public Integer getShortRetryCount();

    /**
     * @return
     */
    public Integer getShortRetryInterval();

    /**
     * @return
     */
    public String getSslCipherSpec();

    /**
     * @return
     */
    public Integer getSslClientAuth();

    /**
     * @return
     */
    public String getSslPeerName();

    /**
     * @return
     */
    public Integer getTransportType();

    /**
     * @return
     */
    public String getUserIdentifier();

    /**
     * @return
     */
    public String getXmitQueueName();

    /**
     * @return Returns the channelStatus.
     */
    public Integer getChannelStatus();
    
    /**
     * @return Returns the channelInstanceType.
     */
    public Integer getChannelInstanceType();
    
    /**
     * @return Returns the channelStartDate.
     */
    public String getChannelStartDate();
    
    /**
     * @return Returns the channelStartTime.
     */
    public String getChannelStartTime();
    
    /**
     * @return Returns the connectionCount.
     */
    public Integer getConnectionCount();
}