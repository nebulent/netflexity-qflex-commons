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
package org.netflexity.api.mb.ibm;

import org.netflexity.api.mb.MbMessageFlowStatistics;
import org.netflexity.api.mb.ibm.parsers.FlowStatisticsAccountingDigester;

/**
 * @author FEDORMAX
 *
 * Bean, used to collect Execution Group's Flow information from Apache Digester.
 */
public class IBMMbMessageFlowStatistics implements MbMessageFlowStatistics {

    final Long ZERO_LONG = new Long(0);
    
    private String brokerLabel;
    private String brokerUUID;
    private String executionGroupName;
    private String executionGroupUUID;
    private String messageFlowName;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String accountingOrigin;
    
    private Long totalElapsedTime = ZERO_LONG;
    private Long maximumElapsedTime = ZERO_LONG;
    private Long minimumElapsedTime = ZERO_LONG;
    private Long totalCPUTime = ZERO_LONG;
    private Long maximumCPUTime = ZERO_LONG;
    private Long minimumCPUTime = ZERO_LONG;
    private Long cpuTimeWaitingForInputMessage = ZERO_LONG;
    private Long elapsedTimeWaitingForInputMessage = ZERO_LONG;
    private Long totalInputMessages = ZERO_LONG;
    private Long totalSizeOfInputMessages = ZERO_LONG;
    private Long maximumSizeOfInputMessages = ZERO_LONG;
    private Long minimumSizeOfInputMessages = ZERO_LONG;
    private Long numberOfThreadsInPool = ZERO_LONG;
    private Long timesMaximumNumberofThreadsReached = ZERO_LONG;
    private Long totalNumberOfMQErrors = ZERO_LONG;
    private Long totalNumberOfMessagesWithErrors = ZERO_LONG;
    private Long totalNumberOfErrorsProcessingMessages = ZERO_LONG;
    private Long totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages = ZERO_LONG;
    private Long totalNumberOfCommits = ZERO_LONG;
    private Long totalNumberOfBackouts = ZERO_LONG;

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getAccountingOrigin()
     */
    public String getAccountingOrigin() {
        return accountingOrigin;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setAccountingOrigin(java.lang.String)
     */
    public void setAccountingOrigin(String accountingOrigin) {
        this.accountingOrigin = accountingOrigin;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getBrokerLabel()
     */
    public String getBrokerLabel() {
        return brokerLabel;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setBrokerLabel(java.lang.String)
     */
    public void setBrokerLabel(String brokerLabel) {
        this.brokerLabel = brokerLabel;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getBrokerUUID()
     */
    public String getBrokerUUID() {
        return brokerUUID;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setBrokerUUID(java.lang.String)
     */
    public void setBrokerUUID(String brokerUUID) {
        this.brokerUUID = brokerUUID;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getCpuTimeWaitingForInputMessage()
     */
    public Long getCpuTimeWaitingForInputMessage() {
        return cpuTimeWaitingForInputMessage;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setCpuTimeWaitingForInputMessage(java.lang.String)
     */
    public void setCpuTimeWaitingForInputMessage(String cpuTimeWaitingForInputMessage) {
        this.cpuTimeWaitingForInputMessage = FlowStatisticsAccountingDigester.toLong(cpuTimeWaitingForInputMessage);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getElapsedTimeWaitingForInputMessage()
     */
    public Long getElapsedTimeWaitingForInputMessage() {
        return elapsedTimeWaitingForInputMessage;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setElapsedTimeWaitingForInputMessage(java.lang.String)
     */
    public void setElapsedTimeWaitingForInputMessage(String elapsedTimeWaitingForInputMessage) {
        this.elapsedTimeWaitingForInputMessage = FlowStatisticsAccountingDigester.toLong(elapsedTimeWaitingForInputMessage);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getEndDate()
     */
    public String getEndDate() {
        return endDate;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setEndDate(java.lang.String)
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getEndTime()
     */
    public String getEndTime() {
        return endTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setEndTime(java.lang.String)
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getExecutionGroupName()
     */
    public String getExecutionGroupName() {
        return executionGroupName;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setExecutionGroupName(java.lang.String)
     */
    public void setExecutionGroupName(String executionGroupName) {
        this.executionGroupName = executionGroupName;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getExecutionGroupUUID()
     */
    public String getExecutionGroupUUID() {
        return executionGroupUUID;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setExecutionGroupUUID(java.lang.String)
     */
    public void setExecutionGroupUUID(String executionGroupUUID) {
        this.executionGroupUUID = executionGroupUUID;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMaximumCPUTime()
     */
    public Long getMaximumCPUTime() {
        return maximumCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMaximumCPUTime(java.lang.String)
     */
    public void setMaximumCPUTime(String maximumCPUTime) {
        this.maximumCPUTime = FlowStatisticsAccountingDigester.toLong(maximumCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMaximumElapsedTime()
     */
    public Long getMaximumElapsedTime() {
        return maximumElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMaximumElapsedTime(java.lang.String)
     */
    public void setMaximumElapsedTime(String maximumElapsedTime) {
        this.maximumElapsedTime = FlowStatisticsAccountingDigester.toLong(maximumElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMaximumSizeOfInputMessages()
     */
    public Long getMaximumSizeOfInputMessages() {
        return maximumSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMaximumSizeOfInputMessages(java.lang.String)
     */
    public void setMaximumSizeOfInputMessages(String maximumSizeOfInputMessages) {
        this.maximumSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(maximumSizeOfInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMessageFlowName()
     */
    public String getMessageFlowName() {
        return messageFlowName;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMessageFlowName(java.lang.String)
     */
    public void setMessageFlowName(String messageFlowName) {
        this.messageFlowName = messageFlowName;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMinimumCPUTime()
     */
    public Long getMinimumCPUTime() {
        return minimumCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMinimumCPUTime(java.lang.String)
     */
    public void setMinimumCPUTime(String minimumCPUTime) {
        this.minimumCPUTime = FlowStatisticsAccountingDigester.toLong(minimumCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMinimumElapsedTime()
     */
    public Long getMinimumElapsedTime() {
        return minimumElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMinimumElapsedTime(java.lang.String)
     */
    public void setMinimumElapsedTime(String minimumElapsedTime) {
        this.minimumElapsedTime = FlowStatisticsAccountingDigester.toLong(minimumElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getMinimumSizeOfInputMessages()
     */
    public Long getMinimumSizeOfInputMessages() {
        return minimumSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setMinimumSizeOfInputMessages(java.lang.String)
     */
    public void setMinimumSizeOfInputMessages(String minimumSizeOfInputMessages) {
        this.minimumSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(minimumSizeOfInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getNumberOfThreadsInPool()
     */
    public Long getNumberOfThreadsInPool() {
        return numberOfThreadsInPool;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setNumberOfThreadsInPool(java.lang.String)
     */
    public void setNumberOfThreadsInPool(String numberOfThreadsInPool) {
        this.numberOfThreadsInPool = FlowStatisticsAccountingDigester.toLong(numberOfThreadsInPool);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getStartDate()
     */
    public String getStartDate() {
        return startDate;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setStartDate(java.lang.String)
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getStartTime()
     */
    public String getStartTime() {
        return startTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setStartTime(java.lang.String)
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTimesMaximumNumberofThreadsReached()
     */
    public Long getTimesMaximumNumberofThreadsReached() {
        return timesMaximumNumberofThreadsReached;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTimesMaximumNumberofThreadsReached(java.lang.String)
     */
    public void setTimesMaximumNumberofThreadsReached(String timesMaximumNumberofThreadsReached) {
        this.timesMaximumNumberofThreadsReached = FlowStatisticsAccountingDigester.toLong(timesMaximumNumberofThreadsReached);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalCPUTime()
     */
    public Long getTotalCPUTime() {
        return totalCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalCPUTime(java.lang.String)
     */
    public void setTotalCPUTime(String totalCPUTime) {
        this.totalCPUTime = FlowStatisticsAccountingDigester.toLong(totalCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalElapsedTime()
     */
    public Long getTotalElapsedTime() {
        return totalElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalElapsedTime(java.lang.String)
     */
    public void setTotalElapsedTime(String totalElapsedTime) {
        this.totalElapsedTime = FlowStatisticsAccountingDigester.toLong(totalElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalInputMessages()
     */
    public Long getTotalInputMessages() {
        return totalInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalInputMessages(java.lang.String)
     */
    public void setTotalInputMessages(String totalInputMessages) {
        this.totalInputMessages = FlowStatisticsAccountingDigester.toLong(totalInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfBackouts()
     */
    public Long getTotalNumberOfBackouts() {
        return totalNumberOfBackouts;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfBackouts(java.lang.String)
     */
    public void setTotalNumberOfBackouts(String totalNumberOfBackouts) {
        this.totalNumberOfBackouts = FlowStatisticsAccountingDigester.toLong(totalNumberOfBackouts);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfCommits()
     */
    public Long getTotalNumberOfCommits() {
        return totalNumberOfCommits;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfCommits(java.lang.String)
     */
    public void setTotalNumberOfCommits(String totalNumberOfCommits) {
        this.totalNumberOfCommits = FlowStatisticsAccountingDigester.toLong(totalNumberOfCommits);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfErrorsProcessingMessages()
     */
    public Long getTotalNumberOfErrorsProcessingMessages() {
        return totalNumberOfErrorsProcessingMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfErrorsProcessingMessages(java.lang.String)
     */
    public void setTotalNumberOfErrorsProcessingMessages(String totalNumberOfErrorsProcessingMessages) {
        this.totalNumberOfErrorsProcessingMessages = FlowStatisticsAccountingDigester.toLong(totalNumberOfErrorsProcessingMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfMessagesWithErrors()
     */
    public Long getTotalNumberOfMessagesWithErrors() {
        return totalNumberOfMessagesWithErrors;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfMessagesWithErrors(java.lang.String)
     */
    public void setTotalNumberOfMessagesWithErrors(String totalNumberOfMessagesWithErrors) {
        this.totalNumberOfMessagesWithErrors = FlowStatisticsAccountingDigester.toLong(totalNumberOfMessagesWithErrors);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfMQErrors()
     */
    public Long getTotalNumberOfMQErrors() {
        return totalNumberOfMQErrors;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfMQErrors(java.lang.String)
     */
    public void setTotalNumberOfMQErrors(String totalNumberOfMQErrors) {
        this.totalNumberOfMQErrors = FlowStatisticsAccountingDigester.toLong(totalNumberOfMQErrors);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages()
     */
    public Long getTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages() {
        return totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages(java.lang.String)
     */
    public void setTotalNumberOfTimeOutsWaitingForRepliesToAggregateMessages(
            String totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages) {
        this.totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages = FlowStatisticsAccountingDigester.toLong(totalNumberOfTimeOutsWaitingForRepliesToAggregateMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#getTotalSizeOfInputMessages()
     */
    public Long getTotalSizeOfInputMessages() {
        return totalSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbMessageFlowStatistics#setTotalSizeOfInputMessages(java.lang.String)
     */
    public void setTotalSizeOfInputMessages(String totalSizeOfInputMessages) {
        this.totalSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(totalSizeOfInputMessages);
    }
}
