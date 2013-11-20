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
package org.netflexity.api.mb.ibm.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration for all supported snapshot node level.
 * 
 * @author MAX
 * 
 */
public final class SnapshotNodeDataLevelEnum extends Enum {

    public static final SnapshotNodeDataLevelEnum NONE = new SnapshotNodeDataLevelEnum("none");
    public static final SnapshotNodeDataLevelEnum BASIC = new SnapshotNodeDataLevelEnum("basic");
    public static final SnapshotNodeDataLevelEnum ADVANCED = new SnapshotNodeDataLevelEnum("advanced");
    
    /**
     * @param arg0
     */
    public SnapshotNodeDataLevelEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static SnapshotNodeDataLevelEnum getEnum(String type) {
        return (SnapshotNodeDataLevelEnum) getEnum(SnapshotNodeDataLevelEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(SnapshotNodeDataLevelEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(SnapshotNodeDataLevelEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(SnapshotNodeDataLevelEnum.class);
    }
}
