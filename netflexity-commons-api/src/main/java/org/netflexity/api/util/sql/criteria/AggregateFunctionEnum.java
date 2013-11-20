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
 * @author MAX
 *
 */
public class AggregateFunctionEnum extends Enum {

    public static AggregateFunctionEnum SUM = new AggregateFunctionEnum("sum");
    public static AggregateFunctionEnum COUNT = new AggregateFunctionEnum("count");
    public static AggregateFunctionEnum MINIMUM = new AggregateFunctionEnum("min");
    public static AggregateFunctionEnum MAXIMUM = new AggregateFunctionEnum("max");
    public static AggregateFunctionEnum AVERAGE = new AggregateFunctionEnum("avg");
    
    /**
     * @param arg0
     */
    public AggregateFunctionEnum(String func) {
        super(func);
    }

    /**
     * @param code
     * @return
     */
    public static AggregateFunctionEnum getEnum(String code) {
        return (AggregateFunctionEnum) getEnum(AggregateFunctionEnum.class, code);
    }

    /**
     * @return
     */
    public static Map getEnumMap() {
        return getEnumMap(AggregateFunctionEnum.class);
    }

    /**
     * @return
     */
    public static List getEnumList() {
        return getEnumList(AggregateFunctionEnum.class);
    }

    /**
     * @return
     */
    public static Iterator iterator() {
        return iterator(AggregateFunctionEnum.class);
    }
}
