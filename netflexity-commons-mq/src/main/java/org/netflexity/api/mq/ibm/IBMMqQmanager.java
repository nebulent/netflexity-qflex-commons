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
package org.netflexity.api.mq.ibm;

import org.netflexity.api.mq.MqQmanager;
import org.netflexity.api.mq.ibm.enums.MqQmanagerAttributeEnum;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class IBMMqQmanager extends AbstractIBMMqObject implements MqQmanager{

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getAlterationDate()
     */
    @Override
    public String getAlterationDate() {
        return getAttributeAsString(MqQmanagerAttributeEnum.ALTERATION_DATE);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getAlterationTime()
     */
    @Override
    public String getAlterationTime() {
        return getAttributeAsString(MqQmanagerAttributeEnum.ALTERATION_TIME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getCreationDate()
     */
    @Override
    public String getCreationDate() {
        return getAttributeAsString(MqQmanagerAttributeEnum.CREATION_DATE);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getCreationTime()
     */
    @Override
    public String getCreationTime() {
        return getAttributeAsString(MqQmanagerAttributeEnum.CREATION_TIME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getAuthorityEvent()
     */
    @Override
    public Integer getAuthorityEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.AUTHORITY_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getChannelAutoDef()
     */
    @Override
    public Integer getChannelAutoDef() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.CHANNEL_AUTO_DEF);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getChannelAutoDefEvent()
     */
    @Override
    public Integer getChannelAutoDefEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.CHANNEL_AUTO_DEF_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getChannelAutoDefExit()
     */
    @Override
    public String getChannelAutoDefExit() {
        return getAttributeAsString(MqQmanagerAttributeEnum.CHANNEL_AUTO_DEF_EXIT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getClusterWorkloadData()
     */
    @Override
    public String getClusterWorkloadData() {
        return getAttributeAsString(MqQmanagerAttributeEnum.CLUSTER_WORKLOAD_DATA);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getClusterWorkloadExit()
     */
    @Override
    public String getClusterWorkloadExit() {
        return getAttributeAsString(MqQmanagerAttributeEnum.CLUSTER_WORKLOAD_EXIT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getClusterWorkloadLength()
     */
    @Override
    public Integer getClusterWorkloadLength() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.CLUSTER_WORKLOAD_LENGTH);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getCodedCharSetId()
     */
    @Override
    public Integer getCodedCharSetId() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.CODED_CHAR_SET_ID);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getCommandInputQName()
     */
    @Override
    public String getCommandInputQName() {
        return getAttributeAsString(MqQmanagerAttributeEnum.COMMAND_INPUT_Q_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getCommandLevel()
     */
    @Override
    public Integer getCommandLevel() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.COMMAND_LEVEL);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getDeadLetterQName()
     */
    @Override
    public String getDeadLetterQName() {
        return getAttributeAsString(MqQmanagerAttributeEnum.DEAD_LETTER_Q_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getDefXmitQName()
     */
    @Override
    public String getDefXmitQName() {
        return getAttributeAsString(MqQmanagerAttributeEnum.DEF_XMIT_Q_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getDistLists()
     */
    @Override
    public Integer getDistLists() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.DIST_LISTS);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getInhibitEvent()
     */
    @Override
    public Integer getInhibitEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.INHIBIT_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getLocalEvent()
     */
    @Override
    public Integer getLocalEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.LOCAL_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getMaxHandles()
     */
    @Override
    public Integer getMaxHandles() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.MAX_HANDLES);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getMaxMsgLength()
     */
    @Override
    public Integer getMaxMsgLength() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.MAX_MSG_LENGTH);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getMaxPriority()
     */
    @Override
    public Integer getMaxPriority() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.MAX_PRIORITY);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getMaxUncommittedMsgs()
     */
    @Override
    public Integer getMaxUncommittedMsgs() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.MAX_UNCOMMITTED_MSGS);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getPerformanceEvent()
     */
    @Override
    public Integer getPerformanceEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.PERFORMANCE_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getPlatform()
     */
    @Override
    public Integer getPlatform() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.PLATFORM);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getQmanagerDesc()
     */
    @Override
    public String getQmanagerDesc() {
        return getAttributeAsString(MqQmanagerAttributeEnum.Q_MGR_DESC);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getQmanagerIdentifier()
     */
    @Override
    public String getQmanagerIdentifier() {
        return getAttributeAsString(MqQmanagerAttributeEnum.Q_MGR_IDENTIFIER);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getQmanagerName()
     */
    @Override
    public String getQmanagerName() {
        return getAttributeAsString(MqQmanagerAttributeEnum.Q_MGR_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getRemoteEvent()
     */
    @Override
    public Integer getRemoteEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.REMOTE_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getRepositoryName()
     */
    @Override
    public String getRepositoryName() {
        return getAttributeAsString(MqQmanagerAttributeEnum.REPOSITORY_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getRepositoryNameList()
     */
    @Override
    public String getRepositoryNameList() {
        return getAttributeAsString(MqQmanagerAttributeEnum.REPOSITORY_NAMELIST);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getStartStopEvent()
     */
    @Override
    public Integer getStartStopEvent() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.START_STOP_EVENT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getSyncPoint()
     */
    @Override
    public Integer getSyncPoint() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.SYNCPOINT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getTriggerInterval()
     */
    @Override
    public Integer getTriggerInterval() {
        return getAttributeAsInteger(MqQmanagerAttributeEnum.TRIGGER_INTERVAL);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getSslCertificateRepositoryList()
     */
    @Override
    public String getSslCertificateRepositoryList() {
        return getAttributeAsString(MqQmanagerAttributeEnum.SSL_CRL_NAMELIST);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQmanager#getSslKeyRepository()
     */
    @Override
    public String getSslKeyRepository() {
        return getAttributeAsString(MqQmanagerAttributeEnum.SSL_KEY_REPOSITORY);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (getQmanagerName() + StringConstants.PIPE + getPlatform());
    }
}
