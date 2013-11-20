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
 * Enumeration of IBM Websphere MQ Queue Manager specific attributes.
 */
public class MqQmanagerAttributeEnum extends AbstractMqAttributeEnum {
    
    public static final MqQmanagerAttributeEnum Q_MGR_NAME = new MqQmanagerAttributeEnum(CMQC.MQCA_Q_MGR_NAME, true, false);             // QM, CH
    public static final MqQmanagerAttributeEnum Q_MGR_DESC = new MqQmanagerAttributeEnum(CMQC.MQCA_Q_MGR_DESC);
    public static final MqQmanagerAttributeEnum Q_MGR_IDENTIFIER = new MqQmanagerAttributeEnum(CMQC.MQCA_Q_MGR_IDENTIFIER, true, false);
    public static final MqQmanagerAttributeEnum AUTHORITY_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_AUTHORITY_EVENT);
    public static final MqQmanagerAttributeEnum CHANNEL_AUTO_DEF = new MqQmanagerAttributeEnum(CMQC.MQIA_CHANNEL_AUTO_DEF);
    public static final MqQmanagerAttributeEnum CHANNEL_AUTO_DEF_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_CHANNEL_AUTO_DEF_EVENT);
    public static final MqQmanagerAttributeEnum CHANNEL_AUTO_DEF_EXIT = new MqQmanagerAttributeEnum(CMQC.MQCA_CHANNEL_AUTO_DEF_EXIT);
    public static final MqQmanagerAttributeEnum CLUSTER_WORKLOAD_DATA = new MqQmanagerAttributeEnum(CMQC.MQCA_CLUSTER_WORKLOAD_DATA);
    public static final MqQmanagerAttributeEnum CLUSTER_WORKLOAD_EXIT = new MqQmanagerAttributeEnum(CMQC.MQCA_CLUSTER_WORKLOAD_EXIT);
    public static final MqQmanagerAttributeEnum CLUSTER_WORKLOAD_LENGTH = new MqQmanagerAttributeEnum(CMQC.MQIA_CLUSTER_WORKLOAD_LENGTH);
    public static final MqQmanagerAttributeEnum CODED_CHAR_SET_ID = new MqQmanagerAttributeEnum(CMQC.MQIA_CODED_CHAR_SET_ID);
    public static final MqQmanagerAttributeEnum COMMAND_INPUT_Q_NAME = new MqQmanagerAttributeEnum(CMQC.MQCA_COMMAND_INPUT_Q_NAME, true, false);
    public static final MqQmanagerAttributeEnum COMMAND_LEVEL = new MqQmanagerAttributeEnum(CMQC.MQIA_COMMAND_LEVEL, true, false);
    public static final MqQmanagerAttributeEnum DEAD_LETTER_Q_NAME = new MqQmanagerAttributeEnum(CMQC.MQCA_DEAD_LETTER_Q_NAME);
    public static final MqQmanagerAttributeEnum DEF_XMIT_Q_NAME = new MqQmanagerAttributeEnum(CMQC.MQCA_DEF_XMIT_Q_NAME);
    public static final MqQmanagerAttributeEnum INHIBIT_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_INHIBIT_EVENT);
    public static final MqQmanagerAttributeEnum LOCAL_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_LOCAL_EVENT);
    public static final MqQmanagerAttributeEnum MAX_MSG_LENGTH = new MqQmanagerAttributeEnum(CMQC.MQIA_MAX_MSG_LENGTH, true, false);     // QM, Q
    public static final MqQmanagerAttributeEnum MAX_HANDLES = new MqQmanagerAttributeEnum(CMQC.MQIA_MAX_HANDLES);
    public static final MqQmanagerAttributeEnum MAX_PRIORITY = new MqQmanagerAttributeEnum(CMQC.MQIA_MAX_PRIORITY, true, false);
    public static final MqQmanagerAttributeEnum MAX_UNCOMMITTED_MSGS = new MqQmanagerAttributeEnum(CMQC.MQIA_MAX_UNCOMMITTED_MSGS);
    public static final MqQmanagerAttributeEnum PERFORMANCE_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_PERFORMANCE_EVENT);
    public static final MqQmanagerAttributeEnum PLATFORM = new MqQmanagerAttributeEnum(CMQC.MQIA_PLATFORM, true, false);
    public static final MqQmanagerAttributeEnum REMOTE_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_REMOTE_EVENT);
    public static final MqQmanagerAttributeEnum REPOSITORY_NAME = new MqQmanagerAttributeEnum(CMQC.MQCA_REPOSITORY_NAME);
    public static final MqQmanagerAttributeEnum REPOSITORY_NAMELIST = new MqQmanagerAttributeEnum(CMQC.MQCA_REPOSITORY_NAMELIST);
    public static final MqQmanagerAttributeEnum START_STOP_EVENT = new MqQmanagerAttributeEnum(CMQC.MQIA_START_STOP_EVENT);
    public static final MqQmanagerAttributeEnum SYNCPOINT = new MqQmanagerAttributeEnum(CMQC.MQIA_SYNCPOINT, true, false);
    public static final MqQmanagerAttributeEnum TRIGGER_INTERVAL = new MqQmanagerAttributeEnum(CMQC.MQIA_TRIGGER_INTERVAL);
    public static final MqQmanagerAttributeEnum DIST_LISTS = new MqQmanagerAttributeEnum(CMQC.MQIA_DIST_LISTS, true, false);             // QM, Q
    public static final MqQmanagerAttributeEnum SSL_CRL_NAMELIST = new MqQmanagerAttributeEnum(CMQC.MQCA_SSL_CRL_NAMELIST);
    public static final MqQmanagerAttributeEnum SSL_KEY_REPOSITORY = new MqQmanagerAttributeEnum(CMQC.MQCA_SSL_KEY_REPOSITORY);
    public static final MqQmanagerAttributeEnum ALTERATION_DATE = new MqQmanagerAttributeEnum(CMQC.MQCA_ALTERATION_DATE, true, false);   // ALL
    public static final MqQmanagerAttributeEnum ALTERATION_TIME = new MqQmanagerAttributeEnum(CMQC.MQCA_ALTERATION_TIME, true, false);   // ALL
    public static final MqQmanagerAttributeEnum CREATION_DATE = new MqQmanagerAttributeEnum(CMQC.MQCA_CREATION_DATE, true, false);
    public static final MqQmanagerAttributeEnum CREATION_TIME = new MqQmanagerAttributeEnum(CMQC.MQCA_CREATION_TIME, true, false);
    
    /**
     * @param arg0
     */
    public MqQmanagerAttributeEnum(int type) {
        super(type);
    }
    
    /**
     * @param type
     * @param immutable
     * @param mandatory
     */
    public MqQmanagerAttributeEnum(int type, boolean immutable, boolean mandatory) {
        super(type, immutable, mandatory);
    }

    /**
     * @param type
     * @return
     */
    public static MqQmanagerAttributeEnum getEnum(int type) {
        return (MqQmanagerAttributeEnum) getEnum(MqQmanagerAttributeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqQmanagerAttributeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqQmanagerAttributeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqQmanagerAttributeEnum.class);
    }
}
