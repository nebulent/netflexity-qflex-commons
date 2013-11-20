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
 * Enumeration for all supported message persistence.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderPersistenceTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderPersistenceTypeEnum PERSISTENT = new MqMessageHeaderPersistenceTypeEnum(CMQC.MQPER_PERSISTENT);
    public static final MqMessageHeaderPersistenceTypeEnum NOT_PERSISTENT = new MqMessageHeaderPersistenceTypeEnum(CMQC.MQPER_NOT_PERSISTENT);
    public static final MqMessageHeaderPersistenceTypeEnum AS_QUEUE = new MqMessageHeaderPersistenceTypeEnum(CMQC.MQPER_PERSISTENCE_AS_Q_DEF);
    
    /**
     * @param type
     */
    public MqMessageHeaderPersistenceTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderPersistenceTypeEnum getEnum(int type) {
        return (MqMessageHeaderPersistenceTypeEnum) getEnum(MqMessageHeaderPersistenceTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderPersistenceTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderPersistenceTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderPersistenceTypeEnum.class);
    }
}
