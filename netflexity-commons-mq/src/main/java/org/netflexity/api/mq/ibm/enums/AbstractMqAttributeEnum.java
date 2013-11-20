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
 * Enumeration parent of any IBM Websphere MQ object specific attributes.
 */
public class AbstractMqAttributeEnum extends Enum {

    private boolean immutable;
    private boolean mandatory;
    
    /**
     * @param type
     * @param immutable
     * @param mandatory
     */
    public AbstractMqAttributeEnum(int type, boolean immutable, boolean mandatory) {
        super(String.valueOf(type));
        this.immutable = immutable;
        this.mandatory = mandatory;
    }

    /**
     * @param type
     */
    public AbstractMqAttributeEnum(int type) {
        this(type, false, false);
    }
    
    /**
     * @return
     */
    public Integer getValue(){
        return new Integer(getName());
    }

    /**
     * @return Returns the mandatory.
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @return Returns the mutable.
     */
    public boolean isImmutable() {
        return immutable;
    }
}
