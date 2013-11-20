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
package org.netflexity.api.mq.ibm.enums;

import org.apache.commons.lang.enums.Enum;

/**
 * @author MAX
 * 
 * Enumeration parent of any IBM Websphere MQ types and options.
 */
public class AbstractMqTypeEnum extends Enum {
    
    /**
     * @param type
     * @param immutable
     * @param mandatory
     */
    public AbstractMqTypeEnum(int type) {
        super(String.valueOf(type));
    }
    
    /**
     * @return
     */
    public Integer getValue(){
        return new Integer(getName());
    }

    /**
     * @return
     */
    public int getIntValue(){
        return Integer.parseInt(getName());
    }
}
