package org.netflexity.api.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author Max Fedorov
 *
 * Row handler that does not do anything.
 */
public class DefaultRowCallbackHandler implements RowCallbackHandler {
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.RowCallbackHandler#processRows(java.sql.ResultSet)
     */
    public List processRows(ResultSet rs)throws SQLException {
        return Collections.EMPTY_LIST;
    }
}
