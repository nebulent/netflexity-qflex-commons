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

public interface MbNodeStatistics extends Serializable{

    // Node's child collections.
    public List getTerminals();

    public void addTerminal(MbTerminalStatistics term);

    // Node's members.
    public Long getCountOfInvocations();

    public void setCountOfInvocations(String countOfInvocations);

    public String getLabel();

    public void setLabel(String label);

    public Long getMaximumCPUTime();

    public void setMaximumCPUTime(String maximumCPUTime);

    public Long getMaximumElapsedTime();

    public void setMaximumElapsedTime(String maximumElapsedTime);

    public Long getMinimumCPUTime();

    public void setMinimumCPUTime(String minimumCPUTime);

    public Long getMinimumElapsedTime();

    public void setMinimumElapsedTime(String minimumElapsedTime);

    public Long getNumberOfInputTerminals();

    public void setNumberOfInputTerminals(String numberOfInputTerminals);

    public Long getNumberOfOutputTerminals();

    public void setNumberOfOutputTerminals(String numberOfOutputTerminals);

    public Long getTotalCPUTime();

    public void setTotalCPUTime(String totalCPUTime);

    public Long getTotalElapsedTime();

    public void setTotalElapsedTime(String totalElapsedTime);

    public String getType();

    public void setType(String type);

}