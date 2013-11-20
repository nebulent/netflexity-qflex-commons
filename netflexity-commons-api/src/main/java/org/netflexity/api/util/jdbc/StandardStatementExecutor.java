package org.netflexity.api.util.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Max Fedorov
 *
 * This class must be extended to execute any SQL statement
 * against database. Only processStatement() method must be implemented.
 * 
 */
public class StandardStatementExecutor extends AbstractStatementExecutor {

    private static Logger logger = LoggerFactory.getLogger(StandardStatementExecutor.class.getPackage().getName());
    
	/**
	 * @param sql
	 * @param timeout
	 * @param maxRows
	 * @param isolation
	 */
	public StandardStatementExecutor(String sql, int timeout, int maxRows, int isolation) {
		super(sql, timeout, maxRows, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 * @param isolation
	 */
	public StandardStatementExecutor(String sql, int timeout, int isolation) {
		super(sql, timeout, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 */
	public StandardStatementExecutor(String sql, int timeout) {
		super(sql, timeout);
	}

	/**
	 * @param sql
	 */
	public StandardStatementExecutor(String sql) {
		super(sql);
	}

    /**
     * @param sql
     * @param rowCallbackHandler
     */
    public StandardStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler) {
        super(sql, rowCallbackHandler);
    }
    
    /**
     * @param sql
     * @param rowCallbackHandler
     * @param statementCallbackHandler
     */
    public StandardStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler,
                    StatementCallbackHandler statementCallbackHandler) {
        super(sql, rowCallbackHandler, statementCallbackHandler);
    }
    
    /**
     * @param sql
     * @param statementCallbackHandler
     */
    public StandardStatementExecutor(String sql, StatementCallbackHandler statementCallbackHandler) {
        super(sql, statementCallbackHandler);
    }
    
	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#executeQuery(java.sql.Connection)
	 */
	public final List executeQuery(Connection conn) throws SQLException {
		Statement st = null;
        ResultSet rs = null;
        List results = Collections.EMPTY_LIST;
        try {
            // Create statement.
            st = createStatement(conn);

            // Process Statement.
            if(statementCallbackHandler != null){
                statementCallbackHandler.processStatement(st);
            }
            
            // Execute sql and process results.
            rs = st.executeQuery(sql);
            
            // Process ResultSet.
            if(rowCallbackHandler != null){
                results = rowCallbackHandler.processRows(rs);
            }
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new SQLException(e.getMessage() + "\nSQL Failed: " + sql);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException e) {
                    logger.error("Failed to close ResultSet : ", e);
                }
            }
            if (st != null) {
                try {
                    st.close();
                }
                catch (SQLException e) {
                    logger.error("Failed to close Statement : ", e);
                }
            }
        }
        return results;
    }

	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#execute(java.sql.Connection)
	 */
	public final int execute(Connection conn) throws SQLException {
		Statement st = null;
        int result = 0;
        try {
            // Create statement.
            st = createStatement(conn);

            // Process Statement.
            if(statementCallbackHandler != null){
                statementCallbackHandler.processStatement(st);
            }
            
            // Execute sql.
            result = st.executeUpdate(sql);
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new SQLException(e.getMessage() + "\nSQL Failed: " + sql);
        }
        finally {
            if (st != null) {
                try {
                    st.close();
                }
                catch (SQLException e) {
                    logger.error("Failed to close Statement : ", e);
                }
            }
        }
        return result;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#createStatement(java.sql.Connection)
	 */
	public Statement createStatement(Connection conn) throws SQLException {
		Statement st = conn.createStatement();
        
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
