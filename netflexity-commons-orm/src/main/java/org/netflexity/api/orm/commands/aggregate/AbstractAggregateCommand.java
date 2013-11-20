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
package org.netflexity.api.orm.commands.aggregate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.util.jdbc.RowCallbackHandler;
import org.netflexity.api.util.jdbc.StandardStatementExecutor;
import org.netflexity.api.util.jdbc.StatementExecutor;
import org.netflexity.api.util.sql.criteria.Column;
import org.netflexity.api.util.sql.criteria.Criteria;
import org.netflexity.api.util.sql.criteria.Query;
import org.netflexity.api.util.sql.criteria.SelectQueryBuilder;

/**
 * @author MAXIMAR
 *
 * Return a sum of all columns that match the criteria. Note that sum()
 * column has to be numeric.
 *
 */
public abstract class AbstractAggregateCommand extends AbstractOrmCommand {

    private static Logger logger = Logger.getLogger(AbstractAggregateCommand.class.getPackage().getName());
    private Column column;
    private Criteria criteria;
    private Double result;

    /**
     * @param recordMetadata
     * @param column
     * @param criteria
     */
    public AbstractAggregateCommand(RecordMetadata recordMetadata, Column column, Criteria criteria) {
	super(recordMetadata.getDatasourceMetadata());
	this.column = column;
	this.criteria = criteria;
    }

    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
	Query aggregateQuery = new SelectQueryBuilder(column);
	if (criteria != null) {
	    aggregateQuery.addCriteria(criteria);
	}
	String sql = aggregateQuery.getQuery();
	StatementExecutor executor = new StandardStatementExecutor(sql, new RowCallbackHandler() {
	    List<Double> rows = new ArrayList<Double>(1);

	    public List<Double> processRows(ResultSet rs) throws SQLException {
		if (rs.next()) {
		    rows.add(new Double(rs.getDouble(1)));
		}
		return rows;
	    }
	});

	// Print executed st-ment.
	logger.debug("TRANS-ID[" + getResource().getTransactionId() + "] - " + sql);

	// Execute query.
	try {
	    List rs = executor.executeQuery(((TransactionResource) getResource()).getConnection());
	    if (!rs.isEmpty()) {
		result = (Double) rs.get(0);
	    }
	} catch (SQLException e) {
	    logger.error("AggregateFunctionCommand failed to execute:", e);
	    throw new CommandException(e.getMessage(), e);
	}
    }

    /**
     * @return sum of all columns that fit the criteria.
     */
    public Double getResult() {
	return result;
    }
}
