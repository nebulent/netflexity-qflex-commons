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

public interface MbMessageFlow extends Serializable{

    /**
     * @return Returns the execGroup.
     */
    public MbExecGroup getExecGroup();

    /**
     * @param execGroup The execGroup to set.
     */
    public void setExecGroup(MbExecGroup execGroup);

    /**
     * @return Returns the flowName.
     */
    public String getFlowName();

    /**
     * @param flowName The flowName to set.
     */
    public void setFlowName(String flowName);

    /**
     * @return Returns the flowUuid.
     */
    public String getFlowUuid();

    /**
     * @param flowUuid The flowUuid to set.
     */
    public void setFlowUuid(String flowUuid);
}