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
package org.netflexity.api.mb;

import java.io.Serializable;

import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;


/**
 * Interface that dictates ways to build topic names.
 *  
 * @author MAX
 *
 */
public interface MbArtifactBuilder extends Serializable{
    
    public String STATISTICS_ACCOUNTING_SNAPSHOT = "/StatisticsAccounting/SnapShot/";
    public String $SYS_BROKER = "$SYS/Broker/";
    public String ALL_CHILD_NODES = "+";
    
    /**
     * @param subscriber
     * @return Topic name
     */
    public String getTopicName(Object subscriber);
    
    /**
     * @param subscriber
     * @param nodeLevel
     * @return xml
     */
    public String getActivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel);
    
    /**
     * @param subscriber
     * @param nodeLevel
     * @return xml
     */
    public String getInactivateFlowStatsMessage(Object subscriber, SnapshotNodeDataLevelEnum nodeLevel);
}