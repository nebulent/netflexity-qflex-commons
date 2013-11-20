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
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.util.StringConstants;


/**
 * @author MAX
 *
 */
public final class IBMMbExecGroupArtifactBuilder implements MbArtifactBuilder {
    
    IBMMbExecGroupArtifactBuilder(){}
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getTopicName(java.lang.Object)
     */
    public String getTopicName(Object o){
        if (o instanceof MbExecGroup) {
            return getTopicName((MbExecGroup)o);
        }
        return null;
    }
    
    /**
     * @param execGroup
     * @return
     */
    public String getTopicName(MbExecGroup execGroup){
        MbMessageBroker broker = execGroup.getBroker();
        return $SYS_BROKER + broker.getBrokerName() + STATISTICS_ACCOUNTING_SNAPSHOT + execGroup.getExecGroupName() + StringConstants.FORWARD_SLASH + ALL_CHILD_NODES;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getActivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getActivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbExecGroup) {
            return getActivateFlowStatsMessage((MbExecGroup)subscriber, nodeLevel);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getInactivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getInactivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbExecGroup) {
            return getInactivateFlowStatsMessage((MbExecGroup)subscriber, nodeLevel);
        }
        return null;
    }

    /**
     * @param execGroup
     * @param nodeLevel
     * @return
     */
    public String getActivateFlowStatsMessage(MbExecGroup execGroup, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(execGroup, true, nodeLevel).toXml();
    }
    
    /**
     * @param execGroup
     * @param nodeLevel
     * @return
     */
    public String getInactivateFlowStatsMessage(MbExecGroup execGroup, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(execGroup, false, nodeLevel).toXml();
    }
}
