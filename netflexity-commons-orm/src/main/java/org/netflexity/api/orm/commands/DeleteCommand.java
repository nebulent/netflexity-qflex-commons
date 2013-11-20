package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.StringConstants;
import org.netflexity.api.util.jdbc.PreparedStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Command that deletes database record.
 */
public class DeleteCommand extends AbstractOrmCommand {
    
    private Object record;
    
    /**
     * @param datasourceMetadata
     * @param record
     */
    public DeleteCommand(DatasourceMetadata datasourceMetadata, Object record) {
        super(datasourceMetadata);
        this.record = record;
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        RecordMetadata recordMetadata = getDatasourceMetadata().getRecordMetadataByClass(record.getClass());
        final String sql = SqlUtil.buildDeleteQuery(recordMetadata);
        executor = new PreparedStatementExecutor(sql, new DeleteStatementCallbackHandler(recordMetadata, record));

        // Get connection.
        TransactionResource resource = getResource();
        Connection conn = resource.getConnection();
        try {
            int affected = executor.execute(conn);
            if (affected > 1){
                throw new CommandException(getDescription() + StringConstants.COLON + " More than 1 row was deleted.");
            }
        }
        catch (SQLException e) {
            throw CommandExceptionFactory.getInstance().create(e);
        }
    }
}
