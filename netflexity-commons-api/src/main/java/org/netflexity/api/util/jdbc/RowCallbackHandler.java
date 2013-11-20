package org.netflexity.api.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Max Fedorov
 * 
 * Implementations of this interface perform the actual work of 
 * retreiving query results.
 */
public interface RowCallbackHandler {
	
	/**
	 * @param rs
	 * @return List of results, representing result set.
	 * @throws SQLException
	 */
    public List processRows(ResultSet rs) throws SQLException;
}
