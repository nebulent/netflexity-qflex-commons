package org.netflexity.api.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Max Fedorov
 *
 * Row handler that creates a list of beans based on ResultSet.
 */
public class CachedRowCallbackHandler implements RowCallbackHandler {
    
    private Class rowSetBeanClass;
    
    /**
     * @param rowSetBeanClass
     */
    public CachedRowCallbackHandler(Class rowSetBeanClass) {
        this.rowSetBeanClass = rowSetBeanClass;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowCallbackHandler#processRows(java.sql.ResultSet)
     */
    public List processRows(ResultSet rs)throws SQLException {
        return DefaultRowSetBeanPopulator.DEFAULT_BEAN_POPULATOR.populate(rowSetBeanClass, rs);
    }
}
