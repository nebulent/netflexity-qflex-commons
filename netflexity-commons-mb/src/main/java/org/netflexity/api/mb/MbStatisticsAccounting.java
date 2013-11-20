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

public interface MbStatisticsAccounting extends Serializable{

    /**
     * @return
     */
    public List getNodes();

    /**
     * @return
     */
    public List getThreads();

    /**
     * @return
     */
    public List getFlows();

    /**
     * @return
     */
    public String getRecordCode();

    /**
     * @param recordCode
     */
    public void setRecordCode(String recordCode);

    /**
     * @return
     */
    public String getRecordType();

    /**
     * @param recordType
     */
    public void setRecordType(String recordType);

}