package org.netflexity.api.util.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Filename:      PreparedBatchStatementExecutor.java
 * Created by:    c1mxf02
 * Creation Date: Fri Feb 18 10:32:19 2005
 * 
 * %version: 5 %
 * 
 * Description:
 *
 */
public class PreparedBatchStatementExecutor implements BatchStatementExecutor {

    private int batchSize;
    private int batchIndex;
    private String sqlQuery;
    private PreparedStatement pst;
    private List results = new ArrayList();
    private Connection conn;
    private boolean commitAfterBatch;

    /**
     * @param batchSize
     * @param sqlQuery
     * @param conn
     * @param commitAfterBatch
     * @throws SQLException
     */
    public PreparedBatchStatementExecutor(int batchSize, String sqlQuery, Connection conn, boolean commitAfterBatch) throws SQLException {
        this.batchSize = batchSize;
        this.sqlQuery = sqlQuery;
        this.conn = conn;
        this.commitAfterBatch = commitAfterBatch;
        pst = conn.prepareStatement(sqlQuery);
    }

    /**
     * @param statementProcessor
     * @return index of the batch executed.
     * @throws SQLException
     */
    public int addBatch(StatementCallbackHandler statementCallback) throws SQLException {
        if (statementCallback == null) {
            throw new SQLException("StatementProcessor parameter cannot be null.");
        }

        // Populate parameter values.
        statementCallback.processStatement(pst);

        // Add parameters to batch.
        pst.addBatch();

        // Release parameter values.
        pst.clearParameters();

        // Increment and check batch index against the size.
        if (++this.batchIndex >= this.batchSize) {
            executeBatch();
        }

        // Return the index of the batch.
        return this.results.size() + 1;
    }

    /**
     * Execute batch if there is something to execute.
     *
     * @throws SQLException
     */
    protected void executeBatch() throws SQLException {
        if (batchIndex > 0) {
            int[] batchResult = pst.executeBatch();

            // Commit if requested.
            if (commitAfterBatch) {
                conn.commit();
            }

            // Collect results.
            List batchResults = new ArrayList(batchResult.length);
            for (int i = 0; i < batchResult.length; i++) {
                batchResults.add(new Integer(batchResult[i]));
            }

            // Add to main results collection.
            this.results.add(batchResults);

            // Clear batch (Optional).
            // pst.clearBatch();
            this.batchIndex = 0;
        }
    }

    /**
     * This method <b>MUST</b> be called at the very end of batch processing
     * to finalize resources.
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        try {
            executeBatch();
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.BatchStatementExecutor#getResults()
     */
    public List getResults() {
        return results;
    }

    /**
     * @return
     */
    public int getBatchSize() {
        return batchSize;
    }

    /**
     * @return
     */
    public String getSqlQuery() {
        return sqlQuery;
    }

    /**
     * @return
     */
    public Connection getConnection() {
        return conn;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.jdbc.BatchStatementExecutor#getDescription()
     */
    public String getDescription() {
        return getSqlQuery();
    }
}
