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
 * Enumeration of IBM Websphere MQ Queue specific attributes.
 */
public class MqQueueAttributeEnum extends AbstractMqAttributeEnum {

    public static final MqQueueAttributeEnum Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_Q_NAME, true, true);
    public static final MqQueueAttributeEnum Q_TYPE = new MqQueueAttributeEnum(CMQC.MQIA_Q_TYPE, true, true);
    public static final MqQueueAttributeEnum Q_DESC = new MqQueueAttributeEnum(CMQC.MQCA_Q_DESC);
    public static final MqQueueAttributeEnum Q_DEPTH_HIGH_LIMIT = new MqQueueAttributeEnum(CMQC.MQIA_Q_DEPTH_HIGH_LIMIT);
    public static final MqQueueAttributeEnum Q_DEPTH_LOW_LIMIT = new MqQueueAttributeEnum(CMQC.MQIA_Q_DEPTH_LOW_LIMIT);
    public static final MqQueueAttributeEnum Q_DEPTH_HIGH_EVENT = new MqQueueAttributeEnum(CMQC.MQIA_Q_DEPTH_HIGH_EVENT);
    public static final MqQueueAttributeEnum Q_DEPTH_LOW_EVENT = new MqQueueAttributeEnum(CMQC.MQIA_Q_DEPTH_LOW_EVENT);
    public static final MqQueueAttributeEnum Q_DEPTH_MAX_EVENT = new MqQueueAttributeEnum(CMQC.MQIA_Q_DEPTH_MAX_EVENT);
    public static final MqQueueAttributeEnum Q_SERVICE_INTERVAL = new MqQueueAttributeEnum(CMQC.MQIA_Q_SERVICE_INTERVAL);
    public static final MqQueueAttributeEnum Q_SERVICE_INTERVAL_EVENT = new MqQueueAttributeEnum(CMQC.MQIA_Q_SERVICE_INTERVAL_EVENT);
    public static final MqQueueAttributeEnum BASE_Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_BASE_Q_NAME);
    public static final MqQueueAttributeEnum INHIBIT_PUT = new MqQueueAttributeEnum(CMQC.MQIA_INHIBIT_PUT);
    public static final MqQueueAttributeEnum INHIBIT_GET = new MqQueueAttributeEnum(CMQC.MQIA_INHIBIT_GET);
    public static final MqQueueAttributeEnum DEF_PRIORITY = new MqQueueAttributeEnum(CMQC.MQIA_DEF_PRIORITY);
    public static final MqQueueAttributeEnum DEF_PERSISTENCE = new MqQueueAttributeEnum(CMQC.MQIA_DEF_PERSISTENCE);
    public static final MqQueueAttributeEnum PROCESS_NAME = new MqQueueAttributeEnum(CMQC.MQCA_PROCESS_NAME);
    public static final MqQueueAttributeEnum MAX_Q_DEPTH = new MqQueueAttributeEnum(CMQC.MQIA_MAX_Q_DEPTH);
    public static final MqQueueAttributeEnum MAX_MSG_LENGTH = new MqQueueAttributeEnum(CMQC.MQIA_MAX_MSG_LENGTH);       // QM & Q
    public static final MqQueueAttributeEnum CURRENT_Q_DEPTH = new MqQueueAttributeEnum(CMQC.MQIA_CURRENT_Q_DEPTH);
    public static final MqQueueAttributeEnum OPEN_INPUT_COUNT = new MqQueueAttributeEnum(CMQC.MQIA_OPEN_INPUT_COUNT);
    public static final MqQueueAttributeEnum OPEN_OUTPUT_COUNT = new MqQueueAttributeEnum(CMQC.MQIA_OPEN_OUTPUT_COUNT);
    public static final MqQueueAttributeEnum BACKOUT_THRESHOLD = new MqQueueAttributeEnum(CMQC.MQIA_BACKOUT_THRESHOLD);
    public static final MqQueueAttributeEnum BACKOUT_REQ_Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_BACKOUT_REQ_Q_NAME);
    public static final MqQueueAttributeEnum SHAREABILITY = new MqQueueAttributeEnum(CMQC.MQIA_SHAREABILITY);
    public static final MqQueueAttributeEnum DEF_INPUT_OPEN_OPTION = new MqQueueAttributeEnum(CMQC.MQIA_DEF_INPUT_OPEN_OPTION);
    public static final MqQueueAttributeEnum HARDEN_GET_BACKOUT = new MqQueueAttributeEnum(CMQC.MQIA_HARDEN_GET_BACKOUT);
    public static final MqQueueAttributeEnum MSG_DELIVERY_SEQUENCE = new MqQueueAttributeEnum(CMQC.MQIA_MSG_DELIVERY_SEQUENCE);
    public static final MqQueueAttributeEnum RETENTION_INTERVAL = new MqQueueAttributeEnum(CMQC.MQIA_RETENTION_INTERVAL);
    public static final MqQueueAttributeEnum DIST_LISTS = new MqQueueAttributeEnum(CMQC.MQIA_DIST_LISTS);               // QM & Q
    public static final MqQueueAttributeEnum USAGE = new MqQueueAttributeEnum(CMQC.MQIA_USAGE);
    public static final MqQueueAttributeEnum INITIATION_Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_INITIATION_Q_NAME);
    public static final MqQueueAttributeEnum TRIGGER_CONTROL = new MqQueueAttributeEnum(CMQC.MQIA_TRIGGER_CONTROL);
    public static final MqQueueAttributeEnum TRIGGER_TYPE = new MqQueueAttributeEnum(CMQC.MQIA_TRIGGER_TYPE);
    public static final MqQueueAttributeEnum TRIGGER_MSG_PRIORITY = new MqQueueAttributeEnum(CMQC.MQIA_TRIGGER_MSG_PRIORITY);
    public static final MqQueueAttributeEnum TRIGGER_DEPTH = new MqQueueAttributeEnum(CMQC.MQIA_TRIGGER_DEPTH);
    public static final MqQueueAttributeEnum TRIGGER_DATA = new MqQueueAttributeEnum(CMQC.MQCA_TRIGGER_DATA);
    public static final MqQueueAttributeEnum REMOTE_Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_REMOTE_Q_NAME);
    public static final MqQueueAttributeEnum REMOTE_Q_MGR_NAME = new MqQueueAttributeEnum(CMQC.MQCA_REMOTE_Q_MGR_NAME);
    public static final MqQueueAttributeEnum XMIT_Q_NAME = new MqQueueAttributeEnum(CMQC.MQCA_XMIT_Q_NAME);
    public static final MqQueueAttributeEnum DEFINITION_TYPE = new MqQueueAttributeEnum(CMQC.MQIA_DEFINITION_TYPE);
    public static final MqQueueAttributeEnum SCOPE = new MqQueueAttributeEnum(CMQC.MQIA_SCOPE);
    public static final MqQueueAttributeEnum CLUSTER_NAME = new MqQueueAttributeEnum(CMQC.MQCA_CLUSTER_NAME);           // Q & CH
    public static final MqQueueAttributeEnum CLUSTER_NAMELIST = new MqQueueAttributeEnum(CMQC.MQCA_CLUSTER_NAMELIST);   // Q & CH
    public static final MqQueueAttributeEnum DEF_BIND = new MqQueueAttributeEnum(CMQC.MQIA_DEF_BIND);
    public static final MqQueueAttributeEnum ALTERATION_DATE = new MqQueueAttributeEnum(CMQC.MQCA_ALTERATION_DATE, true, false);     // ALL
    public static final MqQueueAttributeEnum ALTERATION_TIME = new MqQueueAttributeEnum(CMQC.MQCA_ALTERATION_TIME, true, false);     // ALL
    
    /**
     * @param arg0
     */
    public MqQueueAttributeEnum(int type) {
        super(type);
    }
    
    /**
     * @param type
     * @param immutable
     * @param mandatory
     */
    public MqQueueAttributeEnum(int type, boolean immutable, boolean mandatory) {
        super(type, immutable, mandatory);
    }

    /**
     * @param type
     * @return
     */
    public static MqQueueAttributeEnum getEnum(int type) {
        return (MqQueueAttributeEnum) getEnum(MqQueueAttributeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqQueueAttributeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqQueueAttributeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqQueueAttributeEnum.class);
    }
}
