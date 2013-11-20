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
package org.netflexity.api.mb.ibm.util;

import org.netflexity.api.mb.MbArtifactBuilder;
import org.netflexity.api.mb.MbExecGroup;
import org.netflexity.api.mb.MbMessageFlow;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.util.StringConstants;


/**
 * @author MAX
 *
 */
public final class IBMMbMessageFlowArtifactBuilder implements MbArtifactBuilder {

    IBMMbMessageFlowArtifactBuilder(){}
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getTopicName(java.lang.Object)
     */
    public String getTopicName(Object o){
        if (o instanceof MbMessageFlow) {
            return getTopicName((MbMessageFlow)o);
        }
        return null;
    }
    
    /**
     * @param flow
     * @return
     */
    public String getTopicName(MbMessageFlow flow){
        MbExecGroup execGroup = flow.getExecGroup();
        return $SYS_BROKER + execGroup.getBroker().getBrokerName() + STATISTICS_ACCOUNTING_SNAPSHOT + execGroup.getExecGroupName() + StringConstants.FORWARD_SLASH + flow.getFlowName();
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getActivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getActivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbMessageFlow) {
            return getActivateFlowStatsMessage((MbMessageFlow)subscriber, nodeLevel);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getInactivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getInactivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbMessageFlow) {
            return getInactivateFlowStatsMessage((MbMessageFlow)subscriber, nodeLevel);
        }
        return null;
    }

    /**
     * @param flow
     * @param nodeLevel
     * @return
     */
    public String getActivateFlowStatsMessage(MbMessageFlow flow, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(flow, true, nodeLevel).toXml();
    }
    
    /**
     * @param flow
     * @param nodeLevel
     * @return
     */
    public String getInactivateFlowStatsMessage(MbMessageFlow flow, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(flow, false, nodeLevel).toXml();
    }
}
