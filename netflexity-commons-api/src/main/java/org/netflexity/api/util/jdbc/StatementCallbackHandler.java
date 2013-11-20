package org.netflexity.api.util.jdbc;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Max Fedorov
 * 
 * Implementations of this interface perform the actual work of 
 * setting attributes or parameters of the statement.
 */
public interface StatementCallbackHandler {
	
	/** 
	 * This method should not execute the query, 
	 * but possibly set statement attributes or parameters.
	 * 
	 * @param Statement
	 * @throws SQLException
	 */
	public void processStatement(Statement st) throws SQLException;
    
    /**
     * @return a list of all parameters, set for execution.
     */
    public List getParameters();
}

