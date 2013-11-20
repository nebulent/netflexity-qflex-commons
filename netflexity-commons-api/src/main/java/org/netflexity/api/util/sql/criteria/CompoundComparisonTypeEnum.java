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
package org.netflexity.api.util.sql.criteria;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Enumeration of all the compound comparison types.
 * 
 * @author MAX
 */
public class CompoundComparisonTypeEnum extends Enum {

    public static final CompoundComparisonTypeEnum OR = new CompoundComparisonTypeEnum(" OR ");
    public static final CompoundComparisonTypeEnum AND = new CompoundComparisonTypeEnum(" AND ");
    
    /**
     * @param type
     */
    private CompoundComparisonTypeEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static CompoundComparisonTypeEnum getEnum(String type) {
        return (CompoundComparisonTypeEnum) getEnum(CompoundComparisonTypeEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(CompoundComparisonTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(CompoundComparisonTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(CompoundComparisonTypeEnum.class);
    }
}
