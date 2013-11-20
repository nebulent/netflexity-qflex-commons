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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 *
 */
public class PreparedUpdateQueryBuilder extends AbstractQuery{

    private Table table;
    
    /**
     * @param table
     * @param columns
     */
    public PreparedUpdateQueryBuilder(Table table, List columns){
        this.columns = columns;
        this.table = table;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Query#getQuery()
     */
    public String getQuery() {
        List primaryKeyColumns = new ArrayList(2);
        
        StringBuffer sql = new StringBuffer(128);
        sql.append(SqlConstants.UPDATE).append(table.getName());
        
        // Create a list of table columns to change.
        boolean first = true;
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if(!column.isPrimaryKey()){
                if (first) {
                    sql.append(SqlConstants.SET);
                    first = false;
                }
                else{
                    sql.append(StringConstants.COMMA);
                }
                sql.append(column.getName()).append(StringConstants.EQ).append(StringConstants.QST);
            }
            else{
                primaryKeyColumns.add(column);
            }
        }
        
        // Set primary key fields.
        sql.append(SqlConstants.WHERE);
        for (Iterator iter = primaryKeyColumns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            sql.append(column.getName()).append(StringConstants.EQ).append(StringConstants.QST);
            if(iter.hasNext()){
                sql.append(SqlConstants.AND);
            }
        }
        
        // Add criteria if exists.
        String criteriaSql = createCriteria();
        if(StringUtils.isNotBlank(criteriaSql)){
            if(!primaryKeyColumns.isEmpty()){
                sql.append(SqlConstants.AND);
            }
            sql.append(criteriaSql);
        }
        return sql.toString();
    }
}
