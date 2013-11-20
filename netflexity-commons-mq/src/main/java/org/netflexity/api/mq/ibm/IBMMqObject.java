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
package org.netflexity.api.mq.ibm;

import java.io.Serializable;
import java.util.Map;

import org.netflexity.api.mq.ibm.enums.AbstractMqAttributeEnum;

public interface IBMMqObject extends Serializable{

    /**
     * @param key
     * @param value
     */
    void addAttribute(AbstractMqAttributeEnum key, Object value);

    /**
     * @return a copy of all the attributes.
     */
    Map getAttributes();

    /**
     * @param key
     * @return
     */
    Object getAttribute(AbstractMqAttributeEnum key);

}