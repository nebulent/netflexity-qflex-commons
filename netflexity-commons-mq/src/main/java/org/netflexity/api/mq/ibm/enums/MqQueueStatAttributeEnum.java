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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibm.mq.constants.CMQC;

/**
 * @author MAX
 * 
 * Enumeration of IBM Websphere MQ QueueStat specific attributes.
 */
public class MqQueueStatAttributeEnum extends AbstractMqAttributeEnum {

    public static final MqQueueStatAttributeEnum Q_NAME = new MqQueueStatAttributeEnum(CMQC.MQCA_Q_NAME);
    public static final MqQueueStatAttributeEnum MSG_ENQ_COUNT = new MqQueueStatAttributeEnum(CMQC.MQIA_MSG_ENQ_COUNT);
    public static final MqQueueStatAttributeEnum MSG_DEQ_COUNT = new MqQueueStatAttributeEnum(CMQC.MQIA_MSG_DEQ_COUNT);
    public static final MqQueueStatAttributeEnum HIGH_Q_DEPTH = new MqQueueStatAttributeEnum(CMQC.MQIA_HIGH_Q_DEPTH);
    public static final MqQueueStatAttributeEnum TIME_SINCE_RESET = new MqQueueStatAttributeEnum(CMQC.MQIA_TIME_SINCE_RESET);
    
    /**
     * @param arg0
     */
    public MqQueueStatAttributeEnum(int type) {
        super(type);
    }
    
    /**
     * @param type
     * @return
     */
    public static MqQueueStatAttributeEnum getEnum(int type) {
        return (MqQueueStatAttributeEnum) getEnum(MqQueueStatAttributeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqQueueStatAttributeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqQueueStatAttributeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqQueueStatAttributeEnum.class);
    }
}
