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
public class SumColumn extends AggregateColumn {

    /**
     * @param table
     * @param name
     */
    public SumColumn(Table table, String name) {
        super(AggregateFunctionEnum.SUM, table, name);
    }
    
    /**
     * @param column
     */
    public SumColumn(Column column) {
        super(AggregateFunctionEnum.SUM, column.getTable(), column.getName());
    }
}
