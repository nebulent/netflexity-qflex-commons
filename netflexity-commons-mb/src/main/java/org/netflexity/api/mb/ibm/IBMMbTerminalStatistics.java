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

import org.netflexity.api.mb.MbTerminalStatistics;
import org.netflexity.api.mb.ibm.parsers.FlowStatisticsAccountingDigester;

/**
 * @author FEDORMAX
 *
 * Bean, used to collect Node's Terminal information from Apache Digester.
 */
public class IBMMbTerminalStatistics implements MbTerminalStatistics {
    final Long ZERO_LONG = new Long(0);
    
    private String label;
    private String type;
    private Long countOfInvocations = ZERO_LONG;
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#getCountOfInvocations()
     */
    public Long getCountOfInvocations() {
        return countOfInvocations;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#setCountOfInvocations(java.lang.String)
     */
    public void setCountOfInvocations(String countOfInvocations) {
        this.countOfInvocations = FlowStatisticsAccountingDigester.toLong(countOfInvocations);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#getLabel()
     */
    public String getLabel() {
        return label;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#setLabel(java.lang.String)
     */
    public void setLabel(String label) {
        this.label = label;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#getType()
     */
    public String getType() {
        return type;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbTerminalStatistics#setType(java.lang.String)
     */
    public void setType(String type) {
        this.type = type;
    }
}
