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

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.sql.SqlConstants;

/**
 * @author MAX
 *
 */
public class SimpleCriteria extends AbstractCriteria {
    
    protected final Column column;
    protected final Object comparisonValue;
    protected ComparisonTypeEnum comparisonType;
    
    /**
     * @param comparisonType
     * @param column
     * @param comparisonValues[]
     */
    public SimpleCriteria(ComparisonTypeEnum comparisonType, Column column, Object[] comparisonValues) {
        Object value = null;
        if (comparisonValues == null || (comparisonValues.length == 1 && comparisonValues[0] == null)) {
            if (comparisonType.equals(ComparisonTypeEnum.EQUALS)) {
                comparisonType = ComparisonTypeEnum.IS_NULL;
            }
            else if (comparisonType.equals(ComparisonTypeEnum.NOT_EQUALS)) {
                comparisonType = ComparisonTypeEnum.IS_NOT_NULL;
            }
            value = SqlConstants.NULL;
        }
        else {
            int len = comparisonValues.length;
            StringBuffer values = new StringBuffer(8 * len);
            for (int i = 0; i < len; i++) {
                values.append(escapeSql(comparisonValues[i]));
                if (i != (len - 1)) {
                    values.append(StringConstants.COMMA);
                }
            }
            value = values.toString();
        }
        
        this.column = column;
        this.comparisonValue = value;
        this.comparisonType = comparisonType;
    }

    /**
     * @param comparisonType
     * @param column
     * @param comparisonValue
     */
    public SimpleCriteria(ComparisonTypeEnum comparisonType, Column column, Object comparisonValue) {
        this(comparisonType, column, new Object[]{comparisonValue});
    }
    
    /**
     * @param o
     * @return
     */
    protected String escapeSql(Object o) {
        // NULL is a keyword in SQL.
        if (o == null){
            return SqlConstants.NULL;
        }
        
        // All strings and dates must be enclosed in single quotes.
        if (o instanceof String) {
            String s = (String) o;
            if(StringUtils.isBlank(s)){
                return SqlConstants.NULL;
            }
            
            // Escape list of values.
            StringBuffer buf = new StringBuffer(s.length() + 6);
            buf.append(StringConstants.SINGLE_QUOTE);
            buf.append(StringUtils.replace(s, StringConstants.SINGLE_QUOTE, SqlConstants.DOUBLE_QUOTE));
            buf.append(StringConstants.SINGLE_QUOTE);
            return buf.toString();
        }
        else{
            // Case of a number, no quotes needed.
            return String.valueOf(o);
        }
    }
    
    /**
     * @return Returns the comparisonType.
     */
    public ComparisonTypeEnum getComparisonType() {
        return comparisonType;
    }

    /**
     * @return Returns the column.
     */
    public Column getColumn() {
        return column;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.sql.criteria.Criteria#getSql()
     */
    public String getSql() {
        String literal = String.valueOf(comparisonValue);
        if(ComparisonTypeEnum.IN.equals(comparisonType) || ComparisonTypeEnum.NOT_IN.equals(comparisonType)){
            literal = StringConstants.LP + literal + StringConstants.RP;
        }

        // Generate the sql clause.
        return column.getFullName() + comparisonType.getName() + literal;
    }
}
