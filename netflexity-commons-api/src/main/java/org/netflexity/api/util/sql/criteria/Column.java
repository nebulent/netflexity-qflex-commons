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
 * Represents a column of a table.
 * 
 */
public class Column {

    private final Table table;
    private final String name;
    private boolean primaryKey;
    
    /**
     * @param table
     * @param name
     */
    public Column(Table table, String name) {
        this(table, name, false);
    }
    
    /**
     * @param table
     * @param name
     * @param primaryKey
     */
    public Column(Table table, String name, boolean primaryKey) {
        this.table = table;
        this.name = name;
        this.primaryKey = primaryKey;
    }
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return Returns the name.
     */
    public String getFullName() {
        return table.getName() + StringConstants.DOT + name;
    }
    
    /**
     * @return Returns the table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return Returns the primaryKey.
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey The primaryKey to set.
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return toString().hashCode();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        return toString().equals(obj.toString());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getFullName();
    }
}
