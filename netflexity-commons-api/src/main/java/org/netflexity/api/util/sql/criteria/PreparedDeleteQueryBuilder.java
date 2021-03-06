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

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 *
 */
public class PreparedDeleteQueryBuilder extends AbstractQuery{

    private Table table;
    
    /**
     * @param table
     */
    public PreparedDeleteQueryBuilder(Table table){
        this.table = table;
    }
    
    /**
     * @param table
     * @param primaryKeyColumns
     */
    public PreparedDeleteQueryBuilder(Table table, List primaryKeyColumns){
        this.columns = primaryKeyColumns;
        this.table = table;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getQuery()
     */
    public String getQuery() {
        StringBuffer sql = new StringBuffer(128);
        sql.append(SqlConstants.DELETE).append(SqlConstants.FROM).append(table.getName());
        
        // Set primary key fields.
        sql.append(SqlConstants.WHERE);
        
        // Create a list of table columns to change
        boolean first = true;
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if(column.isPrimaryKey()){
                if (!first) {
                    sql.append(SqlConstants.AND);
                }
                else{
                    first = false;
                }
                sql.append(column.getName()).append(StringConstants.EQ).append(StringConstants.QST);
            }
        }
        
        // Add criteria if exists.
        String criteriaSql = createCriteria();
        if(StringUtils.isNotBlank(criteriaSql)){
            if(!columns.isEmpty()){
                sql.append(SqlConstants.AND);
            }
            sql.append(criteriaSql);
        }
        return sql.toString();
    }
}
