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
package org.netflexity.api.mb.ibm.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration for all subscription object types.
 * 
 * @author MAX
 * 
 */
public final class SubscriptionTypeEnum extends Enum {

    public static final SubscriptionTypeEnum BROKER_TYPE = new SubscriptionTypeEnum(1);
    public static final SubscriptionTypeEnum EXEC_GROUP_TYPE = new SubscriptionTypeEnum(2);
    public static final SubscriptionTypeEnum FLOW_TYPE = new SubscriptionTypeEnum(3);
    
    /**
     * @param arg0
     */
    public SubscriptionTypeEnum(int type) {
        super(String.valueOf(type));
    }

    /**
     * @param type
     * @return
     */
    public static SubscriptionTypeEnum getEnum(int type) {
        return (SubscriptionTypeEnum) getEnum(SubscriptionTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(SubscriptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(SubscriptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(SubscriptionTypeEnum.class);
    }
}
