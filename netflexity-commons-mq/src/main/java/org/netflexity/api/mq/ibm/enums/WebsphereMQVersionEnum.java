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

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration for all supported operating systems..
 * 
 * @author MAX
 * 
 */
public final class WebsphereMQVersionEnum extends Enum {

    public static final WebsphereMQVersionEnum WSMQv5X = new WebsphereMQVersionEnum("5.X");
    public static final WebsphereMQVersionEnum WSMQv6X = new WebsphereMQVersionEnum("6.X");
    public static final WebsphereMQVersionEnum WSMQv7X = new WebsphereMQVersionEnum("7.X");
    
    /**
     * @param arg0
     */
    public WebsphereMQVersionEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static WebsphereMQVersionEnum getEnum(String type) {
        return (WebsphereMQVersionEnum) getEnum(WebsphereMQVersionEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(WebsphereMQVersionEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(WebsphereMQVersionEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(WebsphereMQVersionEnum.class);
    }
}
