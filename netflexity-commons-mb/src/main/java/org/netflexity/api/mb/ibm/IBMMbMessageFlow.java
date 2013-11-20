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
package org.netflexity.api.mb.ibm;

import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageFlow;

/**
 * @author MAX
 *
 */
public class IBMMbMessageFlow implements MbMessageFlow {

    private String flowName;
    private String flowUuid;
    private MbExecGroup execGroup;
    
    public IBMMbMessageFlow(){
    }
    
    /**
     * @param execGroup
     * @param flowName
     * @param flowUuid
     */
    public IBMMbMessageFlow(MbExecGroup execGroup, String flowName, String flowUuid) {
        this.execGroup = execGroup;
        this.flowName = flowName;
        this.flowUuid = flowUuid;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageFlow#getExecGroup()
     */
    public MbExecGroup getExecGroup() {
        return execGroup;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageFlow#setExecGroup(com.netflexity.wbi.business.MbExecGroup)
     */
    public void setExecGroup(MbExecGroup execGroup) {
        this.execGroup = execGroup;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageFlow#getFlowName()
     */
    public String getFlowName() {
        return flowName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbMessageFlow#setFlowName(java.lang.String)
     */
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageFlow#getFlowUuid()
     */
    public String getFlowUuid() {
        return flowUuid;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbMessageFlow#setFlowUuid(java.lang.String)
     */
    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }
}