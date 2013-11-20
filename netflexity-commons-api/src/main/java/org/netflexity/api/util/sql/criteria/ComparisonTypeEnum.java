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
 * Enumeration of all the sql operations.
 * 
 * @author MAX
 */
public class ComparisonTypeEnum extends Enum {

    public static final ComparisonTypeEnum IS_NULL = new ComparisonTypeEnum(" IS ");
    public static final ComparisonTypeEnum IS_NOT_NULL = new ComparisonTypeEnum(" IS NOT ");
    public static final ComparisonTypeEnum EQUALS = new ComparisonTypeEnum(" = ");
    public static final ComparisonTypeEnum NOT_EQUALS = new ComparisonTypeEnum(" != ");
    public static final ComparisonTypeEnum LESS_THAN = new ComparisonTypeEnum(" < ");
    public static final ComparisonTypeEnum LESS_THAN_EQUALS = new ComparisonTypeEnum(" <= ");
    public static final ComparisonTypeEnum GREATER_THAN = new ComparisonTypeEnum(" > ");
    public static final ComparisonTypeEnum GREATER_THAN_EQUALS = new ComparisonTypeEnum(" >= ");
    public static final ComparisonTypeEnum LIKE = new ComparisonTypeEnum(" LIKE ");
    public static final ComparisonTypeEnum NOT_LIKE = new ComparisonTypeEnum(" NOT LIKE ");
    public static final ComparisonTypeEnum IN = new ComparisonTypeEnum(" IN ");
    public static final ComparisonTypeEnum NOT_IN = new ComparisonTypeEnum(" NOT IN ");
    
    /**
     * @param type
     */
    private ComparisonTypeEnum(String type) {
        super(type);
    }

    /**
     * @param type
     * @return
     */
    public static ComparisonTypeEnum getEnum(String type) {
        return (ComparisonTypeEnum) getEnum(ComparisonTypeEnum.class, type);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(ComparisonTypeEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(ComparisonTypeEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(ComparisonTypeEnum.class);
    }
}
