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

/**
 * @author MAX
 * 
 * Any sql query.
 */
public interface Query {
    
    /**
     * @param column
     * @return instance of builder for convenience.
     */
    public Query addColumn(Column column);

    /**
     * @param criterion
     * @return instance of builder for convenience.
     */
    public Query addCriteria(Criteria criterion);
    
    /**
     * @return Returns the columns.
     */
    public Iterator getColumns();

    /**
     * @return Returns the criteria.
     */
    public Iterator getCriteria();
    
    /**
     * @return sql query
     */
    public String getQuery();
}
