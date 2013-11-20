package org.netflexity.api.util.jdbc;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;

/**
 * @author Max Fedorov
 *
 * This class defines common functionality for all JDBC query executors.
 */
public abstract class AbstractStatementExecutor implements StatementExecutor {
	
	public static final RowCallbackHandler DEFAULT_ROW_CALLBACK_HANDLER = new DefaultRowCallbackHandler();
	public static final StatementCallbackHandler DEFAULT_STATEMENT_CALLBACK_HANDLER = new DefaultStatementCallbackHandler();
	
	protected String sql;
    protected int timeout;
    protected int maxRows;
    protected int isolation;
    protected RowCallbackHandler rowCallbackHandler = DEFAULT_ROW_CALLBACK_HANDLER;
    protected StatementCallbackHandler statementCallbackHandler = DEFAULT_STATEMENT_CALLBACK_HANDLER;
    
    /**
     * @param sql
     * @param timeout
     * @param maxRows
     * @param isolation
     */
    public AbstractStatementExecutor(String sql, int timeout, int maxRows, int isolation) {
        this.sql = sql;
        this.timeout = timeout;
        this.maxRows = maxRows;
        this.isolation = isolation;
    }
    
    /**
     * @param sql
     * @param timeout
     * @param isolation
     */
    public AbstractStatementExecutor(String sql, int timeout, int isolation) {
        this(sql, timeout, 0, isolation);
    }
    
    /**
     * @param sql
     * @param timeout
     */
    public AbstractStatementExecutor(String sql, int timeout) {
        this(sql, timeout, 0, 0);
    }
    
    /**
     * @param sql
     */
    public AbstractStatementExecutor(String sql) {
        this(sql, 0, 0, 0);
    }
    
    /**
     * @param sql
     * @param rowCallbackHandler
     * @param statementCallbackHandler
     * @param rowSetBeanPopulator
     */
    public AbstractStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler, StatementCallbackHandler statementCallbackHandler) {
        this(sql);
        if(rowCallbackHandler != null)this.rowCallbackHandler = rowCallbackHandler;
        if(statementCallbackHandler != null)this.statementCallbackHandler = statementCallbackHandler;
    }
    
    /**
     * @param sql
     * @param rowCallbackHandler
     */
    public AbstractStatementExecutor(String sql, RowCallbackHandler rowCallbackHandler) {
        this(sql, rowCallbackHandler, null);
    }
    
    /**
     * @param sql
     * @param statementCallbackHandler
     */
    public AbstractStatementExecutor(String sql, StatementCallbackHandler statementCallbackHandler) {
        this(sql, null, statementCallbackHandler);
    }
    
	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#getIsolation()
	 */
	public int getIsolation() {
		return isolation;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#getMaxRows()
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#getSql()
	 */
	public String getSql() {
		return sql;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#getTimeout()
	 */
	public int getTimeout() {
		return timeout;
	}

	/* (non-Javadoc)
	 * @see org.netflexity.api.util.jdbc.StatementExecutor#setIsolation(int)
	 */
	public void setIsolation(int isolation) {
        this.isolation = isolation;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#setMaxRows(int)
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#setTimeout(int)
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#getRowCallbackHandler()
     */
    public RowCallbackHandler getRowCallbackHandler() {
        return rowCallbackHandler;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#setRowCallbackHandler(org.netflexity.api.util.jdbc.RowCallbackHandler)
     */
    public void setRowCallbackHandler(RowCallbackHandler rowCallbackHandler) {
        this.rowCallbackHandler = rowCallbackHandler;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#getStatementCallbackHandler()
     */
    public StatementCallbackHandler getStatementCallbackHandler() {
        return statementCallbackHandler;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#setStatementCallbackHandler(org.netflexity.api.util.jdbc.StatementCallbackHandler)
     */
    public void setStatementCallbackHandler(StatementCallbackHandler statementCallbackHandler) {
        this.statementCallbackHandler = statementCallbackHandler;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.StatementExecutor#getDescription()
     */
    public String getDescription() {
        return getSql() + StringConstants.SPACE + ((statementCallbackHandler != null) ? statementCallbackHandler.getParameters().toString() : StringUtils.EMPTY);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getDescription();
    }
}
