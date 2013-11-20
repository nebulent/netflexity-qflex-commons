package org.netflexity.api.util.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Max Fedorov
 *
 * Implementations of this interface should implement all methods
 * that inroduce execution specifics of a particular Executor.
 */
public interface StatementExecutor {
	
	/**
	 * Execute any select sql statement.
	 * 
	 * @param conn
	 * @return List of records.
	 * @throws SQLException
	 */
	public List executeQuery(Connection conn) throws SQLException;

	/**
	 * Execute any action sql statement.
	 * 
	 * @param conn
	 * @return rows affected
	 * @throws SQLException
	 */
	public int execute(Connection conn) throws SQLException;
	
	/**
	 * Create statement, setting any necessary attributes.
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Statement createStatement(Connection conn) throws SQLException;
	
	/**
	 * @return Returns the isolation.
	 */
	public int getIsolation();

	/**
	 * @return Returns the maxRows.
	 */
	public int getMaxRows();

	/**
	 * @return Returns the sql.
	 */
	public String getSql();

    /**
     * @param isolation
     */
    public void setIsolation(int isolation);
    
    /**
     * @param maxRows
     */
    public void setMaxRows(int maxRows);
    
    /**
     * @param timeout
     */
    public void setTimeout(int timeout);
    
	/**
	 * @return Returns the timeout.
	 */
	public int getTimeout();
	
	 /**
     * @return
     */
    public RowCallbackHandler getRowCallbackHandler();
    
    /**
     * @param rowCallbackHandler
     */
    public void setRowCallbackHandler(RowCallbackHandler rowCallbackHandler);
    
    /**
     * @return Returns the statementCallbackHandler.
     */
    public StatementCallbackHandler getStatementCallbackHandler();
    
    /**
     * @param statementCallbackHandler The statementCallbackHandler to set.
     */
    public void setStatementCallbackHandler(StatementCallbackHandler statementCallbackHandler);
    
    /**
     * @return
     */
    public String getDescription();
}