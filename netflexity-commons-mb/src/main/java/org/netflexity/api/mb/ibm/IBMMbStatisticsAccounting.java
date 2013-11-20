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

import org.netflexity.api.mb.MbMessageFlowStatistics;
import org.netflexity.api.mb.MbNodeStatistics;
import org.netflexity.api.mb.MbStatisticsAccounting;
import org.netflexity.api.mb.MbThreadStatistics;

/**
 * @author FEDORMAX
 *
 * IBMMbStatisticsAccounting
 */
public class IBMMbStatisticsAccounting implements MbStatisticsAccounting {
    private String recordType;
    private String recordCode;
    
    private List flows = new ArrayList();
    private List nodes = new ArrayList();
    private List threads = new ArrayList();

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#getNodes()
     */
    public List getNodes() {
        return nodes;
    }
    /**
     * @param node
     */
    public void addNode(MbNodeStatistics node) {
        this.nodes.add(node);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#getThreads()
     */
    public List getThreads() {
        return threads;
    }
    /**
     * @param thread
     */
    public void addThread(MbThreadStatistics thread) {
        this.threads.add(thread);
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#getFlows()
     */
    public List getFlows() {
        return flows;
    }
    /**
     * @param flow
     */
    public void addFlow(MbMessageFlowStatistics flow) {
        this.flows.add(flow);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#getRecordCode()
     */
    public String getRecordCode() {
        return recordCode;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#setRecordCode(java.lang.String)
     */
    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#getRecordType()
     */
    public String getRecordType() {
        return recordType;
    }
    /* (non-Javadoc)
     * @see org.netflexity.api.mb.ibm.parsers.MbStatisticsAccounting#setRecordType(java.lang.String)
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
