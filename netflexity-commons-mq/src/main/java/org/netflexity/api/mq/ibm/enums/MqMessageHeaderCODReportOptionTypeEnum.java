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
 * Enumeration for all supported COD report options.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderCODReportOptionTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderCODReportOptionTypeEnum YES = new MqMessageHeaderCODReportOptionTypeEnum(CMQC.MQRO_COD);
    public static final MqMessageHeaderCODReportOptionTypeEnum DATA = new MqMessageHeaderCODReportOptionTypeEnum(CMQC.MQRO_COD_WITH_DATA);
    public static final MqMessageHeaderCODReportOptionTypeEnum FULL = new MqMessageHeaderCODReportOptionTypeEnum(CMQC.MQRO_COD_WITH_FULL_DATA);
    
    /**
     * @param type
     */
    public MqMessageHeaderCODReportOptionTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderCODReportOptionTypeEnum getEnum(int type) {
        return (MqMessageHeaderCODReportOptionTypeEnum) getEnum(MqMessageHeaderCODReportOptionTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderCODReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderCODReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderCODReportOptionTypeEnum.class);
    }
}
