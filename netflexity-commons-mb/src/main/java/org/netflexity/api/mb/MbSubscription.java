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
package org.netflexity.api.mb;

import java.io.Serializable;

import org.netflexity.api.mb.ibm.enums.SnapshotNodeDataLevelEnum;
import org.netflexity.api.mb.ibm.enums.SubscriptionTypeEnum;


public interface MbSubscription extends Serializable{

    /**
     * @return Returns the subscriptionObject.
     */
    public Object getSubscriptionObject();

    /**
     * @param subscriptionObject The subscriptionObject to set.
     */
    public void setSubscriptionObject(Object subscriptionObject);

    /**
     * @return subscription type enumeration.
     */
    public SubscriptionTypeEnum getSubscriptionType();

    /**
     * @param subscriptionType The subscriptionType to set.
     */
    public void setSubscriptionType(SubscriptionTypeEnum subscriptionType);
    
    /**
     * @return Returns the nodeDataLevel.
     */
    public SnapshotNodeDataLevelEnum getNodeDataLevel();

    /**
     * @param nodeDataLevel The nodeDataLevel to set.
     */
    public void setNodeDataLevel(SnapshotNodeDataLevelEnum nodeDataLevel);

    /**
     * @return Returns the subscriptionId.
     */
    public String getSubscriptionId();

    /**
     * @param subscriptionId The subscriptionId to set.
     */
    public void setSubscriptionId(String subscriptionId);

}