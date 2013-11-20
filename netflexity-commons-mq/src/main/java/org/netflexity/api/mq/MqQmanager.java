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
package org.netflexity.api.mq;

import java.io.Serializable;

public interface MqQmanager extends Serializable{

    /**
     * @return Returns the alterationDate.
     */
    public String getAlterationDate();

    /**
     * @return Returns the alterationTime.
     */
    public String getAlterationTime();

    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate();

    /**
     * @return Returns the creationTime.
     */
    public String getCreationTime();

    /**
     * @return Returns the authorityEvent.
     */
    public Integer getAuthorityEvent();

    /**
     * @return Returns the channelAutoDef.
     */
    public Integer getChannelAutoDef();

    /**
     * @return Returns the channelAutoDefEvent.
     */
    public Integer getChannelAutoDefEvent();

    /**
     * @return Returns the channelAutoDefExit.
     */
    public String getChannelAutoDefExit();

    /**
     * @return Returns the clusterWorkloadData.
     */
    public String getClusterWorkloadData();

    /**
     * @return Returns the clusterWorkloadExit.
     */
    public String getClusterWorkloadExit();

    /**
     * @return Returns the clusterWorkloadLength.
     */
    public Integer getClusterWorkloadLength();

    /**
     * @return Returns the codedCharSetId.
     */
    public Integer getCodedCharSetId();

    /**
     * @return Returns the commandInputQName.
     */
    public String getCommandInputQName();

    /**
     * @return Returns the commandLevel.
     */
    public Integer getCommandLevel();

    /**
     * @return Returns the deadLetterQName.
     */
    public String getDeadLetterQName();

    /**
     * @return Returns the defXmitQName.
     */
    public String getDefXmitQName();

    /**
     * @return Returns the distLists.
     */
    public Integer getDistLists();

    /**
     * @return Returns the inhibitEvent.
     */
    public Integer getInhibitEvent();

    /**
     * @return Returns the localEvent.
     */
    public Integer getLocalEvent();

    /**
     * @return Returns the maxHandles.
     */
    public Integer getMaxHandles();

    /**
     * @return Returns the maxMsgLength.
     */
    public Integer getMaxMsgLength();

    /**
     * @return Returns the maxPriority.
     */
    public Integer getMaxPriority();

    /**
     * @return Returns the maxUncommittedMsgs.
     */
    public Integer getMaxUncommittedMsgs();

    /**
     * @return Returns the performanceEvent.
     */
    public Integer getPerformanceEvent();

    /**
     * @return Returns the platform.
     */
    public Integer getPlatform();

    /**
     * @return Returns the qmanagerDesc.
     */
    public String getQmanagerDesc();

    /**
     * @return Returns the qmanagerIdentifier.
     */
    public String getQmanagerIdentifier();

    /**
     * @return Returns the qmanagerName.
     */
    public String getQmanagerName();

    /**
     * @return Returns the remoteEvent.
     */
    public Integer getRemoteEvent();

    /**
     * @return Returns the repositoryName.
     */
    public String getRepositoryName();

    /**
     * @return Returns the repositoryNameList.
     */
    public String getRepositoryNameList();

    /**
     * @return Returns the startStopEvent.
     */
    public Integer getStartStopEvent();

    /**
     * @return Returns the syncPoint.
     */
    public Integer getSyncPoint();

    /**
     * @return Returns the triggerInterval.
     */
    public Integer getTriggerInterval();

    /**
     * @return Returns the sslCertificateRevocationList.
     */
    public String getSslCertificateRepositoryList();

    /**
     * @return Returns the sslKeyRing.
     */
    public String getSslKeyRepository();
}