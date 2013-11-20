package org.netflexity.api.util.jdbc;

import java.sql.CallableStatement;
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
 * This class must be extended to execute any stored procedure. 
 * Only processStatement() method must be implemented.
 * 
 */
public class CallableStatementExecutor extends AbstractStatementExecutor {

    private static Logger logger = LoggerFactory.getLogger(CallableStatementExecutor.class.getPackage().getName());
    
	/**
	 * @param sql
	 * @param timeout
	 * @param maxRows
	 * @param isolation
	 */
	public CallableStatementExecutor(String sql, int timeout, int maxRows, int isolation) {
		super(sql, timeout, maxRows, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 * @param isolation
	 */
	public CallableStatementExecutor(String sql, int timeout, int isolation) {
		super(sql, timeout, isolation);
	}

	/**
	 * @param sql
	 * @param timeout
	 */
	public CallableStatementExecutor(String sql, int timeout) {
		super(sql, timeout);
	}

	/**
	 * @param sql
	 */
	public CallableStatementExecutor(String sql) {
		super(sql);
	}

    /**
     * @param sql
     * @param rowCallbackHandler
     */
    public CallableStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler) {
        super(sql, rowCallbackHandler);
    }
    
    /**
     * @param sql
     * @param rowCallbackHandler
     * @param statementCallbackHandler
     */
    public CallableStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler,
                    StatementCallbackHandler statementCallbackHandler) {
        super(sql, rowCallbackHandler, statementCallbackHandler);
    }
    
    /**
     * @param sql
     * @param statementCallbackHandler
     */
    public CallableStatementExecutor(String sql, StatementCallbackHandler statementCallbackHandler) {
        super(sql, statementCallbackHandler);
    }
    
	/* (non-Javadoc)
	 * @see org.netflexity.dorm.executors.IStatementExecutor#executeQuery(java.sql.Connection)
	 */
	public final List executeQuery(Connection conn) throws SQLException {
		CallableStatement st = null;
        ResultSet rs = null;
        List results = Collections.EMPTY_LIST;
        try {
            // Create statement.
            st = (CallableStatement)createStatement(conn);

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
		CallableStatement st = null;
        int result = 0;
        try {
            // Create statement.
            st = (CallableStatement)createStatement(conn);

            // Process Statement.
            if(statementCallbackHandler != null){
                statementCallbackHandler.processStatement(st);
            }
            
            // Execute sql.
            result = st.executeUpdate();
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
		return conn.prepareCall(sql);
	}
}
