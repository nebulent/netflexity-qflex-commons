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

/**
 * @author MAX
 *
 */
public class MinimumColumn extends AggregateColumn {

    /**
     * @param table
     * @param name
     */
    public MinimumColumn(Table table, String name) {
        super(AggregateFunctionEnum.MINIMUM, table, name);
    }
    
    /**
     * @param column
     */
    public MinimumColumn(Column column) {
        super(AggregateFunctionEnum.MINIMUM, column.getTable(), column.getName());
    }
}
