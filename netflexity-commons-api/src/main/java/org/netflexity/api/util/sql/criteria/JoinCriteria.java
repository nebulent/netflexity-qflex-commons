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
public class JoinCriteria extends AbstractCriteria {

    private Column sourceColumn;
    private Column destColumn;

    /**
     * @param sourceColumn
     * @param destColumn
     */
    public JoinCriteria(Column sourceColumn, Column destColumn) {
        this.sourceColumn = sourceColumn;
        this.destColumn = destColumn;
    }
    
    /**
     * @return Returns the destColumn.
     */
    public Column getDestColumn() {
        return destColumn;
    }

    /**
     * @return Returns the sourceColumn.
     */
    public Column getSourceColumn() {
        return sourceColumn;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Criteria#getSql()
     */
    public String getSql() {
        return sourceColumn.getFullName() + StringConstants.EQ + destColumn.getFullName();
    }
}
