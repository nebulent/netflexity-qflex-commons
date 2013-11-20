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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author FEDORMAX
 *
 * DefaultRowSetBeanPopulator
 */
public class DefaultRowSetBeanPopulator implements RowSetBeanPopulator {

    private static Logger logger = LoggerFactory.getLogger(DefaultRowSetBeanPopulator.class.getPackage().getName());
    
    public static final RowSetBeanPopulator DEFAULT_BEAN_POPULATOR = new DefaultRowSetBeanPopulator();
    
    private DefaultRowSetBeanPopulator(){}
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowSetBeanPopulator#populate(java.lang.Class, javax.sql.rowset.CachedRowSet)
     */
    public List populate(Class beanClazz, ResultSet rs) throws SQLException {
        if(beanClazz.isAssignableFrom(RowSetBean.class)){
            throw new SQLException("Supplied bean " + beanClazz.getName() + " is not a type of " + RowSetBean.class.getName());
        }
        List beans = new ArrayList();
        while(rs.next()){
            try {
                RowSetBean bean = (RowSetBean) beanClazz.newInstance();
                bean.populate(rs);
                beans.add(bean);
            } catch (InstantiationException e) {
                logger.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return beans;
    }

}
