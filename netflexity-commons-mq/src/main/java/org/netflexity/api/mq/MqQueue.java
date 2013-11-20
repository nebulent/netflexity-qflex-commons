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

public interface MqQueue extends Serializable{

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
    public String getBackoutRequeueName();

    /**
     * @return
     */
    public Integer getBackoutThreshold();

    /**
     * @return
     */
    public String getBaseQueueName();

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
    public String getDefBind();

    /**
     * @return
     */
    public Integer getDefInputOpenOption();

    /**
     * @return
     */
    public Integer getDefPersistence();

    /**
     * @return
     */
    public Integer getDefPriority();

    /**
     * @return
     */
    public Integer getDefType();

    /**
     * @return
     */
    public Integer getDistLists();

    /**
     * @return
     */
    public Integer getHardenGetBackout();

    /**
     * @return
     */
    public Integer getInhibitGet();

    /**
     * @return
     */
    public Integer getInhibitPut();

    /**
     * @return
     */
    public String getInitiationQueueName();

    /**
     * @return
     */
    public Integer getMaxMsgLength();

    /**
     * @return
     */
    public Integer getMaxQueueDepth();

    /**
     * @return
     */
    public Integer getMsgDeliverySequence();

    /**
     * @return
     */
    public String getProcessName();

    /**
     * @return
     */
    public Integer getQueueDepthHighEvent();

    /**
     * @return
     */
    public Integer getQueueDepthHighLimit();

    /**
     * @return
     */
    public Integer getQueueDepthLowEvent();

    /**
     * @return
     */
    public Integer getQueueDepthLowLimit();

    /**
     * @return
     */
    public Integer getQueueDepthMaxEvent();

    /**
     * @return
     */
    public String getQueueDesc();

    /**
     * @return
     */
    public String getQueueName();

    /**
     * @return
     */
    public Integer getQueueServiceInterval();

    /**
     * @return
     */
    public Integer getQueueServiceIntervalEvent();

    /**
     * @return
     */
    public Integer getQueueType();

    /**
     * @return
     */
    public Integer getRetentionInterval();

    /**
     * @return
     */
    public Integer getScope();

    /**
     * @return
     */
    public Integer getShareability();

    /**
     * @return
     */
    public Integer getTriggerControl();

    /**
     * @return
     */
    public String getTriggerData();

    /**
     * @return
     */
    public Integer getTriggerDepth();

    /**
     * @return
     */
    public Integer getTriggerMsgPriority();

    /**
     * @return
     */
    public Integer getTriggerType();

    /**
     * @return
     */
    public Integer getUsage();

    /**
     * @return Returns the currentQueueDepth.
     */
    public Integer getCurrentQueueDepth();
    
    /**
     * @return Returns the openInputCount.
     */
    public Integer getOpenInputCount();
    
    /**
     * @return Returns the openOutputCount.
     */
    public Integer getOpenOutputCount();
}