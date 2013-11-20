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

public interface SelectQuery extends Query{

    /**
     * @return Returns the groupByColumns.
     */
    public Iterator getGroupByColumns();

    /**
     * @param groupByColumn The groupByColumn to add.
     * @return instance of builder for convenience.
     */
    public SelectQuery addGroupByColumn(Column groupByColumn);

    /**
     * @return Returns the havingColumns.
     */
    public Iterator getHavingColumns();

    /**
     * @param havingColumns The havingColumns to add.
     * @return instance of builder for convenience.
     */
    public SelectQuery addHavingColumn(Column havingColumn);

    /**
     * @return Returns the orderByColumns.
     */
    public Iterator getOrderByColumns();

    /**
     * @param orderByColumns The orderByColumns to add.
     * @return instance of builder for convenience.
     */
    public SelectQuery addOrderByColumn(Column orderByColumn);
    
    /**
     * @return Returns the orderByDesc.
     */
    public boolean isOrderByDesc();

    /**
     * @param orderByDesc The orderByDesc to set.
     */
    public void setOrderByDesc(boolean orderByDesc);
}