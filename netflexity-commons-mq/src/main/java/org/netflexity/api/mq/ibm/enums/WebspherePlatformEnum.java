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
public final class WebspherePlatformEnum extends Enum {

    public static final WebspherePlatformEnum AIX = new WebspherePlatformEnum("AIX");
    public static final WebspherePlatformEnum COMPAQ_OPENVMS = new WebspherePlatformEnum("Compaq OpenVMS");
    public static final WebspherePlatformEnum COMPAQ_NSK = new WebspherePlatformEnum("Compaq NSK");
    public static final WebspherePlatformEnum HP_UX = new WebspherePlatformEnum("HP-UX");
    public static final WebspherePlatformEnum LINUX = new WebspherePlatformEnum("Linux");
    public static final WebspherePlatformEnum LINUX_ZOS = new WebspherePlatformEnum("Linux for Z/OS");
    public static final WebspherePlatformEnum OS400 = new WebspherePlatformEnum("OS/400");
    public static final WebspherePlatformEnum OS2_WARP = new WebspherePlatformEnum("OS/2 Warp");
    public static final WebspherePlatformEnum SOLARIS = new WebspherePlatformEnum("Solaris");
    public static final WebspherePlatformEnum WINDOWS = new WebspherePlatformEnum("Windows");
    public static final WebspherePlatformEnum ZOS = new WebspherePlatformEnum("Z/OS");

    /**
     * @param arg0
     */
    public WebspherePlatformEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static WebspherePlatformEnum getEnum(String type) {
        return (WebspherePlatformEnum) getEnum(WebspherePlatformEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(WebspherePlatformEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(WebspherePlatformEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(WebspherePlatformEnum.class);
    }
}
