package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.OptimisticLockException;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.command.OrmCommandHelper;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.jdbc.PreparedStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Command that updates database record.
 */
public class UpdateCommand extends AbstractOrmCommand {

    private Object newRecord;
    private Object oldRecord;
    private RecordMetadata recordMetadata;
    
    /**
     * @param datasourceMetadata
     * @param oldRecord
     * @param newRecord
     */
    public UpdateCommand(DatasourceMetadata datasourceMetadata, Object oldRecord, Object newRecord) {
        super(datasourceMetadata);
        this.oldRecord = oldRecord;
        this.newRecord = newRecord;
    }

    /**
     * @param datasourceMetadata
     * @param record
     */
    public UpdateCommand(DatasourceMetadata datasourceMetadata, Object record) {
        this(datasourceMetadata, null, record);
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        recordMetadata = getDatasourceMetadata().getRecordMetadataByClass(newRecord.getClass());
        
        // Validate the record to be updated.
        recordMetadata.validate(newRecord);

        // Obtain fresh record.
        if(oldRecord == null){
            oldRecord = getFreshRecordByPrimaryKey(newRecord);
        }
        
        // Check optimistic lock.
        checkOptimisticLock(oldRecord, newRecord);
        
        // Set statement parameters.
        final String sql = SqlUtil.buildUpdateQuery(recordMetadata, oldRecord, newRecord);

//        System.out.println("query: " + sql);
        
        // sql will be null if there is nothing to update.
        if(StringUtils.isNotBlank(sql)){
            executor = new PreparedStatementExecutor(sql, new UpdateStatementCallbackHandler(recordMetadata, oldRecord, newRecord));
            
            // Get connection resource.
            TransactionResource resource =  getResource();
            Connection conn = resource.getConnection();
            try {
                int affected = executor.execute(conn);
                if (affected > 1){
                    throw new CommandException(getDescription() + StringConstants.COLON + " More than 1 row was updated.");
                }
            }
            catch (SQLException e) {
                throw CommandExceptionFactory.getInstance().create(e);
            }
        }
    }

    /**
     * Override method to provide real check for optimistic locking.
     * Throw OptimisticLockException to trigger that lock.
     * 
     * @param oldRecord
     * @param newRecord
     * @throws OptimisticLockException
     */
    public void checkOptimisticLock(Object oldRecord, Object newRecord) throws OptimisticLockException{
    }
    
    /**
     * @return freshly updated record.
     */
    public Object getRecord() {
        return newRecord;
    }
    
    /**
     * @param record
     * @return
     * @throws CommandException
     */
    protected Object getFreshRecordByPrimaryKey(Object record) throws CommandException{
        Collection primaryKeyMetadata = recordMetadata.getPrimaryKeyMetadata();
        Object[] pk = new Object[primaryKeyMetadata.size()];
        int i = 0;
        for (Iterator iter = primaryKeyMetadata.iterator(); iter.hasNext(); i++) {
            FieldMetadata fmd = (FieldMetadata) iter.next();
            pk[i++] = fmd.getProperty(record);
        }
        return OrmCommandHelper.findByPK(recordMetadata, pk);
    }
}