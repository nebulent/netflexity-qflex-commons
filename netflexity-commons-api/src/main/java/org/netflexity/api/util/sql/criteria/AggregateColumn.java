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
public class AggregateColumn extends Column {

    protected final AggregateFunctionEnum aggregateFunction;
    
    /**
     * @param aggregateFunction
     * @param column
     */
    public AggregateColumn(AggregateFunctionEnum aggregateFunction, Table table, String name) {
        super(table, name);
        this.aggregateFunction = aggregateFunction;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Column#getFullName()
     */
    public String getFullName() {
        return aggregateFunction.getName() + StringConstants.LP + super.getFullName() + StringConstants.RP;
    }
}
