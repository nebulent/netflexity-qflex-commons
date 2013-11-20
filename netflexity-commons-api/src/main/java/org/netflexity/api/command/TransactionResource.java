/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.command;

import java.sql.Connection;

/**
 * @author Max Fedorov
 *
 * Represents some transactional resource, like database connection.
 */
public interface TransactionResource {
    /**
     * Commit changes.
     */
    public void commit() throws TransactionResourceException;

    /**
     * Rollback changes.
     */
    public void rollback() throws TransactionResourceException;

    /**
     * Close resources.
     */
    public void close() throws TransactionResourceException;

    /**
     * Start transaction.
     */
    public void begin() throws TransactionResourceException;
    
	/**
	 * Method getTransactionId.
	 * @return long
	 */
    public long getTransactionId(); 
    
    /**
     * @return
     */
    public Connection getConnection();
}
