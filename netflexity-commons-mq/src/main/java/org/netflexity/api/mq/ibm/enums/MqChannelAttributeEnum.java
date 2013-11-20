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
 * Enumeration of IBM Websphere MQ Channel specific attributes.
 */
public class MqChannelAttributeEnum extends AbstractMqAttributeEnum {

    public static final MqChannelAttributeEnum CHANNEL_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_CHANNEL_NAME, true, true);
    public static final MqChannelAttributeEnum CHANNEL_TYPE = new MqChannelAttributeEnum(CMQCFC.MQIACH_CHANNEL_TYPE, true, true);
    public static final MqChannelAttributeEnum CHANNEL_STATUS = new MqChannelAttributeEnum(CMQCFC.MQIACH_CHANNEL_STATUS);
    public static final MqChannelAttributeEnum CHANNEL_DESC = new MqChannelAttributeEnum(CMQCFC.MQCACH_DESC);
    public static final MqChannelAttributeEnum XMIT_PROTOCOL_TYPE = new MqChannelAttributeEnum(CMQCFC.MQIACH_XMIT_PROTOCOL_TYPE);
    public static final MqChannelAttributeEnum SEC_EXIT_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_SEC_EXIT_NAME);
    public static final MqChannelAttributeEnum SEC_EXIT_USER_DATA = new MqChannelAttributeEnum(CMQCFC.MQCACH_SEC_EXIT_USER_DATA);
    public static final MqChannelAttributeEnum MSG_EXIT_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_MSG_EXIT_NAME);
    public static final MqChannelAttributeEnum MSG_EXIT_USER_DATA = new MqChannelAttributeEnum(CMQCFC.MQCACH_MSG_EXIT_USER_DATA);
    public static final MqChannelAttributeEnum SEND_EXIT_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_SEND_EXIT_NAME);
    public static final MqChannelAttributeEnum SEND_EXIT_USER_DATA = new MqChannelAttributeEnum(CMQCFC.MQCACH_SEND_EXIT_USER_DATA);
    public static final MqChannelAttributeEnum RCV_EXIT_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_RCV_EXIT_NAME);
    public static final MqChannelAttributeEnum RCV_EXIT_USER_DATA = new MqChannelAttributeEnum(CMQCFC.MQCACH_RCV_EXIT_USER_DATA);
    public static final MqChannelAttributeEnum TP_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_TP_NAME);
    public static final MqChannelAttributeEnum MODE_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_MODE_NAME);
    public static final MqChannelAttributeEnum CONNECTION_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_CONNECTION_NAME);
    public static final MqChannelAttributeEnum XMIT_Q_NAME_4CHANNEL = new MqChannelAttributeEnum(CMQCFC.MQCACH_XMIT_Q_NAME);
    public static final MqChannelAttributeEnum BATCH_SIZE = new MqChannelAttributeEnum(CMQCFC.MQIACH_BATCH_SIZE);
    public static final MqChannelAttributeEnum BATCH_INTERVAL = new MqChannelAttributeEnum(CMQCFC.MQIACH_BATCH_INTERVAL);
    public static final MqChannelAttributeEnum BATCH_HB = new MqChannelAttributeEnum(CMQCFC.MQIACH_BATCH_HB);
    public static final MqChannelAttributeEnum DISC_INTERVAL = new MqChannelAttributeEnum(CMQCFC.MQIACH_DISC_INTERVAL);
    public static final MqChannelAttributeEnum SHORT_RETRY = new MqChannelAttributeEnum(CMQCFC.MQIACH_SHORT_RETRY);
    public static final MqChannelAttributeEnum SHORT_TIMER = new MqChannelAttributeEnum(CMQCFC.MQIACH_SHORT_TIMER);
    public static final MqChannelAttributeEnum LONG_RETRY = new MqChannelAttributeEnum(CMQCFC.MQIACH_LONG_RETRY);
    public static final MqChannelAttributeEnum LONG_TIMER = new MqChannelAttributeEnum(CMQCFC.MQIACH_LONG_TIMER);
    public static final MqChannelAttributeEnum DATA_CONVERSION = new MqChannelAttributeEnum(CMQCFC.MQIACH_DATA_CONVERSION);
    public static final MqChannelAttributeEnum PUT_AUTHORITY = new MqChannelAttributeEnum(CMQCFC.MQIACH_PUT_AUTHORITY);
    public static final MqChannelAttributeEnum SEQUENCE_NUMBER_WRAP = new MqChannelAttributeEnum(CMQCFC.MQIACH_SEQUENCE_NUMBER_WRAP);
    public static final MqChannelAttributeEnum MCA_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_MCA_NAME);
    public static final MqChannelAttributeEnum MCA_TYPE = new MqChannelAttributeEnum(CMQCFC.MQIACH_MCA_TYPE);
    public static final MqChannelAttributeEnum MCA_USER_ID = new MqChannelAttributeEnum(CMQCFC.MQCACH_MCA_USER_ID);
    public static final MqChannelAttributeEnum USER_ID = new MqChannelAttributeEnum(CMQCFC.MQCACH_USER_ID);
    public static final MqChannelAttributeEnum PASSWORD = new MqChannelAttributeEnum(CMQCFC.MQCACH_PASSWORD);
    public static final MqChannelAttributeEnum MR_EXIT_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_MR_EXIT_NAME);
    public static final MqChannelAttributeEnum MR_EXIT_USER_DATA = new MqChannelAttributeEnum(CMQCFC.MQCACH_MR_EXIT_USER_DATA);
    public static final MqChannelAttributeEnum MAX_MSG_LENGTH_4CHANNEL = new MqChannelAttributeEnum(CMQCFC.MQIACH_MAX_MSG_LENGTH);
    public static final MqChannelAttributeEnum MR_COUNT = new MqChannelAttributeEnum(CMQCFC.MQIACH_MR_COUNT);
    public static final MqChannelAttributeEnum MR_INTERVAL = new MqChannelAttributeEnum(CMQCFC.MQIACH_MR_INTERVAL);
    public static final MqChannelAttributeEnum HB_INTERVAL = new MqChannelAttributeEnum(CMQCFC.MQIACH_HB_INTERVAL);
    public static final MqChannelAttributeEnum NPM_SPEED = new MqChannelAttributeEnum(CMQCFC.MQIACH_NPM_SPEED);
    public static final MqChannelAttributeEnum NETWORK_PRIORITY = new MqChannelAttributeEnum(CMQCFC.MQIACH_NETWORK_PRIORITY);
    public static final MqChannelAttributeEnum LOCAL_ADDRESS = new MqChannelAttributeEnum(CMQCFC.MQCACH_LOCAL_ADDRESS);
    public static final MqChannelAttributeEnum SSL_CIPHER_SPEC = new MqChannelAttributeEnum(CMQCFC.MQCACH_SSL_CIPHER_SPEC);
    public static final MqChannelAttributeEnum SSL_PEER_NAME = new MqChannelAttributeEnum(CMQCFC.MQCACH_SSL_PEER_NAME);
    public static final MqChannelAttributeEnum SSL_CLIENT_AUTH = new MqChannelAttributeEnum(CMQCFC.MQIACH_SSL_CLIENT_AUTH);
    public static final MqChannelAttributeEnum Q_MGR_NAME = new MqChannelAttributeEnum(CMQC.MQCA_Q_MGR_NAME);                           // QM & CH
    public static final MqChannelAttributeEnum CLUSTER_NAME = new MqChannelAttributeEnum(CMQC.MQCA_CLUSTER_NAME);                       // Q & CH
    public static final MqChannelAttributeEnum CLUSTER_NAMELIST = new MqChannelAttributeEnum(CMQC.MQCA_CLUSTER_NAMELIST);               // Q & CH
    public static final MqChannelAttributeEnum ALTERATION_DATE = new MqChannelAttributeEnum(CMQC.MQCA_ALTERATION_DATE, true, false);    // ALL
    public static final MqChannelAttributeEnum ALTERATION_TIME = new MqChannelAttributeEnum(CMQC.MQCA_ALTERATION_TIME, true, false);    // ALL
    
    // Used for inquire channel command.
    public static final MqChannelAttributeEnum CHANNEL_START_DATE = new MqChannelAttributeEnum(CMQCFC.MQCACH_CHANNEL_START_DATE);
    public static final MqChannelAttributeEnum CHANNEL_START_TIME = new MqChannelAttributeEnum(CMQCFC.MQCACH_CHANNEL_START_TIME);
    public static final MqChannelAttributeEnum CHANNEL_INSTANCE_TYPE = new MqChannelAttributeEnum(CMQCFC.MQIACH_CHANNEL_INSTANCE_TYPE);
    
    /**
     * @param arg0
     */
    public MqChannelAttributeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @param immutable
     * @param mandatory
     */
    public MqChannelAttributeEnum(int type, boolean immutable, boolean mandatory) {
        super(type, immutable, mandatory);
    }

    /**
     * @param type
     * @return
     */
    public static MqChannelAttributeEnum getEnum(int type) {
        return (MqChannelAttributeEnum) getEnum(MqChannelAttributeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqChannelAttributeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqChannelAttributeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqChannelAttributeEnum.class);
    }
}
