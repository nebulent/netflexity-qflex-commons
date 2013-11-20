package org.netflexity.api.orm;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Max Fedorov
 *
 * Interface that dictates the functionality to retrieve 
 * Java values from JDBC ResultSet.
 */
public interface FieldMapper{
    /**
     * Supposedly this is an optimized version.
     * 
     * @param rs
     * @param index
     * @return
     * @throws SQLException
     */
    Object getValueFromResultSet(ResultSet rs, int jdbcType, int fieldIndex) throws SQLException;
    
    /**
     * @param rs
     * @param field
     * @return
     * @throws SQLException
     */
    Object getValueFromResultSet(ResultSet rs, int jdbcType, String fieldName) throws SQLException;
}
