package org.netflexity.api.util.jdbc;

import java.sql.SQLException;
import java.util.List;

/**
 * @author MAX
 *
 */
public interface BatchStatementExecutor {
    
    /**
     * @param statementCallback
     * @return index of the batch executed.
     * @throws SQLException
     */
    public int addBatch(StatementCallbackHandler statementCallback) throws SQLException;
    
    /**
     * This method <b>MUST</b> be called at the very end of batch processing
     * to finalize resources.
     * 
     * @throws SQLException
     */
    public void close() throws SQLException;
    
    /**
     * @return a list of batch execution results (list of lists).
     */
    public List getResults();
    
    /**
     * @return
     */
    public String getDescription();
}