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

import org.netflexity.api.mb.MbThreadStatistics;
import org.netflexity.api.mb.ibm.parsers.FlowStatisticsAccountingDigester;

/**
 * @author FEDORMAX
 *
 * Bean, used to collect Flow's Thread information from Apache Digester.
 */
public class IBMMbThreadStatistics implements MbThreadStatistics {
    final Long ZERO_LONG = new Long(0);
    
    private Long threadNumber = ZERO_LONG;
    private Long totalNumberOfInputMessages = ZERO_LONG;
    private Long totalElapsedTime = ZERO_LONG;
    private Long totalCPUTime = ZERO_LONG;
    private Long cpuTimeWaitingForInputMessage = ZERO_LONG;
    private Long elapsedTimeWaitingForInputMessage = ZERO_LONG;
    private Long totalSizeOfInputMessages = ZERO_LONG;
    private Long maximumSizeOfInputMessages = ZERO_LONG;
    private Long minimumSizeOfInputMessages = ZERO_LONG;
    
    // Thread's members.
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getCpuTimeWaitingForInputMessage()
     */
    public Long getCpuTimeWaitingForInputMessage() {
        return cpuTimeWaitingForInputMessage;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setCpuTimeWaitingForInputMessage(java.lang.String)
     */
    public void setCpuTimeWaitingForInputMessage(String cpuTimeWaitingForInputMessage) {
        this.cpuTimeWaitingForInputMessage = FlowStatisticsAccountingDigester.toLong(cpuTimeWaitingForInputMessage);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getElapsedTimeWaitingForInputMessage()
     */
    public Long getElapsedTimeWaitingForInputMessage() {
        return elapsedTimeWaitingForInputMessage;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setElapsedTimeWaitingForInputMessage(java.lang.String)
     */
    public void setElapsedTimeWaitingForInputMessage(String elapsedTimeWaitingForInputMessage) {
        this.elapsedTimeWaitingForInputMessage = FlowStatisticsAccountingDigester.toLong(elapsedTimeWaitingForInputMessage);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getMaximumSizeOfInputMessages()
     */
    public Long getMaximumSizeOfInputMessages() {
        return maximumSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setMaximumSizeOfInputMessages(java.lang.String)
     */
    public void setMaximumSizeOfInputMessages(String maximumSizeOfInputMessages) {
        this.maximumSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(maximumSizeOfInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getMinimumSizeOfInputMessages()
     */
    public Long getMinimumSizeOfInputMessages() {
        return minimumSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setMinimumSizeOfInputMessages(java.lang.String)
     */
    public void setMinimumSizeOfInputMessages(String minimumSizeOfInputMessages) {
        this.minimumSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(minimumSizeOfInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getThreadNumber()
     */
    public Long getThreadNumber() {
        return threadNumber;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setThreadNumber(java.lang.String)
     */
    public void setThreadNumber(String threadNumber) {
        this.threadNumber = FlowStatisticsAccountingDigester.toLong(threadNumber);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getTotalCPUTime()
     */
    public Long getTotalCPUTime() {
        return totalCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setTotalCPUTime(java.lang.String)
     */
    public void setTotalCPUTime(String totalCPUTime) {
        this.totalCPUTime = FlowStatisticsAccountingDigester.toLong(totalCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getTotalElapsedTime()
     */
    public Long getTotalElapsedTime() {
        return totalElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setTotalElapsedTime(java.lang.String)
     */
    public void setTotalElapsedTime(String totalElapsedTime) {
        this.totalElapsedTime = FlowStatisticsAccountingDigester.toLong(totalElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getTotalNumberOfInputMessages()
     */
    public Long getTotalNumberOfInputMessages() {
        return totalNumberOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setTotalNumberOfInputMessages(java.lang.String)
     */
    public void setTotalNumberOfInputMessages(String totalNumberOfInputMessages) {
        this.totalNumberOfInputMessages = FlowStatisticsAccountingDigester.toLong(totalNumberOfInputMessages);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#getTotalSizeOfInputMessages()
     */
    public Long getTotalSizeOfInputMessages() {
        return totalSizeOfInputMessages;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbThreadStatistics#setTotalSizeOfInputMessages(java.lang.String)
     */
    public void setTotalSizeOfInputMessages(String totalSizeOfInputMessages) {
        this.totalSizeOfInputMessages = FlowStatisticsAccountingDigester.toLong(totalSizeOfInputMessages);
    }
}
