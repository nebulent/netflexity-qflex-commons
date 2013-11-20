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
 * Enumeration for all supported exception report options.
 * 
 * @author MAX
 * 
 */
public final class MqMessageHeaderExceptionReportOptionTypeEnum extends AbstractMqTypeEnum {

    public static final MqMessageHeaderExceptionReportOptionTypeEnum YES = new MqMessageHeaderExceptionReportOptionTypeEnum(CMQC.MQRO_EXCEPTION);
    public static final MqMessageHeaderExceptionReportOptionTypeEnum DATA = new MqMessageHeaderExceptionReportOptionTypeEnum(CMQC.MQRO_EXCEPTION_WITH_DATA);
    public static final MqMessageHeaderExceptionReportOptionTypeEnum FULL = new MqMessageHeaderExceptionReportOptionTypeEnum(CMQC.MQRO_EXCEPTION_WITH_FULL_DATA);
    
    /**
     * @param type
     */
    public MqMessageHeaderExceptionReportOptionTypeEnum(int type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static MqMessageHeaderExceptionReportOptionTypeEnum getEnum(int type) {
        return (MqMessageHeaderExceptionReportOptionTypeEnum) getEnum(MqMessageHeaderExceptionReportOptionTypeEnum.class, String.valueOf(type));
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(MqMessageHeaderExceptionReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(MqMessageHeaderExceptionReportOptionTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(MqMessageHeaderExceptionReportOptionTypeEnum.class);
    }
}
