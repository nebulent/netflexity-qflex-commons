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
package org.netflexity.api.mb;

import java.io.Serializable;

public interface MbThreadStatistics extends Serializable{

    // Thread's members.
    public Long getCpuTimeWaitingForInputMessage();

    public void setCpuTimeWaitingForInputMessage(String cpuTimeWaitingForInputMessage);

    public Long getElapsedTimeWaitingForInputMessage();

    public void setElapsedTimeWaitingForInputMessage(String elapsedTimeWaitingForInputMessage);

    public Long getMaximumSizeOfInputMessages();

    public void setMaximumSizeOfInputMessages(String maximumSizeOfInputMessages);

    public Long getMinimumSizeOfInputMessages();

    public void setMinimumSizeOfInputMessages(String minimumSizeOfInputMessages);

    public Long getThreadNumber();

    public void setThreadNumber(String threadNumber);

    public Long getTotalCPUTime();

    public void setTotalCPUTime(String totalCPUTime);

    public Long getTotalElapsedTime();

    public void setTotalElapsedTime(String totalElapsedTime);

    public Long getTotalNumberOfInputMessages();

    public void setTotalNumberOfInputMessages(String totalNumberOfInputMessages);

    public Long getTotalSizeOfInputMessages();

    public void setTotalSizeOfInputMessages(String totalSizeOfInputMessages);

}