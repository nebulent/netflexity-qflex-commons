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
import com.ibm.mq.constants.CMQCFC;

/**
 * @author MAX
 * 
 * Enumeration of IBM Websphere MQ QueueStat specific attributes.
 */
public class MqAuthRecordAttributeEnum extends AbstractMqAttributeEnum {

    public static final MqAuthRecordAttributeEnum ENTITY_NAME = new MqAuthRecordAttributeEnum(CMQCFC.MQCACF_ENTITY_NAME);
    public static final MqAuthRecordAttributeEnum ENTITY_TYPE = new MqAuthRecordAttributeEnum(CMQCFC.MQCACF_ENTITY_NAME);
    public static final MqAuthRecordAttributeEnum OBJECT_TYPE = new MqAuthRecordAttributeEnum(CMQCFC.MQIACF_OBJECT_TYPE);
    public static final MqAuthRecordAttributeEnum AUTH_PROFILE_NAME = new MqAuthRecordAttributeEnum(CMQCFC.MQCACF_AUTH_PROFILE_NAME);
    public static final MqAuthRecordAttributeEnum QMGR_NAME = new MqAuthRecordAttributeEnum(CMQC.MQCA_Q_MGR_NAME);
    public static final MqAuthRecordAttributeEnum AUTHORIZATION_LIST = new MqAuthRecordAttributeEnum(CMQCFC.MQIACF_AUTHORIZATION_LIST);
    
    /**
     * @param arg0
     */
    public MqAuthRecordAttributeEnum(int type) {
        super(type);
    }
    
    /**
     * @param type
     * @return
     */
    public static MqAuthRecordAttributeEnum getEnum(int type) {
        return (MqAuthRecordAttributeEnum) getEnum(MqAuthRecordAttributeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqAuthRecordAttributeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqAuthRecordAttributeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqAuthRecordAttributeEnum.class);
    }
}
