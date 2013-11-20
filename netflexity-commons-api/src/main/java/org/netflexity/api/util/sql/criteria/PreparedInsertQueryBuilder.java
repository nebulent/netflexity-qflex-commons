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

import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 *
 */
public class PreparedInsertQueryBuilder extends AbstractQuery{

    private Table table;
    
    /**
     * @param table
     * @param columns
     */
    public PreparedInsertQueryBuilder(Table table, List columns){
        this.columns = columns;
        this.table = table;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getQuery()
     */
    public String getQuery() {
        StringBuffer sql = new StringBuffer(128);
        sql.append(SqlConstants.INSERT_INTO).append(table.getName()).append(StringConstants.LP);
        
        // Create a list of table columns.
        boolean first = true;
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (!first) {
                sql.append(StringConstants.COMMA);
            }
            else{
                first = false;
            }
            sql.append(column.getName());
        }
        
        sql.append(StringConstants.RP).append(SqlConstants.VALUES).append(StringConstants.LP);
        
        first = true;
        for (Iterator iter = columns.iterator(); iter.hasNext(); iter.next()) {
            if (!first) {
                sql.append(StringConstants.COMMA);
            }
            else{
                first = false;
            }
            sql.append(StringConstants.QST);
        }
        
        sql.append(StringConstants.RP);
        
        return sql.toString();
    }
}
