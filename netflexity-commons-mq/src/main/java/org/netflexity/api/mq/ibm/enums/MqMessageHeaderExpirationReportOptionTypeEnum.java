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
 * Enumeration for all supported expiration report options.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderExpirationReportOptionTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderExpirationReportOptionTypeEnum YES = new MqMessageHeaderExpirationReportOptionTypeEnum(CMQC.MQRO_EXPIRATION);
    public static final MqMessageHeaderExpirationReportOptionTypeEnum DATA = new MqMessageHeaderExpirationReportOptionTypeEnum(CMQC.MQRO_EXPIRATION_WITH_DATA);
    public static final MqMessageHeaderExpirationReportOptionTypeEnum FULL = new MqMessageHeaderExpirationReportOptionTypeEnum(CMQC.MQRO_EXPIRATION_WITH_FULL_DATA);
    
    /**
     * @param type
     */
    public MqMessageHeaderExpirationReportOptionTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderExpirationReportOptionTypeEnum getEnum(int type) {
        return (MqMessageHeaderExpirationReportOptionTypeEnum) getEnum(MqMessageHeaderExpirationReportOptionTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderExpirationReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderExpirationReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderExpirationReportOptionTypeEnum.class);
    }
}
