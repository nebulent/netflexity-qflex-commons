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

import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.MbMessageFlow;

/**
 * @author MAX
 *
 */
public class IBMMbExecGroup implements MbExecGroup {

    private String execGroupName;
    private String execGroupUuid;
    private MbMessageBroker broker;
    
    private List messageFlows;
    
    public IBMMbExecGroup(){
        messageFlows = new ArrayList();
    }
    
    /**
     * @param broker
     * @param execGroupName
     * @param execGroupUuid
     */
    public IBMMbExecGroup(MbMessageBroker broker, String execGroupName, String execGroupUuid) {
        this.broker = broker;
        this.execGroupName = execGroupName;
        this.execGroupUuid = execGroupUuid;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#getBroker()
     */
    public MbMessageBroker getBroker() {
        return broker;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#setBroker(com.netflexity.wbi.business.MbMessageBroker)
     */
    public void setBroker(MbMessageBroker broker) {
        this.broker = broker;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#getExecGroupName()
     */
    public String getExecGroupName() {
        return execGroupName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#setExecGroupName(java.lang.String)
     */
    public void setExecGroupName(String execGroupName) {
        this.execGroupName = execGroupName;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#getExecGroupUuid()
     */
    public String getExecGroupUuid() {
        return execGroupUuid;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbExecGroup#setExecGroupUuid(java.lang.String)
     */
    public void setExecGroupUuid(String execGroupUuid) {
        this.execGroupUuid = execGroupUuid;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mb.MbExecGroup#getMessageFlows()
     */
    public List getMessageFlows() {
        return messageFlows;
    }

    /**
     * @param messageFlows The messageFlows to set.
     */
    public void addMessageFlow(MbMessageFlow messageFlow) {
        this.messageFlows.add(messageFlow);
    }
}