package org.netflexity.api.orm.commands;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.FieldMetadata;
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
 * Command that inserts database record.
 */
public class InsertCommand extends AbstractOrmCommand {
    
    private Object record;

    /**
     * @param datasourceMetadata
     * @param recordToInsert
     */
    public InsertCommand(DatasourceMetadata datasourceMetadata, Object record) {
        super(datasourceMetadata);
        this.record = record;
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        RecordMetadata recordMetadata = getDatasourceMetadata().getRecordMetadataByClass(record.getClass());
        final String sql = SqlUtil.buildInsertQuery(recordMetadata);
        
        // Populate the Primary Key if it does not exist.
        populatePrimaryKey(recordMetadata, record);

        // Validate the record to be inserted.
        recordMetadata.validate(record);

        // Set statement parameters.
        executor = new PreparedStatementExecutor(sql, new InsertStatementCallbackHandler(recordMetadata, record));

        // Get connection resource.
        TransactionResource resource = getResource();
        Connection conn = resource.getConnection();
        try {
            int affected = executor.execute(conn);
            if (affected > 1){
                throw new CommandException(getDescription() + StringConstants.COLON + " More than 1 row was inserted.");
            }
        }
        catch (SQLException e) {
            throw CommandExceptionFactory.getInstance().create(e);
        }
    }

    /**
     * Set a unique primary key(s) for a record.
     * 
     * @param recordMetadata
     * @param record
     * @throws CommandException
     */
    protected static void populatePrimaryKey(RecordMetadata recordMetadata, Object record) throws CommandException {
        int index = 0;
        Collection primaryKey = recordMetadata.getPrimaryKeyMetadata();
        Object pk[] = new Object[primaryKey.size()];
        while((index++ <= 10)) {
            int pkIndex = 0;
            for (Iterator iter = primaryKey.iterator(); iter.hasNext(); pkIndex++) {
                FieldMetadata fmd = (FieldMetadata) iter.next();
                Object val = fmd.getProperty(record);

                // Assign random values for null pks.
                if(index > 1 || val == null){
                    switch(fmd.getJdbcType()){
	                	case Types.CHAR:
	                    case Types.VARCHAR:
	                	case Types.LONGVARCHAR:
	                	    val = RandomStringUtils.randomAlphanumeric(fmd.getLength());
	                		break;
	                	case Types.NUMERIC:
	                	    val = new BigInteger(String.valueOf(RandomUtils.nextLong()));
	                		break;
	                	case Types.BIGINT:
	                	    val = new Long(RandomUtils.nextLong());
                			break;
                        case Types.INTEGER:
                            val = new Integer(RandomUtils.nextInt());
                            break;
	                }
                }
                
                // Set pk values.
                pk[pkIndex] = val;
                fmd.setProperty(record, val);
            }
            
            // Check if this Primary Key exists.
            Object existingRecord = OrmCommandHelper.findByPK(recordMetadata, pk);
            if(existingRecord == null) {
                return;
            }
        }
    }
    /**
     * @return
     */
    public Object getRecord() {
        return record;
    }

    /**
     * @param record
     */
    public void setRecord(Object record) {
        this.record = record;
    }
}