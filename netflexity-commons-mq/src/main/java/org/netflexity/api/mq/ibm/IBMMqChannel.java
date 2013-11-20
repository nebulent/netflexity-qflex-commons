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
package org.netflexity.api.mq.ibm;

import org.netflexity.api.mq.MqChannel;
import org.netflexity.api.mq.ibm.enums.MqChannelAttributeEnum;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class IBMMqChannel extends AbstractIBMMqObject implements MqChannel {
    
    private Integer connectionCount = new Integer(0);
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getAlterationDate()
     */
    @Override
    public String getAlterationDate() {
        return getAttributeAsString(MqChannelAttributeEnum.ALTERATION_DATE);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getAlterationTime()
     */
    @Override
    public String getAlterationTime() {
        return getAttributeAsString(MqChannelAttributeEnum.ALTERATION_TIME);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getBatchHeartbeat()
     */
    @Override
    public Integer getBatchHeartbeat() {
        return getAttributeAsInteger(MqChannelAttributeEnum.BATCH_HB);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getBatchInterval()
     */
    @Override
    public Integer getBatchInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.BATCH_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getBatchSize()
     */
    @Override
    public Integer getBatchSize() {
        return getAttributeAsInteger(MqChannelAttributeEnum.BATCH_SIZE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelDesc()
     */
    @Override
    public String getChannelDesc() {
        return getAttributeAsString(MqChannelAttributeEnum.CHANNEL_DESC);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelName()
     */
    @Override
    public String getChannelName() {
        return getAttributeAsString(MqChannelAttributeEnum.CHANNEL_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelType()
     */
    @Override
    public Integer getChannelType() {
        return getAttributeAsInteger(MqChannelAttributeEnum.CHANNEL_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getClusterName()
     */
    @Override
    public String getClusterName() {
        return getAttributeAsString(MqChannelAttributeEnum.CLUSTER_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getClusterNamelist()
     */
    @Override
    public String getClusterNamelist() {
        return getAttributeAsString(MqChannelAttributeEnum.CLUSTER_NAMELIST);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getConnectionName()
     */
    @Override
    public String getConnectionName() {
        return getAttributeAsString(MqChannelAttributeEnum.CONNECTION_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getDataConversion()
     */
    @Override
    public Integer getDataConversion() {
        return getAttributeAsInteger(MqChannelAttributeEnum.DATA_CONVERSION);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getDiscInterval()
     */
    @Override
    public Integer getDiscInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.DISC_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getHeartbeatInterval()
     */
    @Override
    public Integer getHeartbeatInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.HB_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getLocalAddress()
     */
    @Override
    public String getLocalAddress() {
        return getAttributeAsString(MqChannelAttributeEnum.LOCAL_ADDRESS);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getLongRetryCount()
     */
    @Override
    public Integer getLongRetryCount() {
        return getAttributeAsInteger(MqChannelAttributeEnum.LONG_RETRY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getLongRetryInterval()
     */
    @Override
    public Integer getLongRetryInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.LONG_TIMER);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMaxMsgLength()
     */
    @Override
    public Integer getMaxMsgLength() {
        return getAttributeAsInteger(MqChannelAttributeEnum.MAX_MSG_LENGTH_4CHANNEL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMcaName()
     */
    @Override
    public String getMcaName() {
        return getAttributeAsString(MqChannelAttributeEnum.MCA_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMcaType()
     */
    @Override
    public Integer getMcaType() {
        return getAttributeAsInteger(MqChannelAttributeEnum.MCA_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMcaUserIdentifier()
     */
    @Override
    public String getMcaUserIdentifier() {
        return getAttributeAsString(MqChannelAttributeEnum.MCA_USER_ID);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getModeName()
     */
    @Override
    public String getModeName() {
        return getAttributeAsString(MqChannelAttributeEnum.MODE_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgExit()
     */
    @Override
    public String getMsgExit() {
        return getAttributeAsString(MqChannelAttributeEnum.MSG_EXIT_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgRetryCount()
     */
    @Override
    public Integer getMsgRetryCount() {
        return getAttributeAsInteger(MqChannelAttributeEnum.MR_COUNT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgRetryExit()
     */
    @Override
    public String getMsgRetryExit() {
        return getAttributeAsString(MqChannelAttributeEnum.MR_EXIT_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgRetryInterval()
     */
    @Override
    public Integer getMsgRetryInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.MR_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgRetryUserData()
     */
    @Override
    public String getMsgRetryUserData() {
        return getAttributeAsString(MqChannelAttributeEnum.MR_EXIT_USER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getMsgUserData()
     */
    @Override
    public String getMsgUserData() {
        return getAttributeAsString(MqChannelAttributeEnum.MSG_EXIT_USER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getNetworkPriority()
     */
    @Override
    public Integer getNetworkPriority() {
        return getAttributeAsInteger(MqChannelAttributeEnum.NETWORK_PRIORITY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getNonPersistentMsgSpeed()
     */
    @Override
    public Integer getNonPersistentMsgSpeed() {
        return getAttributeAsInteger(MqChannelAttributeEnum.NPM_SPEED);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getPassword()
     */
    @Override
    public String getPassword() {
        return getAttributeAsString(MqChannelAttributeEnum.PASSWORD);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getTpName()
     */
    @Override
    public String getTpName() {
        return getAttributeAsString(MqChannelAttributeEnum.TP_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getQmanagerName()
     */
    @Override
    public String getQmanagerName() {
        return getAttributeAsString(MqChannelAttributeEnum.Q_MGR_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getReceiveExit()
     */
    @Override
    public String getReceiveExit() {
        return getAttributeAsString(MqChannelAttributeEnum.RCV_EXIT_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getReceiveUserData()
     */
    @Override
    public String getReceiveUserData() {
        return getAttributeAsString(MqChannelAttributeEnum.RCV_EXIT_USER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSecurityExit()
     */
    @Override
    public String getSecurityExit() {
        return getAttributeAsString(MqChannelAttributeEnum.SEC_EXIT_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSecurityUserData()
     */
    @Override
    public String getSecurityUserData() {
        return getAttributeAsString(MqChannelAttributeEnum.SEC_EXIT_USER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSendExit()
     */
    @Override
    public String getSendExit() {
        return getAttributeAsString(MqChannelAttributeEnum.SEND_EXIT_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSendUserData()
     */
    @Override
    public String getSendUserData() {
        return getAttributeAsString(MqChannelAttributeEnum.SEND_EXIT_USER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSeqNumberWrap()
     */
    @Override
    public Integer getSeqNumberWrap() {
        return getAttributeAsInteger(MqChannelAttributeEnum.SEQUENCE_NUMBER_WRAP);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getShortRetryCount()
     */
    @Override
    public Integer getShortRetryCount() {
        return getAttributeAsInteger(MqChannelAttributeEnum.SHORT_RETRY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getShortRetryInterval()
     */
    @Override
    public Integer getShortRetryInterval() {
        return getAttributeAsInteger(MqChannelAttributeEnum.SHORT_TIMER);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSslCipherSpec()
     */
    @Override
    public String getSslCipherSpec() {
        return getAttributeAsString(MqChannelAttributeEnum.SSL_CIPHER_SPEC);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSslClientAuth()
     */
    @Override
    public Integer getSslClientAuth() {
        return getAttributeAsInteger(MqChannelAttributeEnum.SSL_CLIENT_AUTH);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getSslPeerName()
     */
    @Override
    public String getSslPeerName() {
        return getAttributeAsString(MqChannelAttributeEnum.SSL_PEER_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getTransportType()
     */
    @Override
    public Integer getTransportType() {
        return getAttributeAsInteger(MqChannelAttributeEnum.XMIT_PROTOCOL_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getUserIdentifier()
     */
    @Override
    public String getUserIdentifier() {
        return getAttributeAsString(MqChannelAttributeEnum.USER_ID);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getXmitQueueName()
     */
    @Override
    public String getXmitQueueName() {
        return getAttributeAsString(MqChannelAttributeEnum.XMIT_Q_NAME_4CHANNEL);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelStatus()
     */
    @Override
    public Integer getChannelStatus() {
        return getAttributeAsInteger(MqChannelAttributeEnum.CHANNEL_STATUS);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelInstanceType()
     */
    @Override
    public Integer getChannelInstanceType() {
        return getAttributeAsInteger(MqChannelAttributeEnum.CHANNEL_INSTANCE_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelStartDate()
     */
    @Override
    public String getChannelStartDate() {
        return getAttributeAsString(MqChannelAttributeEnum.CHANNEL_START_DATE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getChannelStartTime()
     */
    @Override
    public String getChannelStartTime() {
        return getAttributeAsString(MqChannelAttributeEnum.CHANNEL_START_TIME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqChannel#getConnectionCount()
     */
    @Override
    public Integer getConnectionCount() {
        return connectionCount;
    }
    
    /**
     * @param connectionCount
     */
    public void setConnectionCount(Integer connectionCount) {
        this.connectionCount = connectionCount;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (getQmanagerName() + StringConstants.PIPE + getChannelName() + StringConstants.PIPE + getChannelType());
    }
}
