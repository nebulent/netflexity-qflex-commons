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
package org.netflexity.api.mq.ibm.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration for all supported message segment types.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderSegmentTypeEnum extends Enum {

    public static final MqMessageHeaderSegmentTypeEnum YES = new MqMessageHeaderSegmentTypeEnum("YES");
    public static final MqMessageHeaderSegmentTypeEnum LAST = new MqMessageHeaderSegmentTypeEnum("LAST");

    /**
     * @param arg0
     */
    public MqMessageHeaderSegmentTypeEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderSegmentTypeEnum getEnum(String type) {
        return (MqMessageHeaderSegmentTypeEnum) getEnum(MqMessageHeaderSegmentTypeEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderSegmentTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderSegmentTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderSegmentTypeEnum.class);
    }
}
