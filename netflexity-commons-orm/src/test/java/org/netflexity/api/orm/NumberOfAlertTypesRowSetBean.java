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
package org.netflexity.api.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.netflexity.api.util.jdbc.RowSetBean;

/**
 * @author FEDORMAX
 *
 * NumberOfAlertTypesRowSetBean
 */
public class NumberOfAlertTypesRowSetBean implements RowSetBean {

    int numberOfAlertTypes;
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowSetBean#populate(javax.sql.rowset.CachedRowSet)
     */
    public void populate(ResultSet rs) throws SQLException {
        numberOfAlertTypes = rs.getInt(1);
    }

    public int getNumberOfAlertTypes() {
        return numberOfAlertTypes;
    }
    public void setNumberOfAlertTypes(int numberOfAlertTypes) {
        this.numberOfAlertTypes = numberOfAlertTypes;
    }
}
