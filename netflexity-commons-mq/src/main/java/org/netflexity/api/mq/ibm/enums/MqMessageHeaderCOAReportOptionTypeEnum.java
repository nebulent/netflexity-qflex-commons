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
 * Enumeration for all supported COA report options.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderCOAReportOptionTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderCOAReportOptionTypeEnum YES = new MqMessageHeaderCOAReportOptionTypeEnum(CMQC.MQRO_COA);
    public static final MqMessageHeaderCOAReportOptionTypeEnum DATA = new MqMessageHeaderCOAReportOptionTypeEnum(CMQC.MQRO_COA_WITH_DATA);
    public static final MqMessageHeaderCOAReportOptionTypeEnum FULL = new MqMessageHeaderCOAReportOptionTypeEnum(CMQC.MQRO_COA_WITH_FULL_DATA);
    
    /**
     * @param type
     */
    public MqMessageHeaderCOAReportOptionTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderCOAReportOptionTypeEnum getEnum(int type) {
        return (MqMessageHeaderCOAReportOptionTypeEnum) getEnum(MqMessageHeaderCOAReportOptionTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderCOAReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderCOAReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderCOAReportOptionTypeEnum.class);
    }
}
