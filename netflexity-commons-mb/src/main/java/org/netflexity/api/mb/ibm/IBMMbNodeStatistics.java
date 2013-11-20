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

import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.mb.MbNodeStatistics;
import org.netflexity.api.mb.MbTerminalStatistics;
import org.netflexity.api.mb.ibm.parsers.FlowStatisticsAccountingDigester;

/**
 * @author FEDORMAX
 *
 * Bean, used to collect Flow's Node information from Apache Digester.
 */
public class IBMMbNodeStatistics implements MbNodeStatistics {
    final Long ZERO_LONG = new Long(0);
    
    private String label;
    private String type;
    
    private Long totalElapsedTime = ZERO_LONG;
    private Long maximumElapsedTime = ZERO_LONG;
    private Long minimumElapsedTime = ZERO_LONG;
    private Long totalCPUTime = ZERO_LONG;
    private Long maximumCPUTime = ZERO_LONG;
    private Long minimumCPUTime = ZERO_LONG;
    private Long countOfInvocations = ZERO_LONG;
    private Long numberOfInputTerminals = ZERO_LONG;
    private Long numberOfOutputTerminals = ZERO_LONG;

    private List terminals = new ArrayList();
    
    // Node's child collections.
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getTerminals()
     */
    public List getTerminals() {
        return terminals;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#addTerminal(org.netflexity.api.mb.ibm.parsers.IBMMbTerminalStatistics)
     */
    public void addTerminal(MbTerminalStatistics term) {
        this.terminals.add(term);
    }
    
    // Node's members.
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getCountOfInvocations()
     */
    public Long getCountOfInvocations() {
        return countOfInvocations;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setCountOfInvocations(java.lang.String)
     */
    public void setCountOfInvocations(String countOfInvocations) {
        this.countOfInvocations = FlowStatisticsAccountingDigester.toLong(countOfInvocations);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getLabel()
     */
    public String getLabel() {
        return label;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setLabel(java.lang.String)
     */
    public void setLabel(String label) {
        this.label = label;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getMaximumCPUTime()
     */
    public Long getMaximumCPUTime() {
        return maximumCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setMaximumCPUTime(java.lang.String)
     */
    public void setMaximumCPUTime(String maximumCPUTime) {
        this.maximumCPUTime = FlowStatisticsAccountingDigester.toLong(maximumCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getMaximumElapsedTime()
     */
    public Long getMaximumElapsedTime() {
        return maximumElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setMaximumElapsedTime(java.lang.String)
     */
    public void setMaximumElapsedTime(String maximumElapsedTime) {
        this.maximumElapsedTime = FlowStatisticsAccountingDigester.toLong(maximumElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getMinimumCPUTime()
     */
    public Long getMinimumCPUTime() {
        return minimumCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setMinimumCPUTime(java.lang.String)
     */
    public void setMinimumCPUTime(String minimumCPUTime) {
        this.minimumCPUTime = FlowStatisticsAccountingDigester.toLong(minimumCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getMinimumElapsedTime()
     */
    public Long getMinimumElapsedTime() {
        return minimumElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setMinimumElapsedTime(java.lang.String)
     */
    public void setMinimumElapsedTime(String minimumElapsedTime) {
        this.minimumElapsedTime = FlowStatisticsAccountingDigester.toLong(minimumElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getNumberOfInputTerminals()
     */
    public Long getNumberOfInputTerminals() {
        return numberOfInputTerminals;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setNumberOfInputTerminals(java.lang.String)
     */
    public void setNumberOfInputTerminals(String numberOfInputTerminals) {
        this.numberOfInputTerminals = FlowStatisticsAccountingDigester.toLong(numberOfInputTerminals);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getNumberOfOutputTerminals()
     */
    public Long getNumberOfOutputTerminals() {
        return numberOfOutputTerminals;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setNumberOfOutputTerminals(java.lang.String)
     */
    public void setNumberOfOutputTerminals(String numberOfOutputTerminals) {
        this.numberOfOutputTerminals = FlowStatisticsAccountingDigester.toLong(numberOfOutputTerminals);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getTotalCPUTime()
     */
    public Long getTotalCPUTime() {
        return totalCPUTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setTotalCPUTime(java.lang.String)
     */
    public void setTotalCPUTime(String totalCPUTime) {
        this.totalCPUTime = FlowStatisticsAccountingDigester.toLong(totalCPUTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getTotalElapsedTime()
     */
    public Long getTotalElapsedTime() {
        return totalElapsedTime;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setTotalElapsedTime(java.lang.String)
     */
    public void setTotalElapsedTime(String totalElapsedTime) {
        this.totalElapsedTime = FlowStatisticsAccountingDigester.toLong(totalElapsedTime);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#getType()
     */
    public String getType() {
        return type;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbNodeStatistics#setType(java.lang.String)
     */
    public void setType(String type) {
        this.type = type;
    }
}
