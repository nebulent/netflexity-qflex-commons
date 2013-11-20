package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.jdbc.PreparedBatchStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Command that deletes a batch of database records.
 */
public class BatchDeleteCommand extends AbstractBatchCommand {
    
    /**
     * @param recordMetadata
     * @param records
     * @param batchSize
     * @param commitAfterBatch
     */
    public BatchDeleteCommand(RecordMetadata recordMetadata, List records, int batchSize, boolean commitAfterBatch) {
        super(recordMetadata, records, batchSize, commitAfterBatch);
    }

    /**
     * @param recordMetadata
     * @param records
     */
    public BatchDeleteCommand(RecordMetadata recordMetadata, List records) {
        super(recordMetadata, records);
    }
    
    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        String sql = SqlUtil.buildDeleteQuery(recordMetadata);
        
        // Get connection resource.
        TransactionResource resource =  getResource();
        Connection conn = resource.getConnection();
        try {
            // Construct executer.
            executor = new PreparedBatchStatementExecutor(batchSize, sql, conn, commitAfterBatch);

            // Iterate through all records.
            for (Iterator iter = records.iterator(); iter.hasNext();) {
                Object record = iter.next();

                // Add delete to a batch of inserts to the same table.
                executor.addBatch(new DeleteStatementCallbackHandler(recordMetadata, record));
            }
        }
        catch (SQLException e) {
            throw CommandExceptionFactory.getInstance().create(e);
        }
        finally{
            // Close batch executer and other related resources.
            if(executor != null){
                try {
                    executor.close();
                } 
                catch (SQLException e) {
                    // Nothing we can do.
                    e.printStackTrace();
                }
                finally{
                    results = executor.getResults();
                }
            }
        }
    }
}
