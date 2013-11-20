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

import org.netflexity.api.mq.MqQueueStat;
import org.netflexity.api.mq.ibm.enums.MqQueueStatAttributeEnum;
import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class IBMMqQueueStat extends AbstractIBMMqObject implements MqQueueStat {
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#getHighDepth()
     */
    @Override
    public Integer getHighDepth() {
        return getAttributeAsInteger(MqQueueStatAttributeEnum.HIGH_Q_DEPTH);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#setHighDepth(java.lang.Integer)
     */
    @Override
    public void setHighDepth(Integer highDepth) {
        addAttribute(MqQueueStatAttributeEnum.HIGH_Q_DEPTH, highDepth);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#getMsgDeqCount()
     */
    @Override
    public Integer getMsgDeqCount() {
        return getAttributeAsInteger(MqQueueStatAttributeEnum.MSG_DEQ_COUNT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#setMsgDeqCount(java.lang.Integer)
     */
    @Override
    public void setMsgDeqCount(Integer msgDeqCount) {
        addAttribute(MqQueueStatAttributeEnum.MSG_DEQ_COUNT, msgDeqCount);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#getMsgEnqCount()
     */
    @Override
    public Integer getMsgEnqCount() {
        return getAttributeAsInteger(MqQueueStatAttributeEnum.MSG_ENQ_COUNT);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#setMsgEnqCount(java.lang.Integer)
     */
    @Override
    public void setMsgEnqCount(Integer msgEnqCount) {
        addAttribute(MqQueueStatAttributeEnum.MSG_ENQ_COUNT, msgEnqCount);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#getQueueName()
     */
    @Override
    public String getQueueName() {
        return getAttributeAsString(MqQueueStatAttributeEnum.Q_NAME);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#setQueueName(java.lang.String)
     */
    @Override
    public void setQueueName(String queueName) {
        addAttribute(MqQueueStatAttributeEnum.Q_NAME, queueName);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#getTimeSinceReset()
     */
    @Override
    public Long getTimeSinceReset() {
        return getAttributeAsLong(MqQueueStatAttributeEnum.TIME_SINCE_RESET);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.impl.MqQueueStat#setTimeSinceReset(java.lang.Long)
     */
    @Override
    public void setTimeSinceReset(Long timeSinceReset) {
        addAttribute(MqQueueStatAttributeEnum.TIME_SINCE_RESET, timeSinceReset);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (getQueueName() + StringConstants.PIPE + getTimeSinceReset());
    }
}
