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
import java.util.List;

public interface MbExecGroup extends Serializable{

    /**
     * @return Returns the broker.
     */
    public MbMessageBroker getBroker();

    /**
     * @param broker The broker to set.
     */
    public void setBroker(MbMessageBroker broker);

    /**
     * @return Returns the execGroupName.
     */
    public String getExecGroupName();

    /**
     * @param execGroupName The execGroupName to set.
     */
    public void setExecGroupName(String execGroupName);

    /**
     * @return Returns the execGroupUuid.
     */
    public String getExecGroupUuid();

    /**
     * @param execGroupUuid The execGroupUuid to set.
     */
    public void setExecGroupUuid(String execGroupUuid);

    /**
     * @return Returns the messageFlows.
     */
    public List getMessageFlows();
}