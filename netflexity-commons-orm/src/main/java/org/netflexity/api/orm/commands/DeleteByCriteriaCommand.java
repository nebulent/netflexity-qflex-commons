package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.jdbc.StandardStatementExecutor;
import org.netflexity.api.util.sql.criteria.Criteria;

/**
 * @author Max Fedorov
 *
 * Executes any SQL against one table.
 */
public class DeleteByCriteriaCommand extends AbstractOrmCommand {

    protected Criteria criteria;
    private RecordMetadata recordMetadata;
    private int result;

    /**
     * @param recordMetadata
     * @param criteria
     */
    public DeleteByCriteriaCommand(RecordMetadata recordMetadata, Criteria criteria) {
	super(recordMetadata.getDatasourceMetadata());
	this.criteria = criteria;
	this.recordMetadata = recordMetadata;
    }

    /**
     * @param recordMetadata
     */
    public DeleteByCriteriaCommand(RecordMetadata recordMetadata) {
	this(recordMetadata, null);
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
	final String sql = SqlUtil.buildDeleteByCriteriaQuery(recordMetadata, criteria);

	// Create executor.
	executor = new StandardStatementExecutor(sql);

	// Get connection.
	TransactionResource resource = getResource();
	Connection conn = resource.getConnection();
	try {
	    result = executor.execute(conn);
	} catch (SQLException e) {
	    throw CommandExceptionFactory.getInstance().create(e);
	}
    }

    /**
     * @return int
     */
    public int getResult() {
	return result;
    }
}
