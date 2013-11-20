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

public interface MbMessageFlowStatistics extends Serializable{

    public String getAccountingOrigin();

    public void setAccountingOrigin(String accountingOrigin);

    public String getBrokerLabel();

    public void setBrokerLabel(String brokerLabel);

    public String getBrokerUUID();

    public void setBrokerUUID(String brokerUUID);

    public Long getCpuTimeWaitingForInputMessage();

    public void setCpuTimeWaitingForInputMessage(String cpuTimeWaitingForInputMessage);

    public Long getElapsedTimeWaitingForInputMessage();

    public void setElapsedTimeWaitingForInputMessage(String elapsedTimeWaitingForInputMessage);

    public String getEndDate();

    public void setEndDate(String endDate);

    public String getEndTime();

    public void setEndTime(String endTime);

    public String getExecutionGroupName();

    public void setExecutionGroupName(String executionGroupName);

    public String getExecutionGroupUUID();

    public void setExecutionGroupUUID(String executionGroupUUID);

    public Long getMaximumCPUTime();

    public void setMaximumCPUTime(String maximumCPUTime);

    public Long getMaximumElapsedTime();

    public void setMaximumElapsedTime(String maximumElapsedTime);

    public Long getMaximumSizeOfInputMessages();

    public void setMaximumSizeOfInputMessages(String maximumSizeOfInputMessages);

    public String getMessageFlowName();

    public void setMessageFlowName(String messageFlowName);

    public Long getMinimumCPUTime();

    public void setMinimumCPUTime(String minimumCPUTime);

    public Long getMinimumElapsedTime();

    public void setMinimumElapsedTime(String minimumElapsedTime);

    public Long getMinimumSizeOfInputMessages();

    public void setMinimumSizeOfInputMessages(String minimumSizeOfInputMessages);

    public Long getNumberOfThreadsInPool();

    public void setNumberOfThreadsInPool(String numberOfThreadsInPool);

    public String getStartDate();

    public void setStartDate(String startDate);

    public String getStartTime();

    public void setStartTime(String startTime);

    public Long getTimesMaximumNumberofThreadsReached();

    public void setTimesMaximumNumberofThreadsReached(String timesMaximumNumberofThreadsReached);

    public Long getTotalCPUTime();

    public void setTotalCPUTime(String totalCPUTime);

    public Long getTotalElapsedTime();

    public void setTotalElapsedTime(String totalElapsedTime);

    public Long getTotalInputMessages();

    public void setTotalInputMessages(String totalInputMessages);

    public Long getTotalNumberOfBackouts();

    public void setTotalNumberOfBackouts(String totalNumberOfBackouts);

    public Long getTotalNumberOfCommits();

    public void setTotalNumberOfCommits(String totalNumberOfCommits);

    public Long getTotalNumberOfErrorsProcessingMessages();

    public void setTotalNumberOfErrorsProcessingMessages(String totalNumberOfErrorsProcessingMessages);

    public Long getTotalNumberOfMessagesWithErrors();

    public void setTotalNumberOfMessagesWithErrors(String totalNumberOfMessagesWithErrors);

    public Long getTotalNumberOfMQErrors();

    public void setTotalNumberOfMQErrors(String totalNumberOfMQErrors);

    public Long getTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages();

    public void setTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages(
            String totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages);

    public Long getTotalSizeOfInputMessages();

    public void setTotalSizeOfInputMessages(String totalSizeOfInputMessages);

}