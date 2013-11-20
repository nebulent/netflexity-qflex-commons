package org.netflexity.api.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

/**
 * @author Max Fedorov
 *
 * This class must be extended to execute any prepared statement
 * against database. Only processStatement() method must be implemented.
 * 
 */
public class PreparedStatementExecutor extends AbstractStatementExecutor {
    
	/**
	 * @param sql
	 * @param timeout
	 * @param maxRows
	 * @param isolation
	 */
	public PreparedStatementExecutor(String sql, int timeout, int maxRows, int isolation) {
		super(sql, timeout, maxRows, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 * @param isolation
	 */
	public PreparedStatementExecutor(String sql, int timeout, int isolation) {
		super(sql, timeout, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 */
	public PreparedStatementExecutor(String sql, int timeout) {
		super(sql, timeout);
	}

	/**
	 * @param sql
	 */
	public PreparedStatementExecutor(String sql) {
		super(sql);
	}
	
    /**
     * @param sql
     * @param rowCallbackHandler
     */
    public PreparedStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler) {
        super(sql, rowCallbackHandler);
    }
    
    /**
     * @param sql
     * @param rowCallbackHandler
     * @param statementCallbackHandler
     */
    public PreparedStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler,
                    StatementCallbackHandler statementCallbackHandler) {
        super(sql, rowCallbackHandler, statementCallbackHandler);
    }
    /**
     * @param sql
     * @param statementCallbackHandler
     */
    public PreparedStatementExecutor(String sql, StatementCallbackHandler statementCallbackHandler) {
        super(sql, statementCallbackHandler);
    }
    
	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#executeQuery(java.sql.Connection)
	 */
	public final List executeQuery(Connection conn) throws SQLException {
		PreparedStatement st = null;
        ResultSet rs = null;
        List results = Collections.EMPTY_LIST;
        try {
            // Create statement.
            st = (PreparedStatement)createStatement(conn);
            
            // Process Statement.
            if(statementCallbackHandler != null){
                statementCallbackHandler.processStatement(st);
            }
            
            // Execute sql and process results.
            rs = st.executeQuery();
            
            // Process ResultSet.
            if(rowCallbackHandler != null){
                results = rowCallbackHandler.processRows(rs);
            }
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#execute(java.sql.Connection)
	 */
	public final int execute(Connection conn) throws SQLException {
		PreparedStatement st = null;
        int result = 0;
        try {
            // Create statement.
            st = (PreparedStatement)createStatement(conn);

            // Process Statement.
            if(statementCallbackHandler != null){
                statementCallbackHandler.processStatement(st);
            }
            
            // Execute sql.
            result = st.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            if (st != null) {
                try {
                    st.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#createStatement(java.sql.Connection)
	 */
	public Statement createStatement(Connection conn) throws SQLException {
        Statement st = conn.prepareStatement(sql);
        
        // Set max rows.
        if(getMaxRows() > 0){
            st.setMaxRows(getMaxRows());
        }
        
        // Set query execution timeout.
        if(getTimeout() > 0){
            st.setQueryTimeout(getTimeout());
        }
        
        // Set isolation level.
        if(getIsolation() > 0){
            conn.setTransactionIsolation(getIsolation());
        }
        
        return st;
	}
}
