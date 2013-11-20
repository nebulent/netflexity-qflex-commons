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
package org.netflexity.api.util.jdbc;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author FEDORMAX
 *
 * All rows contained in CachedRowSet can be represented as classes of this type.
 */
public interface RowSetBean extends Serializable{
    /**
     * Implement this method by populating a bean with all the necessary values.
     * @param rs
     * @throws SQLException
     */
    public void populate(ResultSet rs) throws SQLException;
}
