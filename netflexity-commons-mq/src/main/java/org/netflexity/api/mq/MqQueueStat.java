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

public interface MqQueueStat extends Serializable{

    /**
     * @return Returns the highDepth.
     */
    public Integer getHighDepth();

    /**
     * @param highDepth The highDepth to set.
     */
    public void setHighDepth(Integer highDepth);

    /**
     * @return Returns the msgDeqCount.
     */
    public Integer getMsgDeqCount();

    /**
     * @param msgDeqCount The msgDeqCount to set.
     */
    public void setMsgDeqCount(Integer msgDeqCount);

    /**
     * @return Returns the msgEnqCount.
     */
    public Integer getMsgEnqCount();

    /**
     * @param msgEnqCount The msgEnqCount to set.
     */
    public void setMsgEnqCount(Integer msgEnqCount);

    /**
     * @return Returns the queueName.
     */
    public String getQueueName();

    /**
     * @param queueName The queueName to set.
     */
    public void setQueueName(String queueName);

    /**
     * @return Returns the timeSinceReset.
     */
    public Long getTimeSinceReset();

    /**
     * @param timeSinceReset The timeSinceReset to set.
     */
    public void setTimeSinceReset(Long timeSinceReset);

}