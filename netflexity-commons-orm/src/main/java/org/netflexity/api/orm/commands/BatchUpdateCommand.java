package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.jdbc.PreparedBatchStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Command that inserts a batch of database records.
 */
public class BatchUpdateCommand extends AbstractBatchCommand {
    
    /**
     * @param recordMetadata
     * @param records
     * @param batchSize
     * @param commitAfterBatch
     */
    public BatchUpdateCommand(RecordMetadata recordMetadata, List records, int batchSize, boolean commitAfterBatch) {
        super(recordMetadata, records, batchSize, commitAfterBatch);
    }

    /**
     * @param recordMetadata
     * @param records
     */
    public BatchUpdateCommand(RecordMetadata recordMetadata, List records) {
        super(recordMetadata, records);
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        String sql = SqlUtil.buildUpdateQuery(recordMetadata);
        
        // Get connection resource.
        TransactionResource resource =  getResource();
        Connection conn = resource.getConnection();
        try {
            // Construct executor.
            executor = new PreparedBatchStatementExecutor(batchSize, sql, conn, commitAfterBatch);

            // Iterate through all records.
            for (Iterator iter = records.iterator(); iter.hasNext();) {
                Object record = iter.next();
                
                // Validate the record to be inserted.
                recordMetadata.validate(record);
                
                // Add insert to a batch of inserts to the same table.
                executor.addBatch(new BatchUpdateStatementCallbackHandler(recordMetadata, record));
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
    
    /* (non-Javadoc)
     * @see org.netflexity.api.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return (executor != null) ? executor.getDescription() : StringUtils.EMPTY;
    }
}