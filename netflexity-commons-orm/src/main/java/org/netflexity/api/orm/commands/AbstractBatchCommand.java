package org.netflexity.api.orm.commands;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.util.Assertion;
import org.netflexity.api.util.jdbc.BatchStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Command that executes a batch of operations on records of the same type.
 */
public abstract class AbstractBatchCommand extends AbstractOrmCommand {
    
    protected List records;
    protected int batchSize;
    protected List results;
    protected RecordMetadata recordMetadata;
    protected boolean commitAfterBatch;
    protected BatchStatementExecutor executor;
    
    /**
     * @param recordMetadata
     * @param records
     */
    public AbstractBatchCommand(RecordMetadata recordMetadata, List records) {
        this(recordMetadata, records, 50, true);
    }
        
    /**
     * @param recordMetadata
     * @param records
     * @param batchSize
     * @param commitAfterBatch
     */
    public AbstractBatchCommand(RecordMetadata recordMetadata, List records, int batchSize, boolean commitAfterBatch) {
        super(recordMetadata.getDatasourceMetadata());
        Assertion.asert((records != null && !records.isEmpty()), "Cannot execute a batch of 0 records");
        Assertion.asert(batchSize > 0, "Batch size cannot be negative or zero");
        this.records = records;
        this.recordMetadata = recordMetadata;
        this.commitAfterBatch = commitAfterBatch;
        this.batchSize = batchSize;
    }
    
    /**
     * @return Returns the batchSize.
     */
    public int getBatchSize() {
        return batchSize;
    }

    /**
     * @param batchSize The batchSize to set.
     */
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * @return Returns the results.
     */
    public List getResults() {
        return results;
    }

    /**
     * @param results The results to set.
     */
    public void setResults(List results) {
        this.results = results;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.command.Command#getDescription()
     */
    @Override
    public String getDescription() {
        return (executor != null) ? executor.getDescription() : StringUtils.EMPTY;
    }
}
