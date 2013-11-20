package org.netflexity.api.orm.datasource.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.lang.math.RandomUtils;
import org.netflexity.api.command.TransactionResource;
import org.netflexity.api.command.TransactionResourceException;
import org.netflexity.api.util.Assertion;

/**
 * @author Max Fedorov
 *
 * Connection wrapper.
 */
public class DatabaseTransactionResource implements TransactionResource {

    private Connection connection;
    private long transactionId;

    /**
     *
     */
    public DatabaseTransactionResource(Connection connection) {
	Assertion.asert(connection != null, "Connection cannot be NULL.");
	this.connection = connection;
	this.transactionId = RandomUtils.nextLong();
    }

    /**
     * @see org.netflexity.dorm.IResource#commit()
     */
    @Override
    public void commit() throws TransactionResourceException {
	try {
	    connection.commit();
	} catch (SQLException e) {
	    throw new TransactionResourceException(e.getMessage(), e);
	}
    }

    /**
     * @see org.netflexity.dorm.IResource#rollback()
     */
    @Override
    public void rollback() throws TransactionResourceException {
	try {
	    connection.rollback();
	} catch (SQLException e) {
	    throw new TransactionResourceException(e.getMessage(), e);
	}
    }

    /**
     * @see org.netflexity.dorm.IResource#close()
     */
    @Override
    public void close() throws TransactionResourceException {
	try {
	    connection.close();
	} catch (SQLException e) {
	    throw new TransactionResourceException(e.getMessage(), e);
	}
    }

    /**
     * @see org.netflexity.dorm.IResource#begin()
     */
    @Override
    public void begin() throws TransactionResourceException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    throw new TransactionResourceException(e.getMessage(), e);
	}
    }

    /**
     * @return
     */
    @Override
    public Connection getConnection() {
	return connection;
    }

    /**
     * @param connection
     */
    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    /**
     * @see
     * org.netflexity.dorm.command.TransactionResource#getTransactionId()
     */
    @Override
    public long getTransactionId() {
	return transactionId;
    }
}
