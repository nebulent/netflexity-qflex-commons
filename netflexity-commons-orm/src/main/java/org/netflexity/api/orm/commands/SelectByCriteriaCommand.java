package org.netflexity.api.orm.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.netflexity.api.command.CommandException;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.orm.command.AbstractOrmCommand;
import org.netflexity.api.orm.command.CommandExceptionFactory;
import org.netflexity.api.orm.criteria.CriteriaQueryBuilder;
import org.netflexity.api.orm.jdbc.RecordsRowCallbackhandler;
import org.netflexity.api.util.jdbc.StandardStatementExecutor;

/**
 * @author Max Fedorov
 *
 * Executes any SQL against one table.
 */
public class SelectByCriteriaCommand extends AbstractOrmCommand {

    protected CriteriaQueryBuilder criteria;
    protected List results = new ArrayList();
    protected int maxRows;
    
    /**
     * @param criteria
     */
    public SelectByCriteriaCommand(CriteriaQueryBuilder criteria) {
        super(criteria.getRecordMetadata().getDatasourceMetadata());
        this.criteria = criteria;
    }
    
    /**
     * @param criteria
     * @param maxRows
     */
    public SelectByCriteriaCommand(CriteriaQueryBuilder criteria, int maxRows) {
        this(criteria);
        this.maxRows = maxRows;
    }
    
    /**
     * @see org.netflexity.dorm.command.Command#execute()
     */
    @Override
    public void execute() throws CommandException {
        String sql = criteria.getQuery();

        // Create executor.
        executor = new StandardStatementExecutor(sql, new RecordsRowCallbackhandler(criteria.getRecordMetadata()));
        
        // Set maximum rows to return.
        if(maxRows > 0){
            executor.setMaxRows(maxRows);
        }
        
        // Get connection.
        TransactionResource resource = getResource();
        Connection conn = resource.getConnection();
        try {
            // Execute query and retrieve results.
            results = executor.executeQuery(conn);
        }
        catch (SQLException e) {
            throw CommandExceptionFactory.getInstance().create(e);
        }
    }
    /**
     * @return List of all found records.
     */
    public List getResults() {
        return results;
    }

    /**
     * @return first found Record.
     */
    public Object getFirstResult() {
        return results.isEmpty() ? null : results.get(0);
    }
}
