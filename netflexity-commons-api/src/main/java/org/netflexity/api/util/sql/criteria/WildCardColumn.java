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
 * Column that helps select all columns of a table - (*)
 */
public class WildCardColumn extends Column {
    
    /**
     * @param table
     */
    public WildCardColumn(Table table){
        super(table, StringConstants.STAR);
    }
}
