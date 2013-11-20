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
import org.netflexity.api.mb.MbMessageBroker;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.util.StringConstants;


/**
 * @author MAX
 *
 */
public final class IBMMbMessageBrokerArtifactBuilder implements MbArtifactBuilder {
    
    IBMMbMessageBrokerArtifactBuilder(){}
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getTopicName(java.lang.Object)
     */
    public String getTopicName(Object o){
        if (o instanceof MbMessageBroker) {
            return getTopicName((MbMessageBroker)o);
        }
        return null;
    }
    
    /**
     * @param broker
     * @return
     */
    public String getTopicName(MbMessageBroker broker){
        return $SYS_BROKER + broker.getBrokerName() + STATISTICS_ACCOUNTING_SNAPSHOT + ALL_CHILD_NODES + StringConstants.FORWARD_SLASH + ALL_CHILD_NODES;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getActivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getActivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbMessageBroker) {
            return getActivateFlowStatsMessage((MbMessageBroker)subscriber, nodeLevel);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.util.MbArtifactBuilder#getInactivateFlowStatsMessage(java.lang.Object, com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public String getInactivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel) {
        if (subscriber instanceof MbMessageBroker) {
            return getInactivateFlowStatsMessage((MbMessageBroker)subscriber, nodeLevel);
        }
        return null;
    }

    /**
     * @param broker
     * @param nodeLevel
     * @return
     */
    public String getActivateFlowStatsMessage(MbMessageBroker broker, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(broker, true, nodeLevel).toXml();
    }
    
    /**
     * @param broker
     * @param nodeLevel
     * @return
     */
    public String getInactivateFlowStatsMessage(MbMessageBroker broker, SnapshotNodeDataLevelEnum nodeLevel) {
        return new IBMMbMqsiChangeFlowStats(broker, false, nodeLevel).toXml();
    }
}
