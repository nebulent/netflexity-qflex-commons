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

import org.netflexity.api.mq.MqQueue;
import org.netflexity.api.mq.ibm.enums.MqQueueAttributeEnum;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class IBMMqQueue extends AbstractIBMMqObject implements MqQueue{
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getAlterationDate()
     */
    @Override
    public String getAlterationDate() {
        return getAttributeAsString(MqQueueAttributeEnum.ALTERATION_DATE);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getAlterationTime()
     */
    @Override
    public String getAlterationTime() {
        return getAttributeAsString(MqQueueAttributeEnum.ALTERATION_TIME);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getBackoutRequeueName()
     */
    @Override
    public String getBackoutRequeueName() {
        return getAttributeAsString(MqQueueAttributeEnum.BACKOUT_REQ_Q_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getBackoutThreshold()
     */
    @Override
    public Integer getBackoutThreshold() {
        return getAttributeAsInteger(MqQueueAttributeEnum.BACKOUT_THRESHOLD);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getBaseQueueName()
     */
    @Override
    public String getBaseQueueName() {
        return getAttributeAsString(MqQueueAttributeEnum.BASE_Q_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getClusterName()
     */
    @Override
    public String getClusterName() {
        return getAttributeAsString(MqQueueAttributeEnum.CLUSTER_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getClusterNamelist()
     */
    @Override
    public String getClusterNamelist() {
        return getAttributeAsString(MqQueueAttributeEnum.CLUSTER_NAMELIST);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDefBind()
     */
    @Override
    public String getDefBind() {
        return getAttributeAsString(MqQueueAttributeEnum.DEF_BIND);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDefInputOpenOption()
     */
    @Override
    public Integer getDefInputOpenOption() {
        return getAttributeAsInteger(MqQueueAttributeEnum.DEF_INPUT_OPEN_OPTION);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDefPersistence()
     */
    @Override
    public Integer getDefPersistence() {
        return getAttributeAsInteger(MqQueueAttributeEnum.DEF_PERSISTENCE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDefPriority()
     */
    @Override
    public Integer getDefPriority() {
        return getAttributeAsInteger(MqQueueAttributeEnum.DEF_PRIORITY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDefType()
     */
    @Override
    public Integer getDefType() {
        return getAttributeAsInteger(MqQueueAttributeEnum.DEFINITION_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getDistLists()
     */
    @Override
    public Integer getDistLists() {
        return getAttributeAsInteger(MqQueueAttributeEnum.DIST_LISTS);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getHardenGetBackout()
     */
    @Override
    public Integer getHardenGetBackout() {
        return getAttributeAsInteger(MqQueueAttributeEnum.HARDEN_GET_BACKOUT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getInhibitGet()
     */
    @Override
    public Integer getInhibitGet() {
        return getAttributeAsInteger(MqQueueAttributeEnum.INHIBIT_GET);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getInhibitPut()
     */
    @Override
    public Integer getInhibitPut() {
        return getAttributeAsInteger(MqQueueAttributeEnum.INHIBIT_PUT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getInitiationQueueName()
     */
    @Override
    public String getInitiationQueueName() {
        return getAttributeAsString(MqQueueAttributeEnum.INITIATION_Q_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getMaxMsgLength()
     */
    @Override
    public Integer getMaxMsgLength() {
        return getAttributeAsInteger(MqQueueAttributeEnum.MAX_MSG_LENGTH);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getMaxQueueDepth()
     */
    @Override
    public Integer getMaxQueueDepth() {
        return getAttributeAsInteger(MqQueueAttributeEnum.MAX_Q_DEPTH);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getMsgDeliverySequence()
     */
    @Override
    public Integer getMsgDeliverySequence() {
        return getAttributeAsInteger(MqQueueAttributeEnum.MSG_DELIVERY_SEQUENCE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getProcessName()
     */
    @Override
    public String getProcessName() {
        return getAttributeAsString(MqQueueAttributeEnum.PROCESS_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDepthHighEvent()
     */
    @Override
    public Integer getQueueDepthHighEvent() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_DEPTH_HIGH_EVENT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDepthHighLimit()
     */
    @Override
    public Integer getQueueDepthHighLimit() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_DEPTH_HIGH_LIMIT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDepthLowEvent()
     */
    @Override
    public Integer getQueueDepthLowEvent() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_DEPTH_LOW_EVENT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDepthLowLimit()
     */
    @Override
    public Integer getQueueDepthLowLimit() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_DEPTH_LOW_LIMIT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDepthMaxEvent()
     */
    @Override
    public Integer getQueueDepthMaxEvent() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_DEPTH_MAX_EVENT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueDesc()
     */
    @Override
    public String getQueueDesc() {
        return getAttributeAsString(MqQueueAttributeEnum.Q_DESC);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueName()
     */
    @Override
    public String getQueueName() {
        return getAttributeAsString(MqQueueAttributeEnum.Q_NAME);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueServiceInterval()
     */
    @Override
    public Integer getQueueServiceInterval() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_SERVICE_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueServiceIntervalEvent()
     */
    @Override
    public Integer getQueueServiceIntervalEvent() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_SERVICE_INTERVAL_EVENT);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getQueueType()
     */
    @Override
    public Integer getQueueType() {
        return getAttributeAsInteger(MqQueueAttributeEnum.Q_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getRetentionInterval()
     */
    @Override
    public Integer getRetentionInterval() {
        return getAttributeAsInteger(MqQueueAttributeEnum.RETENTION_INTERVAL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getScope()
     */
    @Override
    public Integer getScope() {
        return getAttributeAsInteger(MqQueueAttributeEnum.SCOPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getShareability()
     */
    @Override
    public Integer getShareability() {
        return getAttributeAsInteger(MqQueueAttributeEnum.SHAREABILITY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getTriggerControl()
     */
    @Override
    public Integer getTriggerControl() {
        return getAttributeAsInteger(MqQueueAttributeEnum.TRIGGER_CONTROL);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getTriggerData()
     */
    @Override
    public String getTriggerData() {
        return getAttributeAsString(MqQueueAttributeEnum.TRIGGER_DATA);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getTriggerDepth()
     */
    @Override
    public Integer getTriggerDepth() {
        return getAttributeAsInteger(MqQueueAttributeEnum.TRIGGER_DEPTH);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getTriggerMsgPriority()
     */
    @Override
    public Integer getTriggerMsgPriority() {
        return getAttributeAsInteger(MqQueueAttributeEnum.TRIGGER_MSG_PRIORITY);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getTriggerType()
     */
    @Override
    public Integer getTriggerType() {
        return getAttributeAsInteger(MqQueueAttributeEnum.TRIGGER_TYPE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getUsage()
     */
    @Override
    public Integer getUsage() {
        return getAttributeAsInteger(MqQueueAttributeEnum.USAGE);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getCurrentQueueDepth()
     */
    @Override
    public Integer getCurrentQueueDepth() {
        return getAttributeAsInteger(MqQueueAttributeEnum.CURRENT_Q_DEPTH);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getOpenInputCount()
     */
    @Override
    public Integer getOpenInputCount() {
        return getAttributeAsInteger(MqQueueAttributeEnum.OPEN_INPUT_COUNT);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqQueue#getOpenOutputCount()
     */
    @Override
    public Integer getOpenOutputCount() {
        return getAttributeAsInteger(MqQueueAttributeEnum.OPEN_OUTPUT_COUNT);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (getQueueName() + StringConstants.PIPE + getQueueType());
    }
}
