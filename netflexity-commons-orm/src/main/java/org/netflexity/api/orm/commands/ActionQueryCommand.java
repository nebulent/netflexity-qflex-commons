package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.util.jdbc.StatementExecutor;

/**
 * @author Max Fedorov
 *
 * This command will simply execute a passed in StatementExecutor as an
 * action statment against a selected datasource.
 *
 */
public class ActionQueryCommand extends AbstractOrmCommand {

    protected int result;

    /**
     * @param datasourceName
     * @param executor
     */
    public ActionQueryCommand(DatasourceMetadata datasourceMetadata, StatementExecutor executor) {
	super(datasourceMetadata);
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
	    result = executor.execute(conn);
	} catch (SQLException e) {
	    throw CommandExceptionFactory.getInstance().create(e);
	}
    }

    /**
     * @return
     */
    public int getResults() {
	return result;
    }
}
