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

import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class CountColumn extends AggregateColumn {

    /**
     * @param table
     * @param name
     */
    public CountColumn(Table table, String name) {
        super(AggregateFunctionEnum.COUNT, table, name);
    }
    
    /**
     * @param column
     */
    public CountColumn(Column column) {
        super(AggregateFunctionEnum.COUNT, column.getTable(), column.getName());
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.AggregateColumn#getFullName()
     */
    public String getFullName() {
        if(StringConstants.STAR.equals(getName())){
            return aggregateFunction.getName() + StringConstants.LP + StringConstants.STAR + StringConstants.RP;
        }
        else{
            return super.getFullName();
        }
    }
}
