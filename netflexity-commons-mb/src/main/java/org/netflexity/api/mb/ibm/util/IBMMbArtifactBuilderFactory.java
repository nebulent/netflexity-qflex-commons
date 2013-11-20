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
import org.netflexity.api.mb.ibm.enums.SubscriptionTypeEnum;


/**
 * @author MAX
 *
 */
public final class IBMMbArtifactBuilderFactory {

    private static final MbArtifactBuilder BROKER_STATS_TOPIC_NM_BUILDER = 
        new IBMMbMessageBrokerArtifactBuilder();
    private static final MbArtifactBuilder EXEC_GROUP_STATS_TOPIC_NM_BUILDER = 
        new IBMMbExecGroupArtifactBuilder();
    private static final MbArtifactBuilder FLOW_STATS_TOPIC_NM_BUILDER = 
        new IBMMbMessageFlowArtifactBuilder();
    
    private IBMMbArtifactBuilderFactory() {
    }

    /**
     * Create topic name builder for a specific subscription object.
     * 
     * @param type
     * @return
     */
    public static MbArtifactBuilder createArtifactBuilder(SubscriptionTypeEnum type){
        if(SubscriptionTypeEnum.BROKER_TYPE.equals(type)){
            return BROKER_STATS_TOPIC_NM_BUILDER;
        }
        else if(SubscriptionTypeEnum.EXEC_GROUP_TYPE.equals(type)){
            return EXEC_GROUP_STATS_TOPIC_NM_BUILDER;
        }
        else if(SubscriptionTypeEnum.FLOW_TYPE.equals(type)){
            return FLOW_STATS_TOPIC_NM_BUILDER;
        }
        return null;
    }
}
