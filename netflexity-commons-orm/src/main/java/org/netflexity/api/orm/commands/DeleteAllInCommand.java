/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.orm.commands;

import java.sql.SQLException;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.util.SqlUtil;
import org.netflexity.api.util.jdbc.StandardStatementExecutor;
import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.CriteriaBuilder;
import org.netflexity.api.util.sql.criteria.Table;

/**
 * @author MAXIMAR
 *
 * Search alerts.
 */
public class DeleteAllInCommand extends AbstractOrmCommand {

    private RecordMetadata recordMetadata;
    private Object[] values;
    private String columnName;
    private int result;

    /**
     * @param recordMetadata
     * @param columnName
     * @param values[]
     */
    public DeleteAllInCommand(RecordMetadata recordMetadata, String columnName, Object values[]) {
	super(recordMetadata.getDatasourceMetadata());
	this.recordMetadata = recordMetadata;
	this.values = values;
	this.columnName = columnName;
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
	if (values != null && values.length > 0) {
	    Table table = new Table(recordMetadata.getName());
	    Criteria inCriteria = CriteriaBuilder.getInCriteria(new Column(table, columnName), values);
	    String sql = SqlUtil.buildDeleteByCriteriaQuery(recordMetadata, inCriteria);

	    // Construct executor.
	    executor = new StandardStatementExecutor(sql);

	    // Execute query.
	    try {
		result = executor.execute(((TransactionResource) getResource()).getConnection());
	    } catch (SQLException e) {
		throw CommandExceptionFactory.getInstance().create(e);
	    }
	}
    }

    /**
     * @return rows deleted
     */
    public int getResult() {
	return result;
    }
}
