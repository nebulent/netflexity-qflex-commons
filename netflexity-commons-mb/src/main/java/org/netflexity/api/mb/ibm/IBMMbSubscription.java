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

import org.netflexity.api.mb.MbSubscription;
import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.mb.ibm.enums.SubscriptionTypeEnum;


/**
 * @author MAX
 *
 */
public class IBMMbSubscription implements MbSubscription {

    private Object subscriptionObject;
    private SubscriptionTypeEnum subscriptionType;
    private SnapshotNodeDataLevelEnum nodeDataLevel;
    private String subscriptionId;
    
    /**
     * @param subscriptionObject
     * @param subscriptionType
     * @param nodeDataLevel
     * @param subscriptionId
     */
    public IBMMbSubscription(Object subscriptionObject, SubscriptionTypeEnum subscriptionType, SnapshotNodeDataLevelEnum nodeDataLevel, String subscriptionId) {
        this.subscriptionObject = subscriptionObject;
        this.subscriptionType = subscriptionType;
        this.nodeDataLevel = nodeDataLevel;
        this.subscriptionId = subscriptionId;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#getSubscriptionObject()
     */
    public Object getSubscriptionObject() {
        return subscriptionObject;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#setSubscriptionObject(java.lang.Object)
     */
    public void setSubscriptionObject(Object subscriptionObject) {
        this.subscriptionObject = subscriptionObject;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#getSubscriptionType()
     */
    public SubscriptionTypeEnum getSubscriptionType(){
        return subscriptionType;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#setSubscriptionType(com.netflexity.qflex.data.enums.SubscriptionTypeEnum)
     */
    public void setSubscriptionType(SubscriptionTypeEnum subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    
    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#getNodeLevel()
     */
    public SnapshotNodeDataLevelEnum getNodeLevel(){
        return nodeDataLevel;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#getNodeDataLevel()
     */
    public SnapshotNodeDataLevelEnum getNodeDataLevel() {
        return nodeDataLevel;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#setNodeDataLevel(com.netflexity.wbi.business.mb.enums.SnapshotNodeDataLevelEnum)
     */
    public void setNodeDataLevel(SnapshotNodeDataLevelEnum nodeDataLevel) {
        this.nodeDataLevel = nodeDataLevel;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#getSubscriptionId()
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /* (non-Javadoc)
     * @see com.netflexity.wbi.business.MbSubscription#setSubscriptionId(java.lang.String)
     */
    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}