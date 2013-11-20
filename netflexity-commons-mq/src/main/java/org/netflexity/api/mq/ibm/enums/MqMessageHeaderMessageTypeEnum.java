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

import com.ibm.mq.constants.CMQC;

/**
 * Enumeration for all supported message types.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderMessageTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderMessageTypeEnum DATAGRAM = new MqMessageHeaderMessageTypeEnum(CMQC.MQMT_DATAGRAM);
    public static final MqMessageHeaderMessageTypeEnum REPLY = new MqMessageHeaderMessageTypeEnum(CMQC.MQMT_REPLY);
    public static final MqMessageHeaderMessageTypeEnum REPORT = new MqMessageHeaderMessageTypeEnum(CMQC.MQMT_REPORT);
    public static final MqMessageHeaderMessageTypeEnum REQUEST = new MqMessageHeaderMessageTypeEnum(CMQC.MQMT_REQUEST);
    
    /**
     * @param arg0
     */
    public MqMessageHeaderMessageTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderMessageTypeEnum getEnum(int type) {
        return (MqMessageHeaderMessageTypeEnum) getEnum(MqMessageHeaderMessageTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderMessageTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderMessageTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderMessageTypeEnum.class);
    }
}
