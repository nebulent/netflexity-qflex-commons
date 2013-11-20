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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author FEDORMAX
 *
 * Class of this type will know how to create and populate beans
 */
public interface RowSetBeanPopulator {
    /**
     * This method will create and populate beans of specified class
     * and populate them with contents of RowSet.
     * 
     * @param beanClazz
     * @param rs
     * @throws SQLException
     */
    public List populate(Class beanClazz, ResultSet rs) throws SQLException;
}
