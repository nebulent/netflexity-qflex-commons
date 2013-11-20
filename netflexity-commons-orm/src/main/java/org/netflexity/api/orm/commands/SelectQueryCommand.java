package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.util.jdbc.StatementExecutor;

/**
 * @author Max Fedorov
 *
 * This command will simply execute a passed in StatementExecutor
 * as a SELECT statement against a selected datasource.
 * 
 */
public class SelectQueryCommand extends AbstractOrmCommand {
    
    protected List results;
    protected Class rowSetBeanClass;
    
    /**
     * @param datasourceMeta
     * @param executor
     */
    public SelectQueryCommand(DatasourceMetadata datasourceMeta, StatementExecutor executor) {
        super(datasourceMeta);
        this.executor = executor;
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        // Get connection resource.
        TransactionResource resource = getResource();
        Connection conn = resource.getConnection();
        try {
            results = executor.executeQuery(conn);
        }
        catch (SQLException e) {
            throw CommandExceptionFactory.getInstance().create(e);
        }
    }

    /**
     * @return a list of records.
     */
    public List getResults() {
        return results;
    }
}
